package com.cj.task.mapper;

import com.cj.task.entity.Task;
import com.cj.task.entity.User;
import com.cj.task.entity.request.AddTaskRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by cj on 2018/9/3.
 */
public interface TaskMapper {
    int save(Task task);

    List<Task> getTasks();

    Task taskInfo(int id);

    int updateTask(Map map);

    int delete(int id);

    int insertTaskUsers(@Param("taskID") int taskId, @Param("users") int[] users);

    int insertTaskLikes(@Param("taskID") int taskID, @Param("userID") int userID);

    int deleteTaskLikes(@Param("taskID") int taskID, @Param("userID") int userID);

    int deleteTaskUsers(@Param("taskID") int taskID);

}
