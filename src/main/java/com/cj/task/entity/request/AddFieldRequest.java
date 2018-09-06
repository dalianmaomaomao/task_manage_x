package com.cj.task.entity.request;

import com.cj.task.entity.Config;
import com.cj.task.entity.Field;
import com.cj.task.entity.Task;

/**
 * Created by cj on 2018/9/4.
 */
public class AddFieldRequest {
    private int id;
    private String name;
    private String description;
    private int configId;

    public Field toField(Task task, Config config) {
        Field field = new Field();
        field.setId(id);
        field.setName(name);
        field.setDescription(description);
        field.setTask(task);
        field.setConfig(config);
        return field;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }
}
