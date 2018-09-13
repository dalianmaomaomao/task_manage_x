package com.cj.task.mapper;

import com.cj.task.entity.Content;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by cj on 2018/9/9.
 */
public interface ContentMapper {
    int save(Content content);

    Content findById(int id);

    int update(Content content);

    int delete(Content content);

    int updateState(Content content);
    
}
