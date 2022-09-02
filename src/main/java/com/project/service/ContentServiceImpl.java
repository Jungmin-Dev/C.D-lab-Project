package com.project.service;

import com.project.domain.Content;
import com.project.domain.FileDownLoad;
import com.project.mapper.ContentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RequiredArgsConstructor
@Log
@Service
public class ContentServiceImpl implements ContentService{
    private final ContentMapper contentMapper;

    // 파일 서버 경로
    String fileRoot = "/home/oracle/FILE_SERVER/";

    @Override
    // 댓글 삭제
    public int commentDelete(String param) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("commentId", param);
        return contentMapper.commentDelete(map);
    }

    @Override
    // 댓글 수정
    public int commentUpdate(Content param) throws Exception {
        HashMap<String, Object> map = DataMapping(param);
        return contentMapper.commentUpdate(map);
    }

    @Override
    // 대댓글 삭제
    public int subCommentDelete(String param) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("subCommentId", param);
        return contentMapper.subCommentDelete(map);
    }

    @Override
    // 대댓글 수정
    public int subCommentUpdate(Content param) throws Exception {
        HashMap<String, Object> map = DataMapping(param);
        return contentMapper.subCommentUpdate(map);
    }

    @Override
    // 파일 다운로드
    public ResponseEntity<byte[]> fileDownLoad(FileDownLoad param) throws Exception {
        // 확장자 찾기
        Path path = Paths.get(fileRoot + param.getUuid());
        String contentType = Files.probeContentType(path);

        //서버의 파일을 다운로드하기 위한 스트림
        InputStream in = null;
        ResponseEntity<byte[]> entity = null;
        try {
            // 헤더 구성성 객체
            HttpHeaders headers = new HttpHeaders();
            // InputStream 생성
            in = new FileInputStream(fileRoot + param.getUuid());
            // 파일 타입
            headers.add(HttpHeaders.CONTENT_TYPE, contentType);
            // 바이트 배열, 헤더
            entity = new ResponseEntity<>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            if (in != null)
                in.close(); // 스트림 닫기
        }
        return entity;
    }

    @Override
    // 댓글 작성
    public int commentCreate(Content param) throws Exception {
        HashMap<String, Object> map = DataMapping(param);
        return contentMapper.commentCreate(map);
    }

    @Override
    // 대댓글 작성
    public int commentSubCreate(Content param) throws Exception {
        HashMap<String, Object> map = DataMapping(param);
        return contentMapper.commentSubCreate(map);
    }

    @Override
    // 게시판 수정
    public int contentUpdate(MultipartHttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception {
        // 디렉토리 없을 경우 디렉토리 생성
        File Folder = new File(fileRoot);
        fileCreate(Folder);

        // ** start ** 수정 된 파일 중 삭제된 파일 삭제
        List<String> detailFile = new ArrayList<>();
        String[] temp = param.get("detailFile").toString().split(",");
        for(String item : temp){
            detailFile.add(item);
        }
        Iterator<String> fileNames = request.getFileNames();
        int contentId = Integer.parseInt(param.get("contentId").toString());
        for(String str : detailFile) {
            File file = new File(fileRoot + str);
            if (file.exists() == true && str.length() > 0) {
                file.delete();
            }
        }
        contentMapper.fileDelete(temp);
        // ** end ** 수정 된 파일 중 삭제된 파일 삭제

        // 파일 업로드
        fileServerInsert(fileNames, contentId, request);

        return contentMapper.contentUpdate(param);
    }

    @Override
    // 게시글 작성
    public int contentInsert(MultipartHttpServletRequest request, Map<String, Object> param) throws Exception {
        // 디렉토리 없을 경우 디렉토리 생성
        File Folder = new File(fileRoot);
        fileCreate(Folder);
        contentMapper.contentInsert(param);
        Iterator<String> fileNames = request.getFileNames();
        // DB 시퀀스 번호 가져오기
        int contentId = contentMapper.fileInfoContentId();
        // DB 시퀀스 번호 맞추기 위해 -1
        fileServerInsert(fileNames, contentId-1, request);
        return 1;
    }

    @Override
    // 게시글 삭제
    public int contentDelete(String param) throws Exception {
        List<String> list = contentMapper.fileDeleteFind(param);
        for(String str : list) {
            File file = new File(fileRoot + str);
            if (file.exists() == true) {
                file.delete();
            }
        }
        return contentMapper.contentDelete(param);
    }

    @Override
    // 게시글 목록 가져오기
    public List<Content> contentList() throws Exception {
        return contentMapper.contentList();
    }

    @Override
    // 게시글 내용 자세히 보기(게시글 클릭 시)
    public List<Content> contentDetail(String param) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("contentId", param);
        return contentMapper.contentDetail(map);
    }

    @Override
    // 게시글 댓글 가져오기
    public List<Content> contentComment(String param) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("contentId", param);
        return contentMapper.contentComment(map);
    }

    @Override
    // 게시글 대댓글 가져오기
    public List<Content> contentCommentSub(String param) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("contentId", param);
        return contentMapper.contentSubComment(map);
    }

    // 디렉토리 없을 경우 디렉토리 생성
    public void fileCreate(File file){
        if (!file.exists())
        {
            try {
                //폴더 생성
                file.mkdir();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }
    // 파일 서버에 파일 업로드
    public void fileServerInsert(Iterator<String> fileNames, int contentId, MultipartHttpServletRequest request){
        while(fileNames.hasNext()){
            String fileName = fileNames.next();
            List<MultipartFile> mFile = request.getFiles(fileName);
            for (MultipartFile mf : mFile){
                Map<String ,Object> fileUpLoad = new HashMap<>();
                String prefix = mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf(".") +1 , mf.getOriginalFilename().length());
                UUID uuid = UUID.randomUUID();
                String originalFilename = mf.getOriginalFilename();
                fileUpLoad.put("contentId", contentId);
                fileUpLoad.put("uuid", uuid + "." + prefix);
                fileUpLoad.put("fileName", originalFilename);
                fileUpLoad.put("fileSize", mf.getSize());
                try {
                    contentMapper.fileInfo(fileUpLoad);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                String safeFile = fileRoot + uuid + "." + prefix;
                try {
                    mf.transferTo(new File(safeFile));
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // DB에 사용될 데이터 맵핑
    public HashMap<String, Object> DataMapping(Content param){
        HashMap<String, Object> map = new HashMap<>();
        map.put("userEmail", param.getUserEmail());
        map.put("context", param.getContext());
        map.put("contentId", param.getContentId());
        map.put("commentId", param.getCommentId());
        map.put("subCommentId", param.getSubCommentId());
        return map;
    }
}
