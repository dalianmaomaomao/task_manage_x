package com.cj.task.service.impl;

import com.cj.task.entity.Content;
import com.cj.task.entity.Result;
import com.cj.task.entity.Task;
import com.cj.task.mapper.ContentMapper;
import com.cj.task.mapper.TaskMapper;
import com.cj.task.service.ContentService;
import com.cj.task.utils.TaskExportUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.support.TaskUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by cj on 2018/9/11.
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    ContentMapper contentMapper;
    @Autowired
    TaskMapper taskMapper;
    @Autowired
    TaskExportUtils taskExportUtils;

    //审核内容
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

    //导出
    public ResponseEntity<Resource> exportTaskContents(int tid) {
        Task task = taskMapper.taskInfo(tid);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        String fileName = task.getTitle() + ".xls";
        HSSFWorkbook workbook = taskExportUtils.exportTaskExcel(task);
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try {
            workbook.write(arrayOutputStream);
            workbook.close();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Cache-Control", "no-cache, no-store, must-revalidate");
            httpHeaders.add("Pragma", "no-cache");
            httpHeaders.add("Expires", "0");
            httpHeaders.add("charset", "utf-8");
            fileName = URLEncoder.encode(fileName, "UTF-8");
            httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + fileName + "\"");
            Resource resource = new InputStreamResource(new ByteArrayInputStream(arrayOutputStream.toByteArray()));
            return ResponseEntity.ok().headers(httpHeaders)
                    .contentType(MediaType.parseMediaType("application/x-msdownload"))
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
            return ResponseEntity.badRequest().build();
    }
}
