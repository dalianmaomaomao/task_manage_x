package com.cj.task.service.impl;

import com.cj.task.entity.Result;
import com.cj.task.entity.Task;
import com.cj.task.entity.request.AddTaskRequest;
import com.cj.task.mapper.TaskMapper;
import com.cj.task.mapper.UserMapper;
import com.cj.task.service.TaskService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cj on 2018/9/3.
 */
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskMapper taskMapper;

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
        if (taskRequest.getDeadline() == null) {
            return new Result.ResultBuilder().fail("表单截止时间为空");
        }
        if (taskRequest.getTitle().length() > 15) {
            return new Result.ResultBuilder().fail("表单名称长度不符合规范");
        }
        if (taskRequest.getDescription().length() > 30) {
            return new Result.ResultBuilder().fail("表单备注长度不符合规范");
        }
        taskMapper.save(taskRequest.toTask());
        return new Result.ResultBuilder().success("表单创建成功", taskRequest);
    }

    public Result taskList(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Task> taskList = taskMapper.getTasks();
        return new Result.ResultBuilder().success("获取表单列表成功", taskList);
    }

    public Result taskInfo(int id) {
        Task task = taskMapper.taskInfo(id);
        return new Result.ResultBuilder().success("获取表单详情成功", task);
    }

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
        if (taskRequest.getTitle().length() > 15) {
            return new Result.ResultBuilder().fail("表单名称长度不符合规范");
        }
        if (taskRequest.getDescription().length() > 30) {
            return new Result.ResultBuilder().fail("表单备注长度不符合规范");
        }
        Map<String,Object> map=new HashMap();
        map.put("id", id);
        map.put("title",taskRequest.getTitle());
        map.put("description",taskRequest.getDescription());
        map.put("publishTime",taskRequest.getPublishTime());
        map.put("deadline",taskRequest.getDeadline());
        taskMapper.updateTask(map);
        return new Result.ResultBuilder().success("修改表单成功",taskRequest);
    }
    public Result deleteTask(int id){
        taskMapper.delete(id);
        return new Result.ResultBuilder().success("删除表单成功");
    }
}
