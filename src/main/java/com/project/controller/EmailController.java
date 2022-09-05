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

//1. 이메일 보내기
public class EmailController {
    private final AuthService authService;
    private final ContentService contentService;

    @ApiOperation(value = "이메일 전송", notes = "이메일 전송")
    @RequestMapping(value="/emailSend", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> emailSend(@RequestBody Info param) throws Exception{
        Map<String, Object> map = new HashMap<>();
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
