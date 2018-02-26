package com.rongdu.cashloan.cl.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountDetailInfo implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private Long user_id;

    /**
     * 充值扣款标志：1-充值，2-扣款
     */
    private Integer amt_type;

    /**
     * 变动金额
     */
    private BigDecimal amt;

    /**
     * 充值方式：1-支付宝，2-微信，3-银行卡，4-其他
     */
    private Integer pay_type;

    /**
     * 充值账号：类型是支付宝就是支付宝号，微信是微信号，银行卡是银行卡号，其他填写其他相关信息
     */
    private String pay_account;

    /**
     * 充值录入人
     */
    private String hand_person;

    /**
     * 
     */
    private String remark;

    /**
     * 
     */
    private Date create_time;

    /**
     * 
     */
    private Date update_time;

    /**
     * sj_account_detail
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return user_id 
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * 
     * @param user_id 
     */
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    /**
     * 充值扣款标志：1-充值，2-扣款
     * @return amt_type 充值扣款标志：1-充值，2-扣款
     */
    public Integer getAmt_type() {
        return amt_type;
    }

    /**
     * 充值扣款标志：1-充值，2-扣款
     * @param amt_type 充值扣款标志：1-充值，2-扣款
     */
    public void setAmt_type(Integer amt_type) {
        this.amt_type = amt_type;
    }

    /**
     * 变动金额
     * @return amt 变动金额
     */
    public BigDecimal getAmt() {
        return amt;
    }

    /**
     * 变动金额
     * @param amt 变动金额
     */
    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    /**
     * 充值方式：1-支付宝，2-微信，3-银行卡，4-其他
     * @return pay_type 充值方式：1-支付宝，2-微信，3-银行卡，4-其他
     */
    public Integer getPay_type() {
        return pay_type;
    }

    /**
     * 充值方式：1-支付宝，2-微信，3-银行卡，4-其他
     * @param pay_type 充值方式：1-支付宝，2-微信，3-银行卡，4-其他
     */
    public void setPay_type(Integer pay_type) {
        this.pay_type = pay_type;
    }

    /**
     * 充值账号：类型是支付宝就是支付宝号，微信是微信号，银行卡是银行卡号，其他填写其他相关信息
     * @return pay_account 充值账号：类型是支付宝就是支付宝号，微信是微信号，银行卡是银行卡号，其他填写其他相关信息
     */
    public String getPay_account() {
        return pay_account;
    }

    /**
     * 充值账号：类型是支付宝就是支付宝号，微信是微信号，银行卡是银行卡号，其他填写其他相关信息
     * @param pay_account 充值账号：类型是支付宝就是支付宝号，微信是微信号，银行卡是银行卡号，其他填写其他相关信息
     */
    public void setPay_account(String pay_account) {
        this.pay_account = pay_account == null ? null : pay_account.trim();
    }

    /**
     * 充值录入人
     * @return hand_person 充值录入人
     */
    public String getHand_person() {
        return hand_person;
    }

    /**
     * 充值录入人
     * @param hand_person 充值录入人
     */
    public void setHand_person(String hand_person) {
        this.hand_person = hand_person == null ? null : hand_person.trim();
    }

    /**
     * 
     * @return remark 
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * @param remark 
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 
     * @return create_time 
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 
     * @param create_time 
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * 
     * @return update_time 
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 
     * @param update_time 
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    /**
     *
     * @mbggenerated 2018-02-26
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AccountDetailInfo other = (AccountDetailInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUser_id() == null ? other.getUser_id() == null : this.getUser_id().equals(other.getUser_id()))
            && (this.getAmt_type() == null ? other.getAmt_type() == null : this.getAmt_type().equals(other.getAmt_type()))
            && (this.getAmt() == null ? other.getAmt() == null : this.getAmt().equals(other.getAmt()))
            && (this.getPay_type() == null ? other.getPay_type() == null : this.getPay_type().equals(other.getPay_type()))
            && (this.getPay_account() == null ? other.getPay_account() == null : this.getPay_account().equals(other.getPay_account()))
            && (this.getHand_person() == null ? other.getHand_person() == null : this.getHand_person().equals(other.getHand_person()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()));
    }

    /**
     *
     * @mbggenerated 2018-02-26
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUser_id() == null) ? 0 : getUser_id().hashCode());
        result = prime * result + ((getAmt_type() == null) ? 0 : getAmt_type().hashCode());
        result = prime * result + ((getAmt() == null) ? 0 : getAmt().hashCode());
        result = prime * result + ((getPay_type() == null) ? 0 : getPay_type().hashCode());
        result = prime * result + ((getPay_account() == null) ? 0 : getPay_account().hashCode());
        result = prime * result + ((getHand_person() == null) ? 0 : getHand_person().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2018-02-26
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", user_id=").append(user_id);
        sb.append(", amt_type=").append(amt_type);
        sb.append(", amt=").append(amt);
        sb.append(", pay_type=").append(pay_type);
        sb.append(", pay_account=").append(pay_account);
        sb.append(", hand_person=").append(hand_person);
        sb.append(", remark=").append(remark);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_time=").append(update_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}