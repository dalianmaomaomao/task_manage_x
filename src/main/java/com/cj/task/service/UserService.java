package com.cj.task.service;

import com.cj.task.entity.Result;
import com.cj.task.entity.User;
import org.springframework.stereotype.Service;

/**
 * Created by cj on 2018/8/26.
 */
@Service
public interface UserService {
    Result registerUser(String account, String pwd, String nickName);

    Result login(String account, String pwd);

    Result findUserByToken(String token);

    Result updateUserinfo(String nickName, int id);

    Result updatePwd(String oldPwd, String newPwd, User user);

    Result userList(int page, int pageSize);

    Result findUserById(int id);

    Result deleteUser(int id);
}
