package com.rongdu.cashloan.cl.service;

import com.rongdu.cashloan.core.common.service.BaseService;
import com.rongdu.cashloan.cl.domain.QueryCondition;

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
public interface QueryConditionService extends BaseService<QueryCondition, Long>{

    QueryCondition getQueryCondition(Long userId);

    void saveOrUpdate(String data,Long userId) throws Exception;
}
