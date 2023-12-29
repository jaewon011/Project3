package com.aidata.fundingtrip.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class HistoryBoardDto {
    private int hisnum;
    private String hisname;
    private String hisloca;
    private String hisexplan;
    private String hisinfo;
    private String hislatt;
    private String hislongt;

    private List<HisFileDto> hisFileList;
}
