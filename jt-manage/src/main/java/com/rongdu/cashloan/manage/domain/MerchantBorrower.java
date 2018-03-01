package com.rongdu.cashloan.manage.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户消息实体
 * 
 * @author Yang
 * @version 1.0.0
 * @date 2018-02-27 11:57:15
 * Copyright 杭州民华金融信息服务有限公司  cashloan All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
 public class MerchantBorrower implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 商户id
    */
    private Long merchantId;

    /**
    * 借款人id
    */
    private Long borrowerId;

    /**
    * 是否审核0-未审核，1-已通过，2-未通过
    */
    private Integer auth;

    /**
    * 是否有详细信息0-没有，1-有
    */
    private Integer isDetail;

    private Date addTime;


    /**
    * 获取商户id
    *
    * @return 商户id
    */
    public Long getMerchantId(){
        return merchantId;
    }

    /**
    * 设置商户id
    * 
    * @param merchantId 要设置的商户id
    */
    public void setMerchantId(Long merchantId){
        this.merchantId = merchantId;
    }

    /**
    * 获取借款人id
    *
    * @return 借款人id
    */
    public Long getBorrowerId(){
        return borrowerId;
    }

    /**
    * 设置借款人id
    * 
    * @param borrowerId 要设置的借款人id
    */
    public void setBorrowerId(Long borrowerId){
        this.borrowerId = borrowerId;
    }

    /**
    * 获取是否审核0-未审核，1-已通过，2-未通过
    *
    * @return 是否审核0-未审核，1-已通过，2-未通过
    */
    public Integer getAuth(){
        return auth;
    }

    /**
    * 设置是否审核0-未审核，1-已通过，2-未通过
    * 
    * @param auth 要设置的是否审核0-未审核，1-已通过，2-未通过
    */
    public void setAuth(Integer auth){
        this.auth = auth;
    }

    /**
    * 获取是否有详细信息0-没有，1-有
    *
    * @return 是否有详细信息0-没有，1-有
    */
    public Integer getIsDetail(){
        return isDetail;
    }

    /**
    * 设置是否有详细信息0-没有，1-有
    * 
    * @param isDetail 要设置的是否有详细信息0-没有，1-有
    */
    public void setIsDetail(Integer isDetail){
        this.isDetail = isDetail;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}