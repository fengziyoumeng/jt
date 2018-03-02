package com.rongdu.cashloan.cl.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SjAccWithCheck implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 扣款日期
     */
    private Date date;

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
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

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

    /**
     *
     * @mbggenerated 2018-03-01
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
        SjAccWithCheck other = (SjAccWithCheck) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDate() == null ? other.getDate() == null : this.getDate().equals(other.getDate()))
            && (this.getUnit_price() == null ? other.getUnit_price() == null : this.getUnit_price().equals(other.getUnit_price()))
            && (this.getAmt() == null ? other.getAmt() == null : this.getAmt().equals(other.getAmt()));
    }

    /**
     *
     * @mbggenerated 2018-03-01
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDate() == null) ? 0 : getDate().hashCode());
        result = prime * result + ((getUnit_price() == null) ? 0 : getUnit_price().hashCode());
        result = prime * result + ((getAmt() == null) ? 0 : getAmt().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2018-03-01
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", unit_price=").append(unit_price);
        sb.append(", amt=").append(amt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}