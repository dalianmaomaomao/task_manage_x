package com.cj.task.entity;

import java.util.List;
import java.util.Set;

/**
 * Created by cj on 2018/8/25.
 */

public class User {
    private int id;
    private String account;
    private String pwd;
    private String nickName;
    private String token;
    private boolean isAdmin;
    private Set<Task> likeTasks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Set<Task> getLikeTasks() {
        return likeTasks;
    }

    public void setLikeTasks(Set<Task> likeTasks) {
        this.likeTasks = likeTasks;
    }
}
