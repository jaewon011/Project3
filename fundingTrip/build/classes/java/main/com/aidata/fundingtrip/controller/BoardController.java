package com.aidata.fundingtrip.controller;

import com.aidata.fundingtrip.dto.*;
import com.aidata.fundingtrip.service.HistoryService;
import com.aidata.fundingtrip.service.QnABoardService;
import com.aidata.fundingtrip.service.TripBoardService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class BoardController {
	private ModelAndView mv;
	@Autowired
	private TripBoardService tServ;
	@Autowired
	private HistoryService hServ;
	@Autowired
	private QnABoardService qServ;

	//펀딩 글 목록
	@GetMapping("fundList")
	public ModelAndView fundList(SearchDto sDto, HttpSession session) {
		log.info("fundList()");
		ModelAndView mv = tServ.getFundList(sDto, session);

		return mv;
	}

	@GetMapping("myFundList")
	public ModelAndView myFundList(SearchDto sDto, HttpSession session) {
		log.info("myFundList()");
		ModelAndView mv = tServ.getMyFundList(sDto, session);

		return mv;
	}

	@GetMapping("allFundList")
	public ModelAndView allFundList(SearchDto sDto, HttpSession session) {
		log.info("allFundList()");
		ModelAndView mv = tServ.getAllFundList(sDto, session);

		return mv;
	}

	@GetMapping("fundWrite")
	public String fundWrite(){
		log.info("fundWrite()");
		return "fundWrite";
	}
	@PostMapping("fundWriteProc")
	public String fundWriteProc(@RequestPart List<MultipartFile> files, TripBoardDto tDto, HttpSession session, RedirectAttributes rttr){
		log.info("fundWriteProc()");
		String view = tServ.writeFundList(files, tDto, session, rttr);
		return view;
	}
	//펀딩 글 상세 보기
	@GetMapping("detailFund")
	public ModelAndView detailFund(int tnum, HttpSession session, TripBoardDto tDto) {
		log.info("detailFund : {}", tnum);
		mv=tServ.getFund(tnum, session, tDto);
		return mv;
	}

	@GetMapping("deleteTripBoard")
	public String deleteTripBoard(int tnum, HttpSession session, RedirectAttributes rttr){
		log.info("deleteTripBoard()");
		String view = tServ.deleteTripBoard(tnum, session, rttr);
		return view;
	}

	@GetMapping("updateTripBoard")
	public ModelAndView updateTripBoard(int tnum){
		log.info("updateTripBoard()");
		ModelAndView mv = tServ.updateTripBoard(tnum);
		return mv;
	}

	@PostMapping("updateTripProc")
	public String updateTripProc(List<MultipartFile> files, TripBoardDto tDto, HttpSession session, RedirectAttributes rttr){
		log.info("updateTripProc()");
		String view = tServ.updateTripBoard(files, tDto, session, rttr);
		return view;
	}

	@GetMapping("updateStatus")
	public String updateStatus(TripBoardDto tDto, HttpSession session, RedirectAttributes rttr){
		log.info("updateStatus()");
		String view = tServ.updateStatus(tDto, session, rttr);
		return view;
	}

	//고객 센터 화면
	@GetMapping("qnaList")
	public ModelAndView qnaList(SearchDto search, HttpSession session) {
		log.info("qnaList()");
		mv = qServ.getQnAList(search, session);
		mv.setViewName("qnaList");
		return mv;
	}

	//고객 센터 글 상세 보기
	@GetMapping("qnaDetail")
	public ModelAndView qnaDetail(int qnum) {
		log.info("qnaDetail():{}", qnum);
		mv = qServ.getQnA(qnum);
		return mv;
	}

	//고객 센터 글쓰기 화면
	@GetMapping("qnaWrite")
	public String qnaWrite() {
		log.info("qnaWrite()");
		return "qnaWrite";
	}

	//고객 센터 글쓰기 처리
	@PostMapping("qnaWriteProc")
	public String qnaWriteProc(QnABoardDto qDto, HttpSession session, RedirectAttributes rttr) {
		log.info("qnaWriteProc()");
		String view = qServ.qnaWrite(qDto, session, rttr);
		return view;
	}

	//고객 센터 글 수정(mv)
	@GetMapping("qnaUpdate")
	public ModelAndView qnaUpdate(int qnum) {
		log.info("qnaUpdate()");
		mv = qServ.qnaUpdate(qnum);
		return mv;
	}

	//고객 센터 글 수정 처리
	@PostMapping("qnaUpdateProc")
	public String qnaUpdateProc(QnABoardDto qDto, HttpSession session, RedirectAttributes rttr) {
		log.info("qnaUpdateProc()");
		String view = qServ.qnaUpdate(qDto, session, rttr);
		return view;
	}

	//고객 센터 글 삭제
	@GetMapping("qnaBoardDelete")
	public String qnaBoardDelete(int qnum, HttpSession session, RedirectAttributes rttr) {
		log.info("qnaBoardDelete()");
		String view = qServ.deleteQnABoard(qnum, session, rttr);
		return view;
	}

	//유적지 목록 화면
	@GetMapping("hisList")
	public ModelAndView hisList(ListDto ldto,
								HttpSession session){
		log.info("hisList()");
		ModelAndView mv = hServ.getHisList(ldto ,session);

		return mv;
	}

	//유적지 정보 작성
	@GetMapping("hisWrite")
	public String hisWrite() {
		log.info("hisWrite()");
		return "hisWrite";
	}
	@PostMapping("hiswriteProc")
	public String hiswriteProc(@RequestPart List<MultipartFile> files,
							   HistoryBoardDto hdto,
							   HttpSession session,
							   RedirectAttributes rttr) {
		log.info("hiswriteProc");
		String view = hServ.hisWrite(files, hdto, session, rttr);
		return view;
	}

	//	유적지 상세 보기
	@GetMapping("hisDetail")
	public ModelAndView hisDetail(int hisnum) {
		log.info("hisDetail() : {}", hisnum);
		ModelAndView mv = hServ.getHistory(hisnum);
		return mv;
	}

	@GetMapping("hisDelete")
	public String hisDelete(int hisnum,
							HttpSession session,
							RedirectAttributes rttr){
		log.info("hisDelete()");
		String view = hServ.deleteHis(hisnum, session, rttr);
		return view;
	}

	@GetMapping("hisUpdate")
	public ModelAndView hisUpdate(int hisnum) {
		log.info("hisUpdate()");
		ModelAndView mv = hServ.hisUpdate(hisnum);
		return mv;
	}

	@PostMapping("hisupdateProc")
	public String hisupdateProc(List<MultipartFile> files,
								HistoryBoardDto hdto,
								HttpSession session,
								RedirectAttributes rttr){
		log.info("hisupdateProc()");
		String view = hServ.updateHis(files, hdto, session, rttr);
		return view;
	}
}