package com.cj.task.mapper;

import com.cj.task.entity.Content;
import com.cj.task.entity.ContentItem;

import java.util.List;

/**
 * Created by cj on 2018/9/9.
 */
public interface ContentItemMapper {
    int save(ContentItem contentItem);

    int update(ContentItem contentItem);

    int delete(ContentItem contentItem);

    List<ContentItem> findContentItems(int conId);
}
