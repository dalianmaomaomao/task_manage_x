package com.cj.task.service.impl;

import com.cj.task.entity.*;
import com.cj.task.entity.request.AddContentRequest;
import com.cj.task.entity.request.AddFieldRequest;
import com.cj.task.entity.request.AddTaskRequest;
import com.cj.task.mapper.*;
import com.cj.task.service.TaskService;
import com.cj.task.utils.RegexUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
    @Autowired
    ContentMapper contentMapper;
    @Autowired
    ContentItemMapper contentItemMapper;

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

    //提交表单内容
    public Result addTaskContents(int id, AddContentRequest contentRequest, User user) {
        Task task = taskMapper.taskInfo(id);
        if (task == null) {
            return new Result.ResultBuilder().fail("表单不存在");
        }
        Set<Field> fields = task.getFields();
        if (fields.size() != contentRequest.getValues().size()) {
            return new Result.ResultBuilder().fail("请完整提交");
        }
        for (AddContentRequest.ValuesBean bean : contentRequest.getValues()) {
            int fieldId = bean.getFieldId();
            Field field = fieldMapper.findById(fieldId);
            Config config = field.getConfig();
            System.out.println("config id: " + config);
            if (!RegexUtils.isMatch(config.getExpression(), bean.getValue())) {
                return new Result.ResultBuilder().fail("您输入的内容不符合正则表达式规则");
            }
        }

        Content content = new Content();
        content.setUpdateAt(new Date());
        content.setState(0);
        content.setUser(user);
        content.setTask(task);
        contentMapper.save(content);

        for (AddContentRequest.ValuesBean bean : contentRequest.getValues()) {
            int fieldId = bean.getFieldId();
            Field field = fieldMapper.findById(fieldId);
            ContentItem contentItem = new ContentItem();
            contentItem.setValue(bean.getValue());
            contentItem.setContent(content);
            contentItem.setField(field);
            contentItemMapper.save(contentItem);
        }
        return new Result.ResultBuilder().success("提交成功");
    }

    //修改提交的表单内容
    public Result updateTaskContents(int taskId, int contentId, AddContentRequest contentRequest, User user) {
        Task task = taskMapper.taskInfo(taskId);
        Content content = contentMapper.findById(contentId);
        if (task == null || content == null) {
            return new Result.ResultBuilder().fail("表单或表单描述为空");
        }
        if (!user.equals(content.getUser())) {
            return new Result.ResultBuilder().fail(HttpStatus.FORBIDDEN.value(), "用户无权限修改");
        }
        if (task.getDeadline().compareTo(new Date()) <= 0) {
            return new Result.ResultBuilder().fail("超过截止时间，无法提交");
        }
        if (content.getState() != 0) {
            return new Result.ResultBuilder().fail("该内容已通过审核，无法修改");
        }
        for (AddContentRequest.ValuesBean bean : contentRequest.getValues()) {
            int fieldId = bean.getFieldId();
            if (fieldId <= 0) {
                return new Result.ResultBuilder().fail("该字段不存在");
            }
            Field field = fieldMapper.findById(fieldId);
            if (field == null || !task.getFields().contains(field)) {
                return new Result.ResultBuilder().fail("该字段不存在");
            }
            if (StringUtils.isEmpty(bean.getValue())) {
                return new Result.ResultBuilder().fail("输入的内容不能为空");
            }
            Config config = field.getConfig();
            if (!RegexUtils.isMatch(config.getExpression(), bean.getValue())) {
                return new Result.ResultBuilder().fail("您输入的内容不符合正则表达式规则");
            }
        }
        content.setUpdateAt(new Date());
        contentMapper.update(content);
        for (AddContentRequest.ValuesBean bean : contentRequest.getValues()) {
            Field field = fieldMapper.findById(bean.getFieldId());
            ContentItem contentItem = new ContentItem();
            contentItem.setValue(bean.getValue());
            contentItem.setField(field);
            contentItem.setContent(content);
            contentItemMapper.update(contentItem);
        }
        return new Result.ResultBuilder().success("修改用户提交内容成功");
    }

    //删除某个用户提交的某条内容
    public Result deleteTaskContents(int taskId, int contentId, User user) {
        Task task = taskMapper.taskInfo(taskId);
        Content content = contentMapper.findById(contentId);
        if (task == null || content == null) {
            return new Result.ResultBuilder().fail("表单或表单描述为空");
        }
        if (!user.equals(content.getUser())) {
            return new Result.ResultBuilder().fail(HttpStatus.FORBIDDEN.value(), "用户无权限修改");
        }
        contentMapper.delete(content);
        ContentItem contentItem = new ContentItem();
        contentItem.setContent(content);
        contentItemMapper.delete(contentItem);
        return new Result.ResultBuilder().success("删除用户提交内容成功");
    }
}
