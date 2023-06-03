package com.bank.bank_system.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Transactions {
    private Integer tranId;
    private Integer accountId;
    private Integer tranReciprocalId;
    private Long tranAmount;
    private String tranDatetime;
    private String tranDescription;
    private Long tranBalance;
    private Long tranReciprocalBalance;
    private Integer typeId;

    //查詢交易紀錄時需要join account表來查出accountName和reciprocalAccountName並封裝到下面兩個屬性
    private String accountName;
    private String reciprocalAccountName;

}
