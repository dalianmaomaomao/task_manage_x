package com.cj.task.annotation;

import java.lang.annotation.*;

/**
 * Created by cj on 2018/8/29.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenValid {
}
