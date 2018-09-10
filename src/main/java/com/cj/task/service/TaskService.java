package com.cj.task.service;

import com.cj.task.entity.Result;
import com.cj.task.entity.User;
import com.cj.task.entity.request.AddContentRequest;
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

    Result addLikeTask(int id, User user);

    Result deleteLikeTask(int id, User user);

    Result getAllLikeTasks(User user);

    Result addTaskContents(int id, AddContentRequest contentRequest, User user);

    Result updateTaskContents(int taskId, int contentId, AddContentRequest contentRequest, User user);

    Result deleteTaskContents(int taskId, int contentId, User user);
}
