package com.project.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.project.domain.Info;
import com.project.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Controller
@Api(tags = "로그인 관련 Controller")
@CrossOrigin(origins="*")
//1. 관리자 페이지 진입시 비밀번호 입력(지정된 비밀번호)
//2.홈페이지에 이미지 리스트 영역 이미지 등록, 수정, 삭제, 순번 지정 기능
//3.게시물(a태그로 링크이동, 텍스트내용, 날짜) 기입되게 등록, 수정, 삭제, 순번 지정 기능
//4. 이메일 보내기
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value = "아이디(이메일) 중복 체크", notes = "아이디(이메일) 중복 체크")
    @RequestMapping(value="/duplicate", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> duplicate(@RequestBody Info param) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("userEmail",authService.duplicate(param.getUserEmail()));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @ApiOperation(value = "회원가입", notes = "회원가입")
    @RequestMapping(value="/join", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> join(@RequestBody Info param) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("user", authService.join(param));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @ApiOperation(value = "로그인", notes = "로그인")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> login(@RequestBody Info param) throws Exception{
        Map<String, Object> map = new HashMap<>();
        Optional info = authService.login(param);
        if(info.isPresent()){
            map.put("login", info.get());
        }
        else if(info == Optional.empty()){
            map.put("login", 0);
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @ApiOperation(value = "인증번호 전송", notes = "인증번호 전송")
    @RequestMapping(value="/emailcheck", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> emailCheck(@RequestBody Info param) throws Exception{
        Map<String, Object> map = new HashMap<>();
        String info =  authService.mailCheck(param);
        if(info=="1"){
            map.put("userEmail", 1);
        }
        else if(info!="1"){
            map.put("userEmail", 0);
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @ApiOperation(value = "인증번호 일치 확인", notes = "인증번호 일치 확인")
    @RequestMapping(value="/certificationcheck", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> certificationcheck(@RequestBody Info param) throws Exception{
        Map<String, Object> map = new HashMap<>();
        int info = authService.Certification(param);
        if(info>0)
            map.put("selfAuth", 1);
        else{
            map.put("selfAuth", 0);
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    @ApiOperation(value = "비밀번호 찾기(아이디 유효 검사)", notes = "비밀번호 찾기(아이디 유효 검사)")
    @RequestMapping(value="/emailfind", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> findPassword(@RequestBody Info param) throws Exception{
        Map<String, Object> map = new HashMap<>();
        Optional info =  authService.findPassword(param.getUserEmail());
        if(info.isPresent()){
            map.put("userEmail", 1);
        }
        else if(info == Optional.empty()){
            map.put("userEmail", 0);
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    @ApiOperation(value = "비밀번호 찾기(비밀번호 변경)", notes = "비밀번호 찾기(비밀번호 변경)")
    @RequestMapping(value="/changepw", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> changePw(@RequestBody Info param) throws Exception{
        Map<String, Object> map = new HashMap<>();
        int info =  authService.changePassword(param);
        if(info>=1){
            map.put("changePw", 1);
        }
        else if(info<=0){
            map.put("changePw", 0);
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
