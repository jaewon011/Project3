package com.aidata.fundingtrip.controller;


import com.aidata.fundingtrip.dao.BoardDao;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Controller
@Slf4j
@RequestMapping("/jq")
public class KakaopayController {

    @Autowired
    BoardDao bdao;

    @RequestMapping("/jq.cls")
    public ModelAndView main(ModelAndView mv, HttpSession s, RedirectView rv) {
        mv.setViewName("jq/test");
        return mv;
    }

    @RequestMapping("/pay.cls")
    public ModelAndView serve(ModelAndView mv, HttpSession S, RedirectView rv) {
        mv.setViewName("jq/serve");
        return mv;
    }

    @RequestMapping("/kakaopay.cls")
    @ResponseBody
    public String kakaopay(@RequestParam(name = "tnum") int tnum) {
        int tcash = getTcashFromTboard(tnum);

        try {
            URL uu = new URL("https://kapi.kakao.com/v1/payment/ready");
            //서버연결 도와줌
            HttpURLConnection gory = (HttpURLConnection) uu.openConnection();
            gory.setRequestMethod("POST");
            gory.setRequestProperty("Authorization", "KakaoAK 750f03e3c0157d6f86fc01ff973cdab2");
            gory.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            gory.setDoOutput(true);
            String pamt = "cid=TC0ONETIME&partner_order_id=partner_order_id&partner_user_id=partner_user_id&item_name=trip&quantity=1&total_amount=" + tcash + "&vat_amount=200&tax_free_amount=0&approval_url=https://localhost/myPage/&fail_url=https://localhost/fail&cancel_url=https://localhost/cancel";
            OutputStream ur = gory.getOutputStream();
            DataOutputStream du = new DataOutputStream(ur);
            du.writeBytes(pamt);
            du.flush();
            du.close();
            int end = gory.getResponseCode();

            InputStream is;
            if (end == 200) {
                is = gory.getInputStream();
            } else {
                is = gory.getErrorStream();
            }
            InputStreamReader look = new InputStreamReader(is);
            BufferedReader up = new BufferedReader(look);
            String response = up.readLine();
            log.info("리턴값" + response);
            return response; // JSON 형식의 문자열을 그대로 반환
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "{\"result\":\"NO\"}";
    }

    // tnum을 기반으로 tboard에서 tcash 값을 가져오는 메소드
    private int getTcashFromTboard(int tnum) {
        return bdao.getTcashFromTboard(tnum);
    }
}