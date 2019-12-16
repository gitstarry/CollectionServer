package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.result.Result;
import com.example.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.Objects;

/*
 * @RestController == @Controller + @ResponseBody，需要注意的是使用这个注解代表着整个一类都是如此
 *  当然 @Controller & @ResponseBody 还是可以使用的
 */
//@CrossOrigin  ？？
@CrossOrigin//如果controller在类上标了@CrossOrigin或在方法上标了@CrossOrigin注解，
            // 则spring 在记录mapper映射时会记录对应跨域请求映射
@RestController
@RequestMapping("/api")
public class login {

    @Autowired//@Autowired是用在JavaBean中的注解，通过byType形式，用来给指定的字段或方法注入所需的外部资源。
    UserService operator;

    @PostMapping("/login")
    public Result login(@RequestBody User requestUser) {
        String username = requestUser.getUsername();
        String password = requestUser.getPassword();
      /*  SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();
        simpleAccountRealm.addAccount("admin","615285e26a7f0486bf6cb5e1bd562699");

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //subject.login(token); // 登录
        int times = 2;
        // 得到 hash 后的密码
        String encodedPassword = new SimpleHash("md5", password, salt, times).toString();
*/
        // 对 html 标签进行转义，防止 XSS 攻击
        List<User> x  =   operator.selectSaltByName(username);
        System.out.println(1);
        username = HtmlUtils.htmlEscape(username);
        if (operator.selectSaltByName(username).size() == 0){

            String msg = "用户名不存在";
            return new Result(200,msg);
        }
        String salt = operator.selectSaltByName(username).get(0).getSalt();
        System.out.println("数据库："+operator.selectSaltByName(username).get(0).getPassword());
        System.out.println("盐"+salt);
        String encodedPassword = new SimpleHash("md5", password, salt, 2).toString();
        System.out.println("生成"+encodedPassword);
        if (encodedPassword.equals(operator.selectSaltByName(username).get(0).getPassword())){
            return new Result(200);
        }else {
            return new Result(400);
        }
    }
/*    @GetMapping("/logout")
    public Result loginOut(){
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            return new Result(200);
        }
        catch (Exception e){
            System.out.println(400);
            return new Result(400);
        }
        try {
            this.clearRunAsIdentitiesInternal();
            this.securityManager.logout(this);
        } finally {
            this.session = null;
            this.principals = null;
            this.authenticated = false;
        }
    }*/


    //注册
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        // 对 html 标签进行转义，防止 XSS 攻击
        username = HtmlUtils.htmlEscape(username);// ①转换为HTML转义字符表示
        user.setUsername(username);
        /*boolean exist = userService.isExist(username);
        if (exist) {
            String message = "用户名已被使用";
            return ResultFactory.buildFailResult(message);
        }*/
        // 生成盐,默认长度 16 位
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        // 设置 hash 算法迭代次数
        int times = 2;
        // 得到 hash 后的密码
        String encodedPassword = new SimpleHash("md5", password, salt, times).toString();
        try {
            operator.insertUser(encodedPassword,username,salt);
            return new Result(200);
        }catch (Exception e){
            return new Result(400);
        }


        // 存储用户信息，包括 salt 与 hash 后的密码
        /*user.setSalt(salt);
        user.setPassword(encodedPassword);
        userService.add(user);
        SecurityUtils.getSubject();
        return ResultFactory.buildSuccessResult(user);*/
    }
}
