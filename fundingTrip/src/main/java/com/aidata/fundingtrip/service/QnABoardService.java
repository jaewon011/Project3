package com.aidata.fundingtrip.service;

import com.aidata.fundingtrip.dao.BoardDao;
import com.aidata.fundingtrip.dao.MemberDao;
import com.aidata.fundingtrip.dto.*;
import com.aidata.fundingtrip.util.PagingUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Service
@Slf4j
public class QnABoardService {
	private ModelAndView mv;
	@Autowired
	MemberDao mDao;
	@Autowired
	private BoardDao bDao;

	//트랜젝션 관련 객체 선언
	@Autowired
	private PlatformTransactionManager manager;
	@Autowired
	private TransactionDefinition definition;

	private int lcnt = 5;//페이지 기본 값

	//고객 센터 글 상세 보기
	public ModelAndView getQnA(int qnum) {
		log.info("getQnA()");
		mv = new ModelAndView();
		//게시글 번호로 선택한 게시글 가져 오기
		QnABoardDto qDto = bDao.selectQnABoard(qnum);
		mv.addObject("qDto", qDto);

		//게시글의 답변(댓글)목록 가져오기
		List<QnAReplyDto> qrDto = bDao.selectQnAReplyList(qnum);
		mv.addObject("qrDto", qrDto);

		mv.setViewName("qnaDetail");//탬플릿 지정
		return mv;
	}

	//고객 센터 글 목록 가져오기
	public ModelAndView getQnAList(SearchDto search, HttpSession session) {
		log.info("getQnAList()");
		mv = new ModelAndView();
		//DB에서 고객센터 게시글 가져오기
		int num = search.getPageNum();


		//출력할 게시물의 수가 설정되지 않으면 기본값 5로 수정
		if (search.getListCnt() == 0) search.setListCnt(lcnt);

		//pageNum을 limit 시작 번호로 변경
		search.setPageNum((num - 1) * search.getListCnt());

		//Dao로 게시글 목록 가져오기
		List<QnABoardDto> qList = bDao.selectQnABoardList(search);

		//DB에서 가져온 데이터를 mv에 넣기
		mv.addObject("qList", qList);

		//페이징 처리
		search.setPageNum(num);
		String pageHtml = getPaging(search);
		mv.addObject("paging", pageHtml);

		//페이지 번호와 검색 관련 내용을 세션에 저장
		if (search.getColname() != null) session.setAttribute("search", search);
		else session.removeAttribute("search");//검색이 아닐 경우 제거

		//세션에 SearchDto를 저장. 글쓰기, 생세보기 화면에서 목록으로
		session.setAttribute("pageNum", num);

		mv.setViewName("qnaList");
		return mv;
	}

	//페이징 처리
	private String getPaging(SearchDto search) {
		String pageHtml = null;
		int maxNum = bDao.selectQnABoardCnt(search);//전체 글 개수
		int pageCnt = 5;//페이지 번호 5개
		String listname = "qnaList?";

		//검색 시의 url
		if (search.getColname() != null)
			listname += "colname" + search.getColname() + "&keyword" + search.getKeyword() + "&";

		//페이징 처리용 객체 생성
		PagingUtil paging = new PagingUtil(maxNum, search.getPageNum(), search.getListCnt(), pageCnt, listname);
		pageHtml = paging.makePaging();

		return pageHtml;
	}

	//고객 센터 게시글 작성
	public String qnaWrite(QnABoardDto qDto, HttpSession session, RedirectAttributes rttr) {
		log.info("qnaWrite()");
		TransactionStatus status = manager.getTransaction(definition);
		String view = null;
		String msg = null;

		try {
			bDao.insertQnABoard(qDto);//글 내용 저장
			MemberDto member = (MemberDto) session.getAttribute("member");

			//세션에 새 정보 저장
			member = mDao.selectMember(member.getMid());
			session.setAttribute("member", member);

			manager.commit(status);//트랜젝션 최종 승인

			//세션에 같은 이름으로 덮어쓰기
			view = "redirect:qnaList?pageNum=1";//첫번쩨 페이지로 돌아가기
			msg = "작성 성공";
		} catch (Exception e) {
			e.printStackTrace();
			manager.rollback(status);//취소
			view = "redirect:qnaWrite";
			msg = "작성 실패";
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}

	//고객 센터 글 수정 처리(mv)
	public ModelAndView qnaUpdate(int qnum) {
		log.info("qnaUpdate()");
		mv = new ModelAndView();
		//게시글 내용 가져오기
		QnABoardDto qDto = bDao.selectQnABoard(qnum);

		//댓글(답변) 목록 가져오기
		List<QnAReplyDto> qrDto = bDao.selectQnAReplyList(qnum);

		//mv에 담기
		mv.addObject("qDto", qDto);
		mv.addObject("qrDto", qrDto);

		//탬플릿 지정
		mv.setViewName("qnaUpdate");
		return mv;
	}

	//고객 센터 글 수정 처리
	public String qnaUpdate(QnABoardDto qDto, HttpSession session, RedirectAttributes rttr) {
		log.info("qnaUpdate()");
		TransactionStatus status = manager.getTransaction(definition);
		String view = null, msg = null;
		try {
			bDao.updateQnABoard(qDto);
			manager.commit(status);//트랜젝션
			view = "redirect:qnaDetail?qnum=" + qDto.getQnum();
			msg = "수정 성공";
		} catch (Exception e) {
			e.printStackTrace();
			manager.rollback(status);
			view = "redirect:qnaUpdate?qnum=" + qDto.getQnum();
			msg = "수정 실패";
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}

	//고객 센터 글 삭제 처리(댓글->게시글 순으로 삭제?)
	public String deleteQnABoard(int qnum, HttpSession session, RedirectAttributes rttr) {
		log.info("deleteQnABoard()");
		TransactionStatus status = manager.getTransaction(definition);
		String view = null, msg = null;
		try {
			bDao.deleteQnAReply(qnum);//댓글 삭제
			bDao.deleteQnABoard(qnum);//게시글 삭제
			manager.commit(status);//트랜젝션 최종 승인
			view = "redirect:qnaList?pageNum=1";
			msg = "삭제 성공";
		} catch (Exception e) {
			e.printStackTrace();
			manager.rollback(status);
			view = "redirect:qnaDetail?qnum=" + qnum;
			msg = "삭제 실패";
		}

		rttr.addFlashAttribute("msg", msg);
		return view;
	}

	//고객 센터 댓글(답변) 작성
	public QnAReplyDto QnAReplyInsert(QnAReplyDto qrDto) {
		log.info("QnAReplyInsert()");
		try {
			bDao.insertQnAReply(qrDto);
			qrDto = bDao.selectLastQnAReply(qrDto.getQrnum());
		} catch (Exception e) {
			e.printStackTrace();
			qrDto = null;
		}
		return qrDto;
	}
}