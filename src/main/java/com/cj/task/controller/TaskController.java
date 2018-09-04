package com.cj.task.controller;

import com.cj.task.annotation.AdminValid;
import com.cj.task.annotation.TokenValid;
import com.cj.task.entity.Result;
import com.cj.task.entity.request.AddTaskRequest;
import com.cj.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cj on 2018/9/3.
 */
@RestController
@RequestMapping("/")
public class TaskController {
    @Autowired
    TaskService taskService;

    //新增表单
    @AdminValid
    @PostMapping("task")
    public ResponseEntity createTask(@RequestBody AddTaskRequest taskRequest) {
        Result result = taskService.createTask(taskRequest);
        return ResponseEntity.ok(result);
    }

    //查看表单列表
    @TokenValid
    @GetMapping("tasks")
    public ResponseEntity getTasks(@RequestParam int page, @RequestParam int pageSize) {
        Result result = taskService.taskList(page, pageSize);
        return ResponseEntity.ok(result);
    }

    //查看表单详情
    @TokenValid
    @GetMapping("task/{id}")
    public ResponseEntity getTaskInfo(@PathVariable int id) {
        Result result = taskService.taskInfo(id);
        return ResponseEntity.ok(result);
    }

    //修改表单
    @AdminValid
    @PutMapping("task/{id}")
    public ResponseEntity updateTask(@PathVariable int id, @RequestBody AddTaskRequest taskRequest) {
        Result result = taskService.updateTask(id, taskRequest);
        return ResponseEntity.ok(result);
    }
    //删除表单
    @AdminValid
    @DeleteMapping("task/{id}")
    public ResponseEntity deleteTask(@PathVariable int id){
        Result result=taskService.deleteTask(id);
        return ResponseEntity.ok(result);
    }
}
