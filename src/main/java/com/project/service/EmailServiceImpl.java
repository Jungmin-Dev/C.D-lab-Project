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

@RequiredArgsConstructor
@Slf4j
@Service
public class EmailServiceImpl implements EmailService{
    private final AuthMapper authMapper;
    private final JavaMailSender javaMailSender;

    @Override
    public String Email(Info param) throws Exception {
        String FROM_ADDRESS = "jungminkim96@naver.com";
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setFrom(new InternetAddress(FROM_ADDRESS,"uni-core"));
        mimeMessage.addRecipients(Message.RecipientType.TO, param.getUserEmail());//보내는 대상
        mimeMessage.setSubject("uni-core 이메일 인증");
        String msgg="";

        msgg+= "<div style='margin:5px;'>";
        msgg+= "<h2> 안녕하세요 uni-core입니다. </h2>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 창으로 돌아가 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= "</strong><div><br/> ";
        msgg+= "</div>";

        mimeMessage.setText(msgg, "utf-8", "html");//내용

        javaMailSender.send(mimeMessage);

        HashMap<String, Object> map = new HashMap<>();
        map.put("Email", param.getUserEmail());
        map.put("selfAuth", param.getSelfAuth());

        authMapper.userEmailCertification(map);

        return "1";
    }
}
