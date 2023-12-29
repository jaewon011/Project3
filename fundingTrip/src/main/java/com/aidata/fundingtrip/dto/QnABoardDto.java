package com.aidata.fundingtrip.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class QnABoardDto {
    private int qnum;
    private String qmid;
    private String qtitle;
    private String qcategory;
    private String qcontents;
    private Timestamp qdate;
}
