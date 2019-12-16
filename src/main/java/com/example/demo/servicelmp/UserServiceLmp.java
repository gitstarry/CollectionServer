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
    public void insertUser(String password,String username,String salt){
        mapper.insertUser(password,username,salt);
    }

    @Override
    public List<User> selectSaltByName(String name){
        return mapper.selectSaltByName(name);
    }
}
