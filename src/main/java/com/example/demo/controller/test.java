package com.example.demo.controller;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author 阳光
 * @version v1.0
 */
public class test {
    public static void main(String[] args) {
        String password = "123";
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        // 设置 hash 算法迭代次数
        int times = 2;
        // 得到 hash 后的密码
        String encodedPassword = new SimpleHash("md5", password, salt, times).toString();

        System.out.println(encodedPassword);


    }
}
