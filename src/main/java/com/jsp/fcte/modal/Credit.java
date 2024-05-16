package com.jsp.fcte.modal;

import java.math.BigDecimal;
import java.sql.Date;

public class Credit {
	protected String accountno;
	protected String transtype;
	protected Date effectdate;
	protected BigDecimal amount;
	protected String remark;
	
	public Credit() {
	}
	
	public Credit(String accountno, String transtype, Date effectdate, BigDecimal amount, String remark) {
        super();
        this.accountno = accountno;
        this.transtype = transtype;
        this.effectdate = effectdate;
        this.amount = amount;
        this.remark = remark;
    }
	
	public Credit(String transtype, Date effectdate, BigDecimal amount, String remark) {
        super();
        this.transtype = transtype;
        this.effectdate = effectdate;
        this.amount = amount;
        this.remark = remark;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getTranstype() {
        return transtype;
    }

    public void setTranstype(String transtype) {
        this.transtype = transtype;
    }

    public Date getEffectdate() {
        return effectdate;
    }

    public void setEffectdate(Date effectdate) {
        this.effectdate = effectdate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
