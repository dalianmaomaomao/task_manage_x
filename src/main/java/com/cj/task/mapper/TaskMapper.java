package com.cj.task.mapper;

import com.cj.task.entity.Task;
import com.cj.task.entity.request.AddTaskRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by cj on 2018/9/3.
 */
public interface TaskMapper {
    int save(Task task);

    List<Task> getTasks();

    Task taskInfo(int id);

    int updateTask(Map map);

    int delete(int id);
}
