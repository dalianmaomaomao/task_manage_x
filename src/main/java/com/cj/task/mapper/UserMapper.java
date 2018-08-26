package com.cj.task.mapper;

import com.cj.task.entity.User;

/**
 * Created by cj on 2018/8/26.
 */
public interface UserMapper {
    User findUserByAccount(String account);

    int save(User user);
}
