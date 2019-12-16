package com.example.demo.service;

import com.example.demo.entity.User;

import java.util.List;

/**
 * @author 阳光
 * @version v1.0
 */
public interface UserService {
    List<User> select();

    void insertUser(String password,String username,String salt);

    List<User> selectSaltByName(String name);
}
