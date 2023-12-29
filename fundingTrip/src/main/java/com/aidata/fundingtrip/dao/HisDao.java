package com.aidata.fundingtrip.dao;

import com.aidata.fundingtrip.dto.HisFileDto;
import com.aidata.fundingtrip.dto.HistoryBoardDto;
import com.aidata.fundingtrip.dto.ListDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HisDao {
    List<HistoryBoardDto> selectHisList(ListDto ldto);

    //전체 개시글 개수 구하는 메소드
    int selectHisCnt(ListDto ldto);

    //게시글 저장 메소드
    void saveHistory(HistoryBoardDto hDto);

    //파일 정보 저장 메소드
    void historyFile(HisFileDto hfd);

    //게시글 하나만 가져오는 메소드
    HistoryBoardDto selectHistory(int hisnum);

    //게시글 번호에 해당하는 파일목록을 가져오는 메소드
    List<HisFileDto> selectFileList(int hisnum);

    //게시글 번호에 해당하는 파일목록 삭제 메소드
    void deleteFiles(int hisnum);

    //게시글 번호에 해당하는 게시글 삭제 메소드
    void deleteHis(int hisnum);

    //파일 저장 이름 목록 구하는 메소드
    List<String> selectHnameList(int hisnum);

    //수정시 단독 파일 삭제
    void deleteFile(String hfSysname);

    //게시글 수정 메소드
    void updateHis(HistoryBoardDto hdto);


}
