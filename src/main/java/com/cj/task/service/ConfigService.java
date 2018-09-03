package com.cj.task.service;

import com.cj.task.entity.Result;
import org.springframework.stereotype.Service;

/**
 * Created by cj on 2018/9/2.
 */
@Service
public interface ConfigService {
    Result createConfig(String name, String description, String expression);

    Result configList(int page, int pageSize);

    Result getConfigInfo(int id);

    Result updateConfig(String name, String description, String expression, int id);

    Result deleteConfig(int id);

}
