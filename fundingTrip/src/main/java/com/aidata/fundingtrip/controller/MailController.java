package com.aidata.fundingtrip.controller;


import com.aidata.fundingtrip.dto.MailDto;
import com.aidata.fundingtrip.service.MailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.UnsupportedEncodingException;



@Controller
public class MailController {
    @Autowired
    private MailService mailServ;

//    @GetMapping("/")
//    public String home(){
//        return "send";
//    }

    @PostMapping("mailConfirm")
    @ResponseBody
    public String mailConfirm(MailDto email) throws MessagingException,UnsupportedEncodingException {

        String result = mailServ.sendEmail(email);
        return result;
    }


}
