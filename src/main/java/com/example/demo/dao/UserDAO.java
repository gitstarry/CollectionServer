package com.example.demo.dao;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 阳光
 * @version v1.0
 */
@Mapper
public interface  UserDAO {
    //@Select("select id,name from user")
    List<User> select();

    void insertUser(String studentNumber,String password,String name,String sex,String salt);

    List<User> selectSaltByNumber(String name);
}
