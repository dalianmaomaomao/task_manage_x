package com.cj.task.mapper;

import com.cj.task.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by cj on 2018/8/26.
 */
public interface UserMapper {
    User findUserByAccount(String account);

    int save(User user);

    User findUserByAccountAndPwd(@Param("account") String account, @Param("pwd") String pwd);

    int updateUserByToken(User user);

    User findUserByToken(String token);

}
