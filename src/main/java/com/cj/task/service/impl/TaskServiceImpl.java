package com.cj.task.service.impl;

import com.cj.task.entity.*;
import com.cj.task.entity.request.AddFieldRequest;
import com.cj.task.entity.request.AddTaskRequest;
import com.cj.task.mapper.ConfigMapper;
import com.cj.task.mapper.FieldMapper;
import com.cj.task.mapper.TaskMapper;
import com.cj.task.mapper.UserMapper;
import com.cj.task.service.TaskService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by cj on 2018/9/3.
 */
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskMapper taskMapper;
    @Autowired
    FieldMapper fieldMapper;
    @Autowired
    ConfigMapper configMapper;
    @Autowired
    UserMapper userMapper;

    //创建表单
    public Result createTask(AddTaskRequest taskRequest) {
        if (StringUtils.isEmpty(taskRequest.getTitle())) {
            return new Result.ResultBuilder().fail("表单名称为空");
        }
        if (StringUtils.isEmpty(taskRequest.getDescription())) {
            return new Result.ResultBuilder().fail("表单备注为空");
        }
        if (taskRequest.getPublishTime() == null) {
            return new Result.ResultBuilder().fail("表单生效时间为空");
        }
        if (taskRequest.getPublishTime().compareTo(new Date()) < 0) {
            return new Result.ResultBuilder().fail("表单生效时间必须为当前之后");
        }
        if (taskRequest.getDeadline().compareTo(new Date()) < 0) {
            return new Result.ResultBuilder().fail("表单截止时间必须为当前之后");
        }
        if (taskRequest.getDeadline().compareTo(taskRequest.getPublishTime()) < 0) {
            return new Result.ResultBuilder().fail("表单截止时间必须在表单生效时间之后");
        }
        if (taskRequest.getDeadline() == null) {
            return new Result.ResultBuilder().fail("表单截止时间为空");
        }
        if (taskRequest.getTitle().length() > 15) {
            return new Result.ResultBuilder().fail("表单名称长度不符合规范");
        }
        if (taskRequest.getDescription().length() > 30) {
            return new Result.ResultBuilder().fail("表单备注长度不符合规范");
        }
//        Task task=taskRequest.toTask();
        Task task = taskRequest.toTask();
        taskMapper.save(task);
        System.out.println("task.id:" + task.getId());
        if (taskRequest.getUsers() != null && taskRequest.getUsers().length != 0) {
            taskMapper.insertTaskUsers(task.getId(), taskRequest.getUsers());
        }
        if (taskRequest.getFields() != null && taskRequest.getFields().length != 0) {
            for (AddFieldRequest fieldRequest : taskRequest.getFields()) {
                Config config = configMapper.getConfigInfoById(fieldRequest.getConfigId());
                Field field = fieldRequest.toField(task, config);
                fieldMapper.save(field);
            }
        }
        return new Result.ResultBuilder().success("表单创建成功", taskRequest);
    }

    //获取表单列表
    public Result taskList(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Task> taskList = taskMapper.getTasks();
        return new Result.ResultBuilder().success("获取表单列表成功", taskList);
    }

    //获取表单详情
    public Result taskInfo(int id) {
        Task task = taskMapper.taskInfo(id);
        return new Result.ResultBuilder().success("获取表单详情成功", task);
    }

    //修改表单
    public Result updateTask(int id, AddTaskRequest taskRequest) {
        if (StringUtils.isEmpty(taskRequest.getTitle())) {
            return new Result.ResultBuilder().fail("表单名称为空");
        }
        if (StringUtils.isEmpty(taskRequest.getDescription())) {
            return new Result.ResultBuilder().fail("表单备注为空");
        }
        if (taskRequest.getPublishTime() == null) {
            return new Result.ResultBuilder().fail("表单生效时间为空");
        }
        if (taskRequest.getDeadline() == null) {
            return new Result.ResultBuilder().fail("表单截止时间为空");
        }
        if (taskRequest.getPublishTime().compareTo(new Date()) < 0) {
            return new Result.ResultBuilder().fail("表单生效时间必须为当前之后");
        }
        if (taskRequest.getDeadline().compareTo(new Date()) < 0) {
            return new Result.ResultBuilder().fail("表单截止时间必须为当前之后");
        }
        if (taskRequest.getDeadline().compareTo(taskRequest.getPublishTime()) < 0) {
            return new Result.ResultBuilder().fail("表单截止时间必须在表单生效时间之后");
        }
        if (taskRequest.getTitle().length() > 15) {
            return new Result.ResultBuilder().fail("表单名称长度不符合规范");
        }
        if (taskRequest.getDescription().length() > 30) {
            return new Result.ResultBuilder().fail("表单备注长度不符合规范");
        }
        Map<String, Object> map = new HashMap();
        map.put("id", id);
        map.put("title", taskRequest.getTitle());
        map.put("description", taskRequest.getDescription());
        map.put("publishTime", taskRequest.getPublishTime());
        map.put("deadline", taskRequest.getDeadline());
        taskMapper.updateTask(map);
        if (taskRequest.getUsers() != null && taskRequest.getUsers().length != 0) {
            taskMapper.deleteTaskUsers(id);
            taskMapper.insertTaskUsers(id, taskRequest.getUsers());
        }
        if (taskRequest.getFields() != null && taskRequest.getFields().length != 0) {
            //oldFields表示该表单已有字段集合
            List<Field> oldFields = fieldMapper.findFieldByTaskId(id);
            //newFields表示提交的字段集合
            AddFieldRequest[] addFieldRequests = taskRequest.getFields();
            //根据task id获取task
            Task task = taskMapper.taskInfo(id);
            for (AddFieldRequest fieldRequest : addFieldRequests) {
                //根据config id获取config
                Config config = configMapper.getConfigInfoById(fieldRequest.getConfigId());
                oldFields.remove(fieldRequest.toField(task, config));
            }
            for (Field oddField : oldFields) {
                fieldMapper.delete(oddField.getId());
            }
            for (AddFieldRequest fieldRequest2 : addFieldRequests) {
                Config config = configMapper.getConfigInfoById(fieldRequest2.getConfigId());
                if (fieldRequest2.getId() > 0) {
                    fieldMapper.updateFieldById(fieldRequest2.toField(task, config), fieldRequest2.getId());
                } else {
                    fieldMapper.save(fieldRequest2.toField(task, config));
                }
            }
        }
        return new Result.ResultBuilder().success("修改表单成功");
    }

    //删除表单
    public Result deleteTask(int id) {
        taskMapper.delete(id);
        return new Result.ResultBuilder().success("删除表单成功");
    }

    //收藏表单
    public Result addLikeTask(int id, User user) {
        Task task = taskMapper.taskInfo(id);
        if (task == null) {
            return new Result.ResultBuilder().fail(HttpStatus.NOT_FOUND.value(), "该task id不存在");
        }
        taskMapper.insertTaskLikes(id, user.getId());
        return new Result.ResultBuilder().success("收藏表单成功");
    }

    //取消收藏
    public Result deleteLikeTask(int id, User user) {
        Task task = taskMapper.taskInfo(id);
        if (task == null) {
            return new Result.ResultBuilder().fail(HttpStatus.NOT_FOUND.value(), "该task id不存在");
        }
        taskMapper.deleteTaskLikes(id, user.getId());
        return new Result.ResultBuilder().success("取消收藏成功");
    }

    //获取收藏列表
    public Result getAllLikeTasks(User user) {
        //PageHelper.startPage(page, pageSize);
        User userLikeTasks = userMapper.findAllWithLikeTask(user.getId());
        System.out.println("userLikeTasks.getLikeTasks().size" + userLikeTasks.getLikeTasks().size());
        Set<Task> likeTasks = userLikeTasks.getLikeTasks();
        return new Result.ResultBuilder().success("获取收藏列表成功", likeTasks);
    }
}
