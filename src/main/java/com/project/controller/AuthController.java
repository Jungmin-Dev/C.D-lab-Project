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

//1. 관리자 페이지 진입 시 비밀번호 입력(지정된 비밀번호) - 비밀번호 테이블
public class AuthController {
    private final AuthService authService;

    @ApiOperation(value = "로그인", notes = "비밀번호 확인")
    @RequestMapping(value="/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> login(@RequestBody String param) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("login",authService.login(param));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
