package com.cj.task.mapper;

import com.cj.task.entity.Field;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by cj on 2018/9/4.
 */
public interface FieldMapper {
    int save(Field field);

    List<Field> findFieldByTaskId(int taskID);

    int delete(int id);

    int updateFieldById(@Param("field") Field field, @Param("id") int id);
}
