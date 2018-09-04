package com.cj.task.entity.request;

import com.cj.task.entity.Task;

import java.util.Date;

/**
 * Created by cj on 2018/9/3.
 */
public class AddTaskRequest {
    private String title;
    private String description;
    private Date publishTime;
    private Date deadline;

    public Task toTask() {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setPublishTime(publishTime);
        task.setDeadline(deadline);
        return task;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
