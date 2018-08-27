package com.cj.task.entity;

import java.io.Serializable;

/**
 * Created by cj on 2018/8/26.
 */
public class Result implements Serializable {
    private int state;
    private String msg;
    private Object data;

    private Result(int state, String msg, Object data) {
        this.state = state;
        this.msg = msg;
        this.data = data;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Result getResult(int state, String msg, Object data) {
        return new Result(state, msg, data);
    }

    public static Result getSuccess(int state, String msg, Object data) {
        return getResult(state, msg, data);
    }

    public static Result getFail(int state, String msg) {
        return getResult(state, msg, null);
    }
}
