package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.result.Result;
import com.example.demo.service.UserService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RestController == @Controller + @ResponseBody，需要注意的是使用这个注解代表着整个一类都是如此
//@CrossOrigin
@CrossOrigin//如果controller在类上标了@CrossOrigin或在方法上标了@CrossOrigin注解，// 则spring 在记录mapper映射时会记录对应跨域请求映射
@RestController
@RequestMapping("/api")
public class login {

    @Autowired//@Autowired是用在JavaBean中的注解，通过byType形式，用来给指定的字段或方法注入所需的外部资源。
            UserService operator;

    @GetMapping(value = "/getDoc")
    public void getDoc(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }

@PostMapping("/getAllUser")
    public User getAllUser(@RequestBody User requestUserInfo){
         List<User> list = operator.selectSaltByNumber(requestUserInfo.getStudentNumber());//获取全部学生信息
         User user = new User();
         user.setName(list.get(0).getName());
         user.setStudentNumber(list.get(0).getStudentNumber());
         user.setSex(list.get(0).getSex());
         return user;
    }

    @PostMapping("/login")
    public Result login(@RequestBody User requestUser) {
        String studentNumber = requestUser.getStudentNumber();
        String password = requestUser.getPassword();

        List<User> x = operator.selectSaltByNumber(studentNumber);
        System.out.println(operator.selectSaltByNumber(studentNumber).size());
        if (operator.selectSaltByNumber(studentNumber).size() == 0) {
            String msg = "用户名不存在";
            return new Result(200, msg);
        }
        String salt = operator.selectSaltByNumber(studentNumber).get(0).getSalt();
        String encodedPassword = new SimpleHash("md5", password, salt, 2).toString();
        if (encodedPassword.equals(operator.selectSaltByNumber(studentNumber).get(0).getPassword())) {
            return new Result(200);
        } else {
            return new Result(400);
        }
    }

    //注册
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        String name = user.getName();
        String password = user.getPassword();
        String studentNumber = user.getStudentNumber();//学号
        String sex = user.getSex();//性别

        // 对 html 标签进行转义，防止 XSS 攻击
        String username = HtmlUtils.htmlEscape(name);// ①转换为HTML转义字符表示
        user.setName(username);
        // 生成盐,默认长度 16 位
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        // 设置 hash 算法迭代次数
        int times = 2;
        // 得到 hash 后的密码
        String encodedPassword = new SimpleHash("md5", password, salt, times).toString();
        try {
            operator.insertUser(studentNumber, encodedPassword, name, sex, salt);
            return new Result(200);
        } catch (Exception e) {
            return new Result(400);
        }
    }
}
