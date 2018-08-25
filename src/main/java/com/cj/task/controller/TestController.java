package com.cj.task.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cj on 2018/8/25.
 */
@RestController
@RequestMapping("/hello")
public class TestController {
    @GetMapping("/")
   public String hello(){
       return "hello world";
   }
}
