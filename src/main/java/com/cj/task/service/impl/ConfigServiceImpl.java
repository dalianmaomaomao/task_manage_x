package com.cj.task.service.impl;

import com.cj.task.entity.Config;
import com.cj.task.entity.Result;
import com.cj.task.mapper.ConfigMapper;
import com.cj.task.service.ConfigService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cj on 2018/9/2.
 */
@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    ConfigMapper configMapper;

    public Result createConfig(String name, String description, String expression) {
        if (StringUtils.isEmpty(name) || name.length() > 15) {
            return new Result.ResultBuilder().fail("您输入的规则名称长度不符合规范");
        }
        if (StringUtils.isEmpty(description) || description.length() > 30) {
            return new Result.ResultBuilder().fail("您输入的描述为空");
        }
        if (StringUtils.isEmpty(expression) || expression.length() > 64) {
            return new Result.ResultBuilder().fail("您输入的正则表达式为空");
        }
        Config config = new Config();
        config.setName(name);
        config.setDescription(description);
        config.setExpression(expression);
        configMapper.save(config);
        return new Result.ResultBuilder().success("新增规则成功", config);
    }

    public Result configList(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Config> configList = configMapper.configList();
        return new Result.ResultBuilder().success("获取规则列表成功", configList);
    }

    public Result getConfigInfo(int id) {
        Config config = configMapper.getConfigInfoById(id);
        return new Result.ResultBuilder().success("获取规则详情成功", config);
    }

    public Result updateConfig(String name, String description, String expression, int id) {
        if (StringUtils.isEmpty(name) || name.length() > 15) {
            return new Result.ResultBuilder().fail("您输入的规则名称长度不符合规范");
        }
        if (StringUtils.isEmpty(description) || description.length() > 30) {
            return new Result.ResultBuilder().fail("您输入的描述为空");
        }
        if (StringUtils.isEmpty(expression) || expression.length() > 64) {
            return new Result.ResultBuilder().fail("您输入的正则表达式为空");
        }
//        Map<String,Object> map=new HashMap();
//        map.put("name",name);
//        map.put("description",description);
//        map.put("expression",expression);
//        map.put("id",id);
        configMapper.updateConfig(name, description, expression, id);
        return new Result.ResultBuilder().success("修改规则成功");
    }

    public Result deleteConfig(int id) {
        configMapper.delete(id);
        return new Result.ResultBuilder().success("删除规则成功");
    }
}
