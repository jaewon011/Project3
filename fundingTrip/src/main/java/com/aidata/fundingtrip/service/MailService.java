package com.aidata.fundingtrip.service;

import com.aidata.fundingtrip.dao.MemberDao;
import com.aidata.fundingtrip.dto.MailDto;
import com.aidata.fundingtrip.dto.MemberDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@Slf4j
public class MailService {
    //의존성 주입을 통해서 필요한 객체를 가져온다.
    @Autowired
    private JavaMailSender emailSender;
    // 타임리프를사용하기 위한 객체를 의존성 주입으로 가져온다
    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private MemberDao mDao;

//    private String authNum; //랜덤 인증 코드
//
//    //랜덤 인증 코드 생성
//    public void createCode() {
//        Random random = new Random();
//        StringBuffer key = new StringBuffer();
//
//        for(int i=0;i<8;i++) {
//            int index = random.nextInt(3);
//
//            switch (index) {
//                case 0 :
//                    key.append((char) ((int)random.nextInt(26) + 97));
//                    break;
//                case 1:
//                    key.append((char) ((int)random.nextInt(26) + 65));
//                    break;
//                case 2:
//                    key.append(random.nextInt(9));
//                    break;
//            }
//        }
//        authNum = key.toString();
//        createCode(); //인증 코드 생성 , createEmailForm 에서 옮겨옴

    //메일 양식 작성
    public MimeMessage createEmailForm(MailDto email) throws MessagingException, UnsupportedEncodingException {

        log.info("메일 발송 설정");

        String setFrom = "xkxkfhwk3@gmail.com"; //email-config에 설정한 자신의 이메일 주소(보내는 사람)
        String toEmail = email.getEmail(); //받는 사람
        String title = "비밀번호 재설정"; //제목

        String link = "http://localhost/searchPwForm?mid="+email.getMid();

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO,toEmail); //보낼 이메일 설정
        message.setSubject(title); //제목 설정
        message.setFrom(setFrom); //보내는 이메일
        message.setText(setContext("<a href='"+ link + "' style='text-decoration: none'>비밀번호 변경</a>")
                                    , "utf-8", "html");

        return message;
    }

    //실제 메일 전송
    public String sendEmail(MailDto email) throws MessagingException, UnsupportedEncodingException {
//        public String sendEmail(String emailAddress, String memberId) {
        log.info("sendEmail()");
        //메일전송에 필요한 정보 설정
        String result = null;
        //email.getMid() -> 사용자 검색
        MemberDto member = mDao.selectMember(email.getMid());
        if(member != null) {
            MimeMessage emailForm = createEmailForm(email);
            //실제 메일 전송
            emailSender.send(emailForm);
            result = "ok";
        }
        else {
            result = "fail";
        }

        return result;
    }

    //타임리프를 이용한 context 설정
    public String setContext(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("mail", context); //mail.html
    }
}
