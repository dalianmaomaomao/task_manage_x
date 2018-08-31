package com.cj.task.entity.response;

import com.cj.task.entity.User;

/**
 * Created by cj on 2018/8/31.
 */
public class UserResponse {
    public int id;
    public String account;
    public String nickName;
    public String token;
    public boolean isAdmin;

    public static UserResponse wrap(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.id = user.getId();
        userResponse.account = user.getAccount();
        userResponse.nickName = user.getNickName();
        userResponse.token = user.getToken();
        userResponse.isAdmin = user.isAdmin();
        return userResponse;
    }
}
