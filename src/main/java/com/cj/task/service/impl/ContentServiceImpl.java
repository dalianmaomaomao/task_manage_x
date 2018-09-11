package com.cj.task.service.impl;

import com.cj.task.entity.Content;
import com.cj.task.entity.Result;
import com.cj.task.mapper.ContentMapper;
import com.cj.task.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cj on 2018/9/11.
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    ContentMapper contentMapper;

    public Result verifyContent(int cid, boolean pass) {
        Content content = contentMapper.findById(cid);
        if (content == null) {
            return new Result.ResultBuilder().fail("该表单详情不存在");
        }
        if (content.getState() != 0) {
            return new Result.ResultBuilder().fail("该表单已审核过");
        }
        content.setState(pass ? 1 : -1);
        contentMapper.updateState(content);
        return new Result.ResultBuilder().success("审核成功");
    }
}
