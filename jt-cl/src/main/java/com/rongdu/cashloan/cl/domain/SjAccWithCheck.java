package com.rongdu.cashloan.cl.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SjAccWithCheck implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     *商家id
     */
    private Long user_id;

    /**
     * 用户数
     */
    private Long count_borrower;

    /**
     * 扣款日期
     */
    private Date date;

    /**
     * 扣款日期
     */
    private Date update_date;

    /**
     * CPA单价
     */
    private BigDecimal unit_price;

    /**
     * 扣款金额
     */
    private BigDecimal amt;

    /**
     * sj_acc_withcheck
     */
    private static final long serialVersionUID = 1L;



    /**
     * 扣款日期
     * @return date 扣款日期
     */
    public Date getDate() {
        return date;
    }

    /**
     * 扣款日期
     * @param date 扣款日期
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * CPA单价
     * @return unit_price CPA单价
     */
    public BigDecimal getUnit_price() {
        return unit_price;
    }

    /**
     * CPA单价
     * @param unit_price CPA单价
     */
    public void setUnit_price(BigDecimal unit_price) {
        this.unit_price = unit_price;
    }

    /**
     * 扣款金额
     * @return amt 扣款金额
     */
    public BigDecimal getAmt() {
        return amt;
    }

    /**
     * 扣款金额
     * @param amt 扣款金额
     */
    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }

    public Long getCount_borrower() {
        return count_borrower;
    }

    public void setCount_borrower(Long count_borrower) {
        this.count_borrower = count_borrower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SjAccWithCheck that = (SjAccWithCheck) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (user_id != null ? !user_id.equals(that.user_id) : that.user_id != null) return false;
        if (count_borrower != null ? !count_borrower.equals(that.count_borrower) : that.count_borrower != null)
            return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (update_date != null ? !update_date.equals(that.update_date) : that.update_date != null) return false;
        if (unit_price != null ? !unit_price.equals(that.unit_price) : that.unit_price != null) return false;
        return amt != null ? amt.equals(that.amt) : that.amt == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user_id != null ? user_id.hashCode() : 0);
        result = 31 * result + (count_borrower != null ? count_borrower.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (update_date != null ? update_date.hashCode() : 0);
        result = 31 * result + (unit_price != null ? unit_price.hashCode() : 0);
        result = 31 * result + (amt != null ? amt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SjAccWithCheck{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", count_borrower=" + count_borrower +
                ", date=" + date +
                ", update_date=" + update_date +
                ", unit_price=" + unit_price +
                ", amt=" + amt +
                '}';
    }
}