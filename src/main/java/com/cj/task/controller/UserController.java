package com.cj.task.controller;

import com.cj.task.entity.Result;
import com.cj.task.service.UserService;
import com.cj.task.utils.RegexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

/**
 * Created by cj on 2018/8/25.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    //用户注册
    @PostMapping("/register")
    public Result register(@RequestParam String account, @RequestParam String pwd, @RequestParam String nickName) {
        System.out.println("account " + account);
        return userService.registerUser(account, pwd, nickName);
    }
}
