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
    private final JavaMailSender javaMailSender;

    @Override
    // 회원가입
    public int join(Info param) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Password", param.getUserPassword());
        map.put("Name", param.getUserName());
        map.put("Email", param.getUserEmail());
        return authMapper.userJoin(map);
    }

    @Override
    // 인증번호 일치 확인
    public int Certification(Info param) throws Exception {
        int result = 0;
        Optional<Info> check = authMapper.userEmailCertificationCheck(param.getUserEmail());

        if(check.isPresent() && check.get().getSelfAuth().equals(param.getSelfAuth())){
            result = authMapper.userEmailCertificationDelete(param.getUserEmail());
        }
        return result;
    }

    @Override
    // 인증번호 전송
    public String mailCheck(Info param) throws Exception {
        String ePw = createKey();
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
        msgg+= ePw+"</strong><div><br/> ";
        msgg+= "</div>";

        mimeMessage.setText(msgg, "utf-8", "html");//내용

        javaMailSender.send(mimeMessage);

        param.setSelfAuth(ePw);

        HashMap<String, Object> map = new HashMap<>();
        map.put("Email", param.getUserEmail());
        map.put("selfAuth", param.getSelfAuth());

        authMapper.userEmailCertification(map);

        return "1";
    }

    @Override
    // 아이디(이메일) 중복 체크
    public Optional duplicate(String param) throws Exception {
        // 중복 체크
        HashMap<String, Object> map = new HashMap<>();
        map.put("Email", param);
        return Optional.ofNullable(authMapper.userDuplicate(map));
    }

    @Override
    // 로그인
    public Optional login(Info param) throws Exception {
        log.info("로그인 하셨습니다.");
        HashMap<String, Object> map = new HashMap<>();
        map.put("Email", param.getUserEmail());
        map.put("Password", param.getUserPassword());
        return Optional.ofNullable(authMapper.userLogin(map));
    }

    @Override
    // 비밀번호 찾기(아이디 유효 검사)
    public Optional findPassword(String param) throws Exception {
        return Optional.ofNullable(authMapper.userFindEmail(param));
    }

    @Override
    // 비밀번호 찾기(비밀번호 변경)
    public int changePassword(Info param) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Email", param.getUserEmail());
        map.put("Password", param.getUserPassword());
        return authMapper.userChangePw(map);
    }

    // 인증번호 생성
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }
}
