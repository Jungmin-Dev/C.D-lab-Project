package com.project.mapper;

import com.project.domain.Content;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ContentMapper {
    public List<Content> contentList() throws Exception;
    public List<Content> contentDetail(HashMap<String, Object> param) throws Exception;
    public List<Content> contentComment(HashMap<String, Object> param) throws Exception;
    public List<Content> contentSubComment(HashMap<String, Object> param) throws Exception;
    public int contentInsert(Map<String, Object> param) throws Exception;
    public int contentDelete(String param) throws Exception;
    public int contentUpdate(Map<String, Object> param) throws Exception;
    public int commentSubCreate(HashMap<String, Object> param) throws Exception;
    public int commentCreate(HashMap<String, Object> param) throws Exception;
    public int commentDelete(HashMap<String, Object> param) throws Exception;
    public int subCommentDelete(HashMap<String, Object> param) throws Exception;
    public int commentUpdate(HashMap<String, Object> param) throws Exception;
    public int subCommentUpdate(HashMap<String, Object> param) throws Exception;
    public int fileInfo(Map<String, Object> param) throws Exception;
    public int fileInfoContentId() throws Exception;
    public List<String> fileDeleteFind(String param) throws Exception;
    public int fileDelete(String[] param) throws Exception;
}
