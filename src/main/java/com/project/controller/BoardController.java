package com.project.controller;

import com.project.domain.Info;
import com.project.service.AuthService;
import com.project.service.ContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Controller
@Api(tags = "로그인 관련 Controller")
@CrossOrigin(origins="*")


//1. 홈페이지에 이미지 리스트 영역 이미지 등록, 수정, 삭제 기능 - 이미지 테이블(번호, 이미지 이름, 이미지 가명, 등록 일자, 수정 일자)
//2. 게시물(a태그로 링크이동, 텍스트내용, 날짜) 기입되게 등록, 수정 삭제 지정 기능(번호, 게시글 내용, 링크, 날짜, 페이징)
public class BoardController {
    private final AuthService authService;
    private final ContentService contentService;

    @ApiOperation(value = "게시글 목록 가져오기", notes = "게시글 목록 가져오기")
    @RequestMapping(value="/linkList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> linkList() throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("contentList",contentService.contentList());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @ApiOperation(value = "이미지 목록 가져오기", notes = "이미지 목록 가져오기")
    @RequestMapping(value="/imgList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> imgList() throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("contentList",contentService.contentList());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @ApiOperation(value = "이미지 등록", notes = "이미지 등록")
    @RequestMapping(value="/imgInsert", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> imgInsert(MultipartHttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception{
        contentService.contentInsert(request, param);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "이미지 삭제", notes = "이미지 삭제")
    @RequestMapping(value="/imgDelete/{contentId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> imgDelete(@PathVariable String contentId) throws Exception{
        contentService.contentDelete(contentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "게시글 등록", notes = "게시글 등록")
    @RequestMapping(value="/linkInsert", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> linkInsert(MultipartHttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception{
        contentService.contentInsert(request, param);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글 수정")
    @RequestMapping(value="/linkUpdate/{contentId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> linkUpdate(@PathVariable String contentId) throws Exception{
        contentService.contentDelete(contentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "게시글 삭제", notes = "게시글 삭제")
    @RequestMapping(value="/linkDelete/{contentId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> linkDelete(@PathVariable String contentId) throws Exception{
        contentService.contentDelete(contentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
