package com.project.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.project.domain.Content;
import com.project.domain.FileDownLoad;
import com.project.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping(value = "/content")
@Controller
@CrossOrigin(origins="*")
@Api(tags = "게시판 관련 Controller")
public class ContentController {

    private final ContentService contentService;

    @ApiOperation(value = "게시글 목록 가져오기", notes = "게시글 목록 가져오기")
    @RequestMapping(value="/list", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> list() throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("contentList",contentService.contentList());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @ApiOperation(value = "게시글 내용 자세히 보기(게시글 클릭 시)", notes = "게시글 내용 자세히 보기(게시글 클릭 시)")
    @RequestMapping(value="/detail/{contentId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> detail(@PathVariable String contentId) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("contentList",contentService.contentDetail(contentId));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @ApiOperation(value = "게시글 댓글 가져오기", notes = "게시글 댓글 가져오기")
    @RequestMapping(value="/comment/{contentId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> comment(@PathVariable String contentId) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("contentCommentList",contentService.contentComment(contentId));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @ApiOperation(value = "게시글 대댓글 가져오기", notes = "게시글 대댓글 가져오기")
    @RequestMapping(value="/commentsub/{contentId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> commentSub(@PathVariable String contentId) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("contentCommentSubList",contentService.contentCommentSub(contentId));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @ApiOperation(value = "게시글 작성", notes = "게시글 작성(첨부파일 등록)")
    @RequestMapping(value="/insert", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> insert(MultipartHttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception{
        contentService.contentInsert(request, param);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "게시글 삭제", notes = "게시글 삭제(첨부파일 삭제)")
    @RequestMapping(value="/delete/{contentId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> delete(@PathVariable String contentId) throws Exception{
        contentService.contentDelete(contentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글 수정(첨부파일 수정)")
    @RequestMapping(value="/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> update(MultipartHttpServletRequest request, @RequestParam Map<String, Object> param) throws Exception{
        contentService.contentUpdate(request, param);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "댓글 작성", notes = "댓글 작성")
    @RequestMapping(value="/comment/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> commentCreate(@RequestBody Content param) throws Exception{
        contentService.commentCreate(param);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "댓글 수정", notes = "댓글 수정")
    @RequestMapping(value="/comment/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> commentUpdate(@RequestBody Content param) throws Exception{
        contentService.commentUpdate(param);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "댓글 삭제", notes = "댓글 삭제")
    @RequestMapping(value="/comment/delete/{contentId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> commentDelete(@PathVariable String contentId) throws Exception{
        contentService.commentDelete(contentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "대댓글 작성", notes = "대댓글 작성")
    @RequestMapping(value="/subcomment/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> commentSubCreate(@RequestBody Content param) throws Exception{
        contentService.commentSubCreate(param);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "대댓글 수정", notes = "대댓글 수정")
    @RequestMapping(value="/subcomment/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> subCommentUpdate(@RequestBody Content param) throws Exception{
        contentService.subCommentUpdate(param);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "대댓글 삭제", notes = "대댓글 삭제")
    @RequestMapping(value="/subcomment/delete/{subContentId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> subCommentDelete(@PathVariable String subContentId) throws Exception{
        contentService.subCommentDelete(subContentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "파일 다운로드", notes = "파일 다운로드")
    @RequestMapping(value="/download", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<byte[]> fileDownLoad(@RequestBody FileDownLoad uuid) throws Exception{
        return contentService.fileDownLoad(uuid);
    }

}
