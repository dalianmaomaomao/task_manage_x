package com.cj.task.mapper;

import com.cj.task.entity.Content;

/**
 * Created by cj on 2018/9/9.
 */
public interface ContentMapper {
    int save(Content content);

    Content findById(int id);

    int update(Content content);

    int delete(Content content);

}
