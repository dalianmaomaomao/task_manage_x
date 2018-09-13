package com.cj.task.service;

import com.cj.task.entity.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by cj on 2018/9/11.
 */
@Service
public interface ContentService {
    Result verifyContent(int cid, boolean pass);

    ResponseEntity exportTaskContents(int tid);
}
