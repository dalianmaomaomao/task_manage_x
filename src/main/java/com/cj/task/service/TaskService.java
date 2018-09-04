package com.cj.task.service;

import com.cj.task.entity.Result;
import com.cj.task.entity.request.AddTaskRequest;
import org.springframework.stereotype.Service;

/**
 * Created by cj on 2018/9/3.
 */
@Service
public interface TaskService {
    Result createTask(AddTaskRequest taskRequest);

    Result taskList(int page, int pageSize);

    Result taskInfo(int id);

    Result updateTask(int id, AddTaskRequest taskRequest);

    Result deleteTask(int id);
}
