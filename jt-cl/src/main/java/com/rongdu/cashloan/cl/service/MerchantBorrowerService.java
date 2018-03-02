package com.rongdu.cashloan.cl.service;

import com.github.pagehelper.Page;
import com.rongdu.cashloan.core.common.service.BaseService;
import com.rongdu.cashloan.cl.domain.MerchantBorrower;
import com.rongdu.cashloan.cl.domain.UserData;

import java.util.Map;

/**
 * 用户消息Service
 * 
 * @author Yang
 * @version 1.0.0
 * @date 2018-02-26 10:56:07
 * Copyright 杭州民华金融信息服务有限公司  cashloan All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
public interface MerchantBorrowerService extends BaseService<UserData, Long>{

    Page<UserData> getUserDataList(Map<String,Object> params);

    void setAuditStatus(Long id, String borrowerId, String audit);
}
