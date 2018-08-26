package com.cj.task.service;

import com.cj.task.entity.Result;
import org.springframework.stereotype.Service;

/**
 * Created by cj on 2018/8/26.
 */
@Service
public interface UserService {
    Result registerUser(String account, String pwd, String nickName);
    Result login(String account,String pwd);
}
