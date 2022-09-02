package com.project.service;

import com.project.domain.Content;
import com.project.domain.FileDownLoad;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Map;


public interface ContentService {
    public List<Content> contentList() throws Exception;
    public List<Content> contentDetail(String param) throws Exception;
    public List<Content> contentComment(String param) throws Exception;
    public List<Content> contentCommentSub(String param) throws Exception;
    public int contentInsert(MultipartHttpServletRequest request, Map<String, Object> param) throws Exception;
    public int contentDelete(String param) throws Exception;
    public int contentUpdate(MultipartHttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception;
    public int commentCreate(Content param) throws Exception;
    public int commentSubCreate(Content param) throws Exception;
    public int commentDelete(String param) throws Exception;
    public int commentUpdate(Content param) throws Exception;
    public int subCommentDelete(String param) throws Exception;
    public int subCommentUpdate(Content param) throws Exception;
    public ResponseEntity<byte[]> fileDownLoad(FileDownLoad param) throws Exception;
}
