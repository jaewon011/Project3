package com.aidata.fundingtrip.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class TripReplyDto {
    private int trnum;
    private int trbnum;
    private String trmid;
    private String trcontents;
    private Timestamp trdate;
}
