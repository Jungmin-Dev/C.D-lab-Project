package com.project.service;

import com.project.domain.Info;
import com.project.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    private final AuthMapper authMapper;

    @Override
    // 로그인
    public Optional login(String param) throws Exception {
        log.info("로그인 시도를 하였습니다.");
        HashMap<String, Object> map = new HashMap<>();
        map.put("password", param);
        return Optional.ofNullable(authMapper.userLogin(map));
    }

}
