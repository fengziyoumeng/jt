package com.rongdu.cashloan.cl.mapper;

import com.rongdu.cashloan.cl.domain.UserData;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;
import com.rongdu.cashloan.cl.domain.MerchantBorrower;

import java.util.List;
import java.util.Map;

/**
 * 用户消息Dao
 * 
 * @author Yang
 * @version 1.0.0
 * @date 2018-02-27 11:57:15
 * Copyright 杭州民华金融信息服务有限公司  cashloan All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@RDBatisDao
public interface MerchantBorrowerMapper extends BaseMapper<MerchantBorrower, Long> {

    void batchInsert(List<MerchantBorrower> params);

    List<UserData> getUserDataList(Map<String, Object> params);

    void setAuditStatus(Map<String, Object> params);

    Long countBorrowers(Map<String, Object> params);

    List<Map<String, Object>> countBorrowersByGroup(Map<String, Object> params);

    List<MerchantBorrower> queryUserById(Long userId);
}
