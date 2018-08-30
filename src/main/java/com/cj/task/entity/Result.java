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

    public static class ResultBuilder {
        private int state;
        private String msg;
        private Object data;

        public int getState() {
            return state;
        }

        public ResultBuilder setState(int state) {
            this.state = state;
            return this;
        }

        public String getMsg() {
            return msg;
        }

        public ResultBuilder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public Object getData() {
            return data;
        }

        public ResultBuilder setData(Object data) {
            this.data = data;
            return this;
        }
        public Result success(String msg){
            return setMsg(msg).setState(200).build();
        }

        public Result success(String msg, Object data) {
            return setMsg(msg).setData(data).setState(200).build();
        }

        public Result fail(String msg) {
            return setMsg(msg).setState(400).build();
        }

        public Result fail(int state,String msg) {
            return setMsg(msg).setState(state).build();
        }

        public Result build() {
            return new Result(state, msg, data);
        }
    }
}
