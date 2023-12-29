package com.aidata.fundingtrip.dao;

import com.aidata.fundingtrip.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardDao {
    void insertQnABoard(QnABoardDto qDto);//고객 센터 게시글 저장(삽입)

    List<QnABoardDto> selectQnABoardList(SearchDto search);//고객 센터 게시글 전체 목록 가져오기

    QnABoardDto selectQnABoard(int qnum);//고객 센터 게시글 하나만 가져오기

    int selectQnABoardCnt(SearchDto search);//고객 센터 전체 글 카운트

    List<QnAReplyDto> selectQnAReplyList(int qnum);//게시글 번호에 해당 하는 댓글 목록을 가져옴

    void updateQnABoard(QnABoardDto qDto);//고객 센터 글 수정

    void deleteQnAReply(int qnum);//고객 센터 댓글 삭제

    void deleteQnABoard(int qnum);//고객 센터 글 삭제

    void insertQnAReply(QnAReplyDto qrDto);//고객 센터 댓글 작성

    QnAReplyDto selectLastQnAReply(int qrnum);//저장 시 생성된 댓글 번호로 댓글 정보를 가져옴

    void insertFundBoard(TripBoardDto tDto);//펀딩 트립 게시글 저장

    void insertFundFile(TripBoardFileDto tfDto);//펀딩 트립 게시글 사진 저장

    List<TripBoardDto> selectTripBoardList(SearchDto sDto);//승인된 글의 리스트만 나오는 메소드

    int selectTripBoardCnt(SearchDto sDto);//승인된 게시글 개수 구하는 메소드

    TripBoardDto selectTripBoard(int tnum);//게시글 하나만 가져오는 메소드

    List<TripBoardFileDto> selectTripFileList(int tnum);//게시글 번호에 해당하는 파일목록을 가져오는 메소드

    List<TripReplyDto> selectTripReplyList(int tnum);//게시글 번호에 해당하는 댓글목록을 가져오는 메소드

    List<String> selectTFnameList(int tnum); //파일의 저장 이름 목록 구하는 메소드

    void deleteTripFiles(int tnum); //게시글 번호에 해당하는 파일목록 삭제 메소드

    void deleteTreplays(int tnum);//게시글 번호에 해당하는 댓글목록 삭제 메소드

    void deleteTripBoard(int tnum);//게시글 번호에 해당하는 게시글 삭제 메소드

    void insertTreply(TripReplyDto treply);//댓글 저장 메소드

    TripReplyDto selectLastTreply(int trnum);//저장 시 생성된 댓글 번호로 댓글 정보 가져오는 메소드

    void deleteTripFile(String tsysname);//수정 시 단독 파일 삭제

    void updateTripBoard(TripBoardDto tDto);//게시글 수정 메소드

    void updateStatus(TripBoardDto tDto); //승인 상태 변경

    List<TripBoardDto> selectMyTripBoardList(SearchDto sDto); //자신이 쓴 글의 리스트만 나오는 메소드

    int selectMyTripBoardCnt(SearchDto sDto);//자신이 쓴 글의 게시글 개수를 구하는 메소드

    List<TripBoardDto> selectAllTripBoardList(SearchDto sDto);//전체 글의 리스트만 나오는 메소드

    int selectAllTripBoardCnt(SearchDto sDto);//전체 게시글 개수 구하는 메소드

    int getTcashFromTboard(int tnum);
}
