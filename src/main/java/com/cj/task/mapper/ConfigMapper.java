package com.cj.task.mapper;

import com.cj.task.entity.Config;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by cj on 2018/9/2.
 */
public interface ConfigMapper {
    int save(Config config);

    List<Config> configList();

    Config getConfigInfoById(int id);

    int updateConfig(@Param("name") String name, @Param("description") String description, @Param("expression") String expression, @Param("id") int id);

    //int updateConfig(Map map);
    int delete(int id);
}
