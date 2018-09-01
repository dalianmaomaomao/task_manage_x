package com.cj.task.entity.response;

import com.cj.task.entity.User;

/**
 * Created by cj on 2018/8/31.
 */
public class UserListResponse {
    public int id;
    public String account;
    public String nickName;
    public boolean isAdmin;

    public static UserListResponse wrap(User user) {
        UserListResponse userListResponse=new UserListResponse();
        userListResponse.id=user.getId();
        userListResponse.account=user.getAccount();
        userListResponse.nickName=user.getNickName();
        userListResponse.isAdmin=user.isAdmin();
        return userListResponse;
    }
}
