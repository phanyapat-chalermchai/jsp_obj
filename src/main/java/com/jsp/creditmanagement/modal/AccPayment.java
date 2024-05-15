package com.jsp.creditmanagement.modal;

import java.math.BigDecimal;
import java.sql.Date;

public class AccPayment {
	protected String cardid;
	protected String custacct;
	protected String custcode;
	protected String account;
	protected String trantype;
	protected String rptype;
	protected String bankcheqcode;
	protected String bankcode;
	protected String bankbranchcode;
	protected String bankaccno;
	protected String bankacctype;
	protected String bankcheqcodeextra;
	protected String paytype;
	protected String crosstype;
	protected Date effdate;
	protected Date enddate;

    // Default constructor
    public AccPayment() {
    }

    // Constructor with parameters

    // Constructor with parameters
    public AccPayment(String cardid, String custacct, String custcode, String account, String trantype, 
                      String rptype, String bankcheqcode, String bankcode, String bankbranchcode, 
                      String bankaccno, String bankacctype, String bankcheqcodeextra, String paytype, 
                      String crosstype, Date effdate, Date enddate) {
        this.cardid = cardid;
        this.custacct = custacct;
        this.custcode = custcode;
        this.account = account;
        this.trantype = trantype;
        this.rptype = rptype;
        this.bankcheqcode = bankcheqcode;
        this.bankcode = bankcode;
        this.bankbranchcode = bankbranchcode;
        this.bankaccno = bankaccno;
        this.bankacctype = bankacctype;
        this.bankcheqcodeextra = bankcheqcodeextra;
        this.paytype = paytype;
        this.crosstype = crosstype;
        this.effdate = effdate;
        this.enddate = enddate;
    }


    // Getters and Setters

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getCustacct() {
        return custacct;
    }

    public void setCustacct(String custacct) {
        this.custacct = custacct;
    }

    public String getCustcode() {
        return custcode;
    }

    public void setCustcode(String custcode) {
        this.custcode = custcode;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTrantype() {
        return trantype;
    }

    public void setTrantype(String trantype) {
        this.trantype = trantype;
    }

    public String getRptype() {
        return rptype;
    }

    public void setRptype(String rptype) {
        this.rptype = rptype;
    }

    public String getBankcheqcode() {
        return bankcheqcode;
    }

    public void setBankcheqcode(String bankcheqcode) {
        this.bankcheqcode = bankcheqcode;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getBankbranchcode() {
        return bankbranchcode;
    }

    public void setBankbranchcode(String bankbranchcode) {
        this.bankbranchcode = bankbranchcode;
    }

    public String getBankaccno() {
        return bankaccno;
    }

    public void setBankaccno(String bankaccno) {
        this.bankaccno = bankaccno;
    }

    public String getBankacctype() {
        return bankacctype;
    }

    public void setBankacctype(String bankacctype) {
        this.bankacctype = bankacctype;
    }

    public String getBankcheqcodeextra() {
        return bankcheqcodeextra;
    }

    public void setBankcheqcodeextra(String bankcheqcodeextra) {
        this.bankcheqcodeextra = bankcheqcodeextra;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getCrosstype() {
        return crosstype;
    }

    public void setCrosstype(String crosstype) {
        this.crosstype = crosstype;
    }

    public Date getEffdate() {
        return effdate;
    }

    public void setEffdate(Date effdate) {
        this.effdate = effdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }
}
