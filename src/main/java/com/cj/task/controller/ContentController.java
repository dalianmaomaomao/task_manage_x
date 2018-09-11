package com.cj.task.controller;

import com.cj.task.annotation.AdminValid;
import com.cj.task.entity.Result;
import com.cj.task.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cj on 2018/9/9.
 */
@RestController
@RequestMapping("/")
public class ContentController {
    @Autowired
    ContentService contentService;

    //管理员审核内容
    @AdminValid
    @PutMapping("contents/{cid}")
    public ResponseEntity verifyContent(@PathVariable int cid, @RequestParam boolean pass) {
        Result result = contentService.verifyContent(cid, pass);
        return ResponseEntity.ok(result);
    }
}
