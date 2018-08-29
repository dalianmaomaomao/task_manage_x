package com.cj.task.aop;

import com.cj.task.entity.Result;
import com.cj.task.entity.User;
import com.cj.task.mapper.UserMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by cj on 2018/8/28.
 */
@Aspect
@Component
public class TokenAop {
    @Autowired
    HttpServletRequest request;
    @Autowired
    UserMapper userMapper;

    @Pointcut("execution(public * com.cj.task.controller.*.*(..))&&" + "@annotation(com.cj.task.annotation.TokenValid)")
    public void tokenPointCut() {
    }

    @Around("tokenPointCut()")
    public Object addTokenToMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            return Result.getFail(HttpStatus.BAD_REQUEST.value(), "token为空");
        }
        User user = userMapper.findUserByToken(token);
        if (user == null) {
            return Result.getFail(HttpStatus.NOT_FOUND.value(), "该token不存在");
        }
        injectUserObject(args, user);
        return joinPoint.proceed(args);//传入的新的参数去执行目标方法
    }

    private void injectUserObject(Object[] args, User user) {
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof User) {
                args[i] = user;
                break;
            }
        }
    }
}
