package com.jsp.fcte.dto;

import java.sql.Date;

public class SearchQueryFcte011DTO {
	protected String appId;
	protected String channel;
	protected String custNameEN;
	protected String custNameTH;
	protected String custcode;
	protected String cardid;
	protected String marketingId;
	protected String branch;

    // Default constructor
    public SearchQueryFcte011DTO() {
    }

    // Constructor with parameters
    public SearchQueryFcte011DTO(String appId, String channel, String custNameEN, String custNameTH, 
                      String custcode, String cardid, String marketingId, String branch) {
        this.appId = appId;
        this.channel = channel;
        this.custNameEN = custNameEN;
        this.custNameTH = custNameTH;
        this.custcode = custcode;
        this.cardid = cardid;
        this.marketingId = marketingId;
        this.branch = branch;
    }


    // Getters and Setters

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCustNameEN() {
        return custNameEN;
    }

    public void setCustNameEN(String custNameEN) {
        this.custNameEN = custNameEN;
    }

    public String getCustNameTH() {
        return custNameTH;
    }

    public void setCustNameTH(String custNameTH) {
        this.custNameTH = custNameTH;
    }
    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getCustcode() {
        return custcode;
    }

    public void setCustcode(String custcode) {
        this.custcode = custcode;
    }

    public String getMarketingId() {
        return marketingId;
    }

    public void setMarketingId(String marketingId) {
        this.marketingId = marketingId;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

}
