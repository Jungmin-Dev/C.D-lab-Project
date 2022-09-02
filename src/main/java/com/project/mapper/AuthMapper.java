package com.project.mapper;

import com.project.domain.Info;

import java.util.HashMap;
import java.util.Optional;


public interface AuthMapper {
    // 로그인
    public Info userLogin(HashMap<String, Object> map) throws Exception;
    public Info userDuplicate(HashMap<String, Object> map) throws Exception;
    // 회원가입
    public int userJoin(HashMap<String, Object> map) throws Exception;
    // 인증번호 저장 (비교를 위한 DB 저장)
    public int userEmailCertification(HashMap<String, Object> map) throws Exception;
    // 인증번호 일치 시 인증번호 DB 삭제 -- DB 과부하 예방
    public int userEmailCertificationDelete(String info) throws Exception;
    // 인증번호 일치 여부 확인
    public Optional<Info> userEmailCertificationCheck(String info) throws Exception;
    // 비밀번호 찾기 (이메일 유효 검사)
    public Info userFindEmail(String info) throws Exception;
    // 비밀번호 찾기 (비밀번호 변경)
    public int userChangePw(HashMap<String, Object> map) throws Exception;
}
