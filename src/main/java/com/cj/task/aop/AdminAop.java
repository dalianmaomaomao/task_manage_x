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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by cj on 2018/8/31.
 */
@Aspect
@Component
public class AdminAop {
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    UserMapper userMapper;

    @Pointcut("execution(public * com.cj.task.controller.*.*(..))&&" + "@annotation(com.cj.task.annotation.AdminValid)")
    public void tokenPointCut() {
    }

    @Around("tokenPointCut()")
    public Object addTokenToMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        String token = httpServletRequest.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            return ResponseEntity.badRequest().body(new Result.ResultBuilder().fail("token为空"));
        }
        User user = userMapper.findUserByToken(token);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result.ResultBuilder().fail(HttpStatus.NOT_FOUND.value(), "该token不存在"));
        }
        if (!user.isAdmin()) {
            System.out.println("user.isAdmin: " + user.isAdmin());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Result.ResultBuilder().fail(HttpStatus.FORBIDDEN.value(), "该用户无管理员权限"));
        }
        injectUserObject(user,args);
        return proceedingJoinPoint.proceed(args);
    }
    private void injectUserObject(User asUser,Object[] args){
        for(int i=0;i<args.length;i++){
            if(args[i] instanceof User){
                args[i]=asUser;
                break;
            }
        }
    }
}
