package com.cj.task.controller;

import com.cj.task.annotation.AdminValid;
import com.cj.task.annotation.TokenValid;
import com.cj.task.entity.Result;
import com.cj.task.entity.User;
import com.cj.task.entity.request.AddContentRequest;
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
    public ResponseEntity deleteTask(@PathVariable int id) {
        Result result = taskService.deleteTask(id);
        return ResponseEntity.ok(result);
    }

    //收藏表单
    @TokenValid
    @PostMapping("tasks/{id}/likes")
    public ResponseEntity addLikeTask(@PathVariable int id, User user) {
        Result result = taskService.addLikeTask(id, user);
        return ResponseEntity.ok(result);
    }

    //取消收藏
    @TokenValid
    @DeleteMapping("tasks/{id}/likes")
    public ResponseEntity deleteLikeTask(@PathVariable int id, User user) {
        Result result = taskService.deleteLikeTask(id, user);
        return ResponseEntity.ok(result);
    }

    //查看收藏列表
    @TokenValid
    @GetMapping("tasks/likes")
    public ResponseEntity getAllLikeTasks(User user) {
        Result result = taskService.getAllLikeTasks(user);
        return ResponseEntity.ok(result);
    }

    //提交表单内容
    @TokenValid
    @PostMapping("/tasks/{id}/contents")
    public ResponseEntity addTaskContents(@PathVariable int id, @RequestBody AddContentRequest contentRequest, User user) {
        Result result = taskService.addTaskContents(id, contentRequest, user);
        return ResponseEntity.ok(result);
    }

    //修改某个用户提交的某条内容
    @TokenValid
    @PutMapping("/tasks/{taskId}/contents/{contentId}")
    public ResponseEntity updateTaskContents(@PathVariable int taskId, @PathVariable int contentId,
                                             @RequestBody AddContentRequest contentRequest, User user) {
        Result result = taskService.updateTaskContents(taskId, contentId, contentRequest, user);
        return ResponseEntity.ok(result);
    }

    //删除某个用户提交的某条内容
    @TokenValid
    @DeleteMapping("/tasks/{taskId}/contents/{contentId}")
    public ResponseEntity deleteTaskContents(@PathVariable int taskId, @PathVariable int contentId,
                                             User user) {
        Result result = taskService.deleteTaskContents(taskId, contentId, user);
        return ResponseEntity.ok(result);
    }

}
