package com.aidata.fundingtrip.controller;

import com.aidata.fundingtrip.dto.HisFileDto;
import com.aidata.fundingtrip.dto.TripBoardFileDto;
import com.aidata.fundingtrip.dto.TripReplyDto;
import com.aidata.fundingtrip.service.HistoryService;
import com.aidata.fundingtrip.service.TripBoardService;
import com.aidata.fundingtrip.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class BoardRestController {

    @Autowired
    private HistoryService hServ;
    @Autowired
    private MemberService mServ;
    @Autowired
    private TripBoardService tServ;



    @PostMapping("delTripFile")
    public List<TripBoardFileDto> delFile(TripBoardFileDto tFile, HttpSession session){
        log.info("delTripFile()");
        List<TripBoardFileDto> fList = tServ.delFile(tFile, session);
        return fList;
    }

    @PostMapping("treplyInsert")
    public TripReplyDto treplyInsert(TripReplyDto treply){
        log.info("treplyInsert()");
        treply = tServ.treplyInsert(treply);
        return treply;
    }

    @PostMapping("delFile")
    public List<HisFileDto> delFile(HisFileDto hFile,
                                    HttpSession session){
        log.info("delFile()");
        List<HisFileDto> fList = hServ.delFile(hFile, session);
        return fList;
    }
}
