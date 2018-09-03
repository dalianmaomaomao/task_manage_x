package com.cj.task.controller;

import com.cj.task.annotation.AdminValid;
import com.cj.task.annotation.TokenValid;
import com.cj.task.entity.Result;
import com.cj.task.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cj on 2018/9/2.
 */
@RestController
@RequestMapping("/")
public class ConfigController {
    @Autowired
    ConfigService configService;

    //新增表单字段规则
    @AdminValid
    @PostMapping("config")
    public ResponseEntity createConfig(@RequestParam String name, @RequestParam String description, @RequestParam String expression) {
        Result result = configService.createConfig(name, description, expression);
        return ResponseEntity.ok(result);
    }

    //查看规则列表
    @TokenValid
    @GetMapping("configs")
    public ResponseEntity configList(@RequestParam int page, @RequestParam int pageSize) {
        Result result = configService.configList(page, pageSize);
        return ResponseEntity.ok(result);
    }

    //查看规则详情
    @TokenValid
    @GetMapping("config/{id}")
    public ResponseEntity getConfigInfo(@PathVariable int id) {
        Result result = configService.getConfigInfo(id);
        return ResponseEntity.ok(result);
    }

    //修改规则
    @AdminValid
    @PutMapping("config/{id}")
    public ResponseEntity updateConfig(@PathVariable int id, @RequestParam String name, @RequestParam String description, @RequestParam String expression) {
        Result result = configService.updateConfig(name, description, expression, id);
        return ResponseEntity.ok(result);
    }

    //删除规则
    @AdminValid
    @DeleteMapping("config/{id}")
    public ResponseEntity deleteConfig(@PathVariable int id) {
        Result result = configService.deleteConfig(id);
        return ResponseEntity.ok(result);
    }
}
