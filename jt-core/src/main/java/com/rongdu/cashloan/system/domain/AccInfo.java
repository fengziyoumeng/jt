package com.rongdu.cashloan.system.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccInfo implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private Long user_id;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 
     */
    private Date create_time;

    /**
     * 
     */
    private Date update_time;

    /**
     * sj_account
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
     * 余额
     * @return balance 余额
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 余额
     * @param balance 余额
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccInfo accInfo = (AccInfo) o;

        if (id != null ? !id.equals(accInfo.id) : accInfo.id != null) return false;
        if (user_id != null ? !user_id.equals(accInfo.user_id) : accInfo.user_id != null) return false;
        if (balance != null ? !balance.equals(accInfo.balance) : accInfo.balance != null) return false;
        if (create_time != null ? !create_time.equals(accInfo.create_time) : accInfo.create_time != null) return false;
        return update_time != null ? update_time.equals(accInfo.update_time) : accInfo.update_time == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user_id != null ? user_id.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (create_time != null ? create_time.hashCode() : 0);
        result = 31 * result + (update_time != null ? update_time.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AccInfo{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", balance=" + balance +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                '}';
    }
}