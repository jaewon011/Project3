package com.aidata.fundingtrip.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class QnAReplyDto {
    private int qrnum;
    private int qrbnum;
    private String qrmid;
    private String qrcontents;
    private Timestamp qrdate;
}
