package com.cj.task.entity;

/**
 * Created by cj on 2018/8/26.
 */
public class LoginResponse {
    public int id;
    public String account;
    public String nickName;
    public String token;
    public boolean isAdmin;

    public static LoginResponse wrap(User user) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.id = user.getId();
        loginResponse.account = user.getAccount();
        loginResponse.nickName = user.getNickName();
        loginResponse.token = user.getToken();
        loginResponse.isAdmin = user.isAdmin();
        return loginResponse;
    }
}
