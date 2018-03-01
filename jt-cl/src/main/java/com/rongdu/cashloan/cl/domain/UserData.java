package com.rongdu.cashloan.cl.domain;

import java.math.BigDecimal;
import java.util.Date;

public class UserData {

    private Long userId;
    private Long borrowerId;
    private String cardId;
    private Integer cardType;
    private String realName;
    private Integer sex;
    private Integer age;
    private String qq;
    private String weixin;
    private Integer authStatus;
    private Integer zhimaStatus;
    private Integer jingdongStatus;
    private Integer operatorStatus;
    private Integer isAuthPay;
    private String provinceAddress;
    private String cityAddress;
    private Date authTime;

    private String authMobile;
    private Integer zhimaScore;
    private BigDecimal price;
    private Integer isDetail;
    private Integer audit;

    private Date addTime;

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public Integer getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Integer authStatus) {
        this.authStatus = authStatus;
    }

    public Integer getZhimaStatus() {
        return zhimaStatus;
    }

    public void setZhimaStatus(Integer zhimaStatus) {
        this.zhimaStatus = zhimaStatus;
    }

    public Integer getJingdongStatus() {
        return jingdongStatus;
    }

    public void setJingdongStatus(Integer jingdongStatus) {
        this.jingdongStatus = jingdongStatus;
    }

    public Integer getOperatorStatus() {
        return operatorStatus;
    }

    public void setOperatorStatus(Integer operatorStatus) {
        this.operatorStatus = operatorStatus;
    }

    public Integer getIsAuthPay() {
        return isAuthPay;
    }

    public void setIsAuthPay(Integer isAuthPay) {
        this.isAuthPay = isAuthPay;
    }

    public String getProvinceAddress() {
        return provinceAddress;
    }

    public void setProvinceAddress(String provinceAddress) {
        this.provinceAddress = provinceAddress;
    }

    public String getCityAddress() {
        return cityAddress;
    }

    public void setCityAddress(String cityAddress) {
        this.cityAddress = cityAddress;
    }

    public String getAuthMobile() {
        return authMobile;
    }

    public void setAuthMobile(String authMobile) {
        this.authMobile = authMobile;
    }

    public Integer getZhimaScore() {
        return zhimaScore;
    }

    public void setZhimaScore(Integer zhimaScore) {
        this.zhimaScore = zhimaScore;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getIsDetail() {
        return isDetail;
    }

    public void setIsDetail(Integer isDetail) {
        this.isDetail = isDetail;
    }

    public Integer getAudit() {
        return audit;
    }

    public void setAudit(Integer audit) {
        this.audit = audit;
    }

    public Date getAuthTime() {
        return authTime;
    }

    public void setAuthTime(Date authTime) {
        this.authTime = authTime;
    }


    @Override
    public String toString() {
        return "UserData{" +
                "userId=" + userId +
                ", borrowerId=" + borrowerId +
                ", cardId='" + cardId + '\'' +
                ", cardType=" + cardType +
                ", realName='" + realName + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", qq='" + qq + '\'' +
                ", weixin='" + weixin + '\'' +
                ", authStatus=" + authStatus +
                ", zhimaStatus=" + zhimaStatus +
                ", jingdongStatus=" + jingdongStatus +
                ", operatorStatus=" + operatorStatus +
                ", isAuthPay=" + isAuthPay +
                ", provinceAddress='" + provinceAddress + '\'' +
                ", cityAddress='" + cityAddress + '\'' +
                ", authTime=" + authTime +
                ", authMobile='" + authMobile + '\'' +
                ", zhimaScore=" + zhimaScore +
                ", price=" + price +
                ", isDetail=" + isDetail +
                ", audit=" + audit +
                ", addTime=" + addTime +
                '}';
    }
}
