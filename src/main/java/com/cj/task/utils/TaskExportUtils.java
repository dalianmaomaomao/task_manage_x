package com.cj.task.utils;

import com.cj.task.entity.Content;
import com.cj.task.entity.ContentItem;
import com.cj.task.entity.Field;
import com.cj.task.entity.Task;
import com.cj.task.mapper.ContentItemMapper;
import com.cj.task.mapper.ContentMapper;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by cj on 2018/9/12.
 */
@Service
public class TaskExportUtils {
    @Autowired
    ContentItemMapper contentItemMapper;
    @Autowired
    ContentMapper contentMapper;

    public void addCell(HSSFRow row, int index, String value) {
        HSSFCell cell = row.createCell(index);
        cell.setCellValue(value);
    }

    public HSSFWorkbook exportTaskExcel(Task task) {
        //创建一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个工作表
        //添加表头
        List<String> heads = new ArrayList<>();
        heads.add("序号");
        heads.add("用户");
        heads.add("提交时间");
        heads.add("审核状态");
        for (Field field : task.getFields()) {
            heads.add(field.getName());
        }
        HSSFSheet sheet = workbook.createSheet(task.getTitle());
        //增加前几行标题、描述、发布时间、截止时间
        HSSFRow row = sheet.createRow(0);
        addCell(row, 0, task.getTitle());
        CellRangeAddress rangeAddress = new CellRangeAddress(0, 0, 0, heads.size() - 1);
        sheet.addMergedRegion(rangeAddress);
        row = sheet.createRow(1);
        System.out.println("task.getDescription()  " + task.getDescription());
        addCell(row, 0, task.getDescription());
        rangeAddress = new CellRangeAddress(1, 1, 0, heads.size() - 1);
        sheet.addMergedRegion(rangeAddress);
        row = sheet.createRow(2);
        addCell(row, 0, "发布时间");
        addCell(row, 1, DateUtils.format(task.getPublishTime()));
        rangeAddress = new CellRangeAddress(2, 2, 1, 2);
        sheet.addMergedRegion(rangeAddress);
        row = sheet.createRow(3);
        addCell(row, 0, "截止时间");
        addCell(row, 1, DateUtils.format(task.getDeadline()));
        rangeAddress = new CellRangeAddress(3, 3, 1, 2);
        sheet.addMergedRegion(rangeAddress);


        row = sheet.createRow(4);
        for (int i = 0; i < heads.size(); i++) {
            addCell(row, i, heads.get(i));
        }
        //添加表内容
        Set<Content> contents = task.getContents();
        int rowIndex = 5;
        System.out.println("tcontents  " + contents.size());
        for (Content content : contents) {
            HSSFRow rowItem = sheet.createRow(rowIndex);
            addCell(rowItem, 0, String.valueOf(rowIndex - 4));//序号
            System.out.println("contentMapper " + contentMapper);
            Content contentWithUser = contentMapper.findById(content.getId());
            System.out.println("contentWithUser.getUser().getNickName()  " + contentWithUser.getUser().getNickName());
            addCell(rowItem, 1, contentWithUser.getUser().getNickName());//用户
            addCell(rowItem, 2, DateUtils.format(content.getUpdateAt()));//更新时间
            addCell(rowItem, 3, content.getStateStr(content.getState()));//审核状态
            List<ContentItem> contentItems = contentItemMapper.findContentItems(content.getId());
            int j = 3;
            for (Field field : task.getFields()) {
                System.out.println("task.getFields().size()  " + task.getFields().size());
                for (int i = 0; i < contentItems.size(); i++) {
                    if (field.getId() == contentItems.get(i).getField().getId()) {
                        System.out.println("contentItems.get(i) " + contentItems.get(i).getValue());
                        addCell(rowItem, ++j, contentItems.get(i).getValue());
                        break;
                    }
                }

            }
            rowIndex++;
        }
        return workbook;
    }
}
