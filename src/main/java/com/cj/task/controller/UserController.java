package com.cj.task.controller;

import com.cj.task.annotation.AdminValid;
import com.cj.task.annotation.TokenValid;
import com.cj.task.entity.Result;
import com.cj.task.entity.User;
import com.cj.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Created by cj on 2018/8/25.
 */
@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    UserService userService;

    //用户注册
    @PostMapping("register")
    public ResponseEntity register(@RequestParam String account, @RequestParam String pwd, @RequestParam String nickName) {
        System.out.println("account " + account);
        Result result = userService.registerUser(account, pwd, nickName);
        return ResponseEntity.ok(result);
    }

    //用户登录
    @PostMapping("login")
    public ResponseEntity login(@RequestParam String account, @RequestParam String pwd) {
        Result result = userService.login(account, pwd);
        return ResponseEntity.ok(result);
    }

    //获取用户信息
    @TokenValid
    @GetMapping("user/id")
    public ResponseEntity getUsers(@RequestParam int id, User user) {
        if (!user.isAdmin()) {
            if (id == user.getId()) {
                return ResponseEntity.ok(new Result.ResultBuilder().success("获取用户信息成功", user));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Result.ResultBuilder().fail(HttpStatus.FORBIDDEN.value(), "该用户无权限"));
        }
        Result result = userService.findUserById(id);
        return ResponseEntity.ok(result);
    }

    //修改个人信息
    @TokenValid
    @PutMapping("updateUserinfo")
    public ResponseEntity updateUserinfo(@RequestParam String nickName, User user) {
        Result result = userService.updateUserinfo(nickName, user);
        return ResponseEntity.ok(result);
    }

    //修改密码
    @TokenValid
    @PutMapping("updatePwd")
    public ResponseEntity updatePwd(@RequestParam String oldPwd, @RequestParam String newPwd, User user) {
        Result result = userService.updatePwd(oldPwd, newPwd, user);
        return ResponseEntity.ok(result);
    }

    //查看所有用户列表
    @AdminValid
    @GetMapping("userList")
    public ResponseEntity userList() {
        Result result = userService.userList();
        return ResponseEntity.ok(result);
    }

}

