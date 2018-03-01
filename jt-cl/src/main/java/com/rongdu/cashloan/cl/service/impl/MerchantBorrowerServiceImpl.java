package com.rongdu.cashloan.cl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rongdu.cashloan.cl.mapper.MerchantBorrowerMapper;
import com.rongdu.cashloan.cl.mapper.UserDataMapper;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.service.impl.BaseServiceImpl;
import com.rongdu.cashloan.core.common.util.DateUtil;
import com.rongdu.cashloan.cl.domain.MerchantBorrower;
import com.rongdu.cashloan.cl.domain.UserData;
import com.rongdu.cashloan.cl.service.MerchantBorrowerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户消息ServiceImpl
 * 
 * @author Yang
 * @version 1.0.0
 * @date 2018-02-26 10:56:07
 * Copyright 杭州民华金融信息服务有限公司  cashloan All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
 
@Service("merchantBorrowerService")
public class MerchantBorrowerServiceImpl extends BaseServiceImpl<UserData, Long> implements MerchantBorrowerService {
	
    private static final Logger logger = LoggerFactory.getLogger(MerchantBorrowerServiceImpl.class);
   
    @Resource
    private UserDataMapper userDataMapper;
    @Resource
    private MerchantBorrowerMapper merchantBorrowerMapper;

	@Override
	public BaseMapper<UserData, Long> getMapper() {
		return userDataMapper;
	}

	@Override
	public Page<MerchantBorrower> getUserDataList(Map<String,Object> params) {
		List<MerchantBorrower> merchantBorrower = null;
		try{

			if(params.get("end")!=null && params.get("start")!=null){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date end = format.parse(params.get("end").toString());
				Date dayEndTime = DateUtil.getDayEndTime(end);

				params.put("end",format.format(dayEndTime));
			}

			PageHelper.startPage((int)params.get("current"), (int)params.get("pageSize"));
			merchantBorrower = merchantBorrowerMapper.getUserDataList(params);
		}catch (Exception e){
			logger.info("查询失败",e);
		}
		return (Page<MerchantBorrower>)merchantBorrower;
	}

	@Override
	public void setAuditStatus(Long merchantId, String borrowerId, String audit) {
		Map<String,Object> params = new HashMap();
		try{
			params.put("merchantId",merchantId);
			params.put("borrowerId",borrowerId);
			params.put("audit",audit);
			merchantBorrowerMapper.setAuditStatus(params);
		}catch (Exception e){
			logger.info("状态设置失败",e);
		}
	}
}