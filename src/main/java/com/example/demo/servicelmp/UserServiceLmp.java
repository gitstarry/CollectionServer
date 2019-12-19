package com.example.demo.servicelmp;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dao.UserDAO;
import java.util.List;

/**
 * @author 阳光
 * @version v1.0
 */
@Service
public class UserServiceLmp implements UserService {
    @Autowired
    UserDAO mapper;

    @Override
    public List<User> select() {
        System.out.println(mapper);
        return mapper.select();
    }
    @Override
    public void insertUser(String studentNumber,String password,String name,String sex,String salt){
        mapper.insertUser(studentNumber,password,name,sex,salt);
    }
    @Override
    public List<User> selectSaltByNumber(String name){
        return mapper.selectSaltByNumber(name);
    }
}
