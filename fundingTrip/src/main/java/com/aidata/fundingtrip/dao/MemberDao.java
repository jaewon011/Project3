package com.aidata.fundingtrip.dao;

import com.aidata.fundingtrip.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberDao {
    int selectId(String mid);
    void insertMember(MemberDto mDto);
    String selectPassword(String mid);
    MemberDto selectMember(String mid);

    //String memberUpdate(MemberDto member, HttpSession session);

    void drawMember(String sessionId);

    void memberUpdate(MemberDto member);

    String serchId(@Param("memail") String memail, @Param("mname") String mname);

    void updatePw(MemberDto member);
}
