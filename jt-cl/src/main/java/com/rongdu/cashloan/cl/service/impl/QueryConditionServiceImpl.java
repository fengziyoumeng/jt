package com.rongdu.cashloan.cl.service.impl;

import javax.annotation.Resource;

import com.rongdu.cashloan.cl.domain.MerchantBorrower;
import com.rongdu.cashloan.cl.mapper.MerchantBorrowerMapper;
import com.rongdu.cashloan.cl.mapper.QueryConditionMapper;
import com.rongdu.cashloan.core.common.util.JsonUtil;
import com.rongdu.cashloan.core.common.util.StringUtil;
import com.rongdu.cashloan.cl.service.QueryConditionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.service.impl.BaseServiceImpl;
import com.rongdu.cashloan.cl.domain.QueryCondition;

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
 
@Service("queryConditionService")
public class QueryConditionServiceImpl extends BaseServiceImpl<QueryCondition, Long> implements QueryConditionService {
	
    private static final Logger logger = LoggerFactory.getLogger(QueryConditionServiceImpl.class);
   
    @Resource
    private QueryConditionMapper queryConditionMapper;
    @Resource
    private MerchantBorrowerMapper merchantBorrowerMapper;

	@Override
	public BaseMapper<QueryCondition, Long> getMapper() {
		return queryConditionMapper;
	}

	@Override
	public QueryCondition getQueryCondition(Long userId) {
		QueryCondition queryCondition = null;
		try{
			HashMap<String,Object> params = new HashMap();
			params.put("merchantId",userId);

			queryCondition = queryConditionMapper.findSelective(params);
		}catch (Exception e){
			logger.info("查询异常",e);
		}
		return queryCondition;

	}


	@Override
	public void saveOrUpdate(String data,Long userId) {
		try{
			if(StringUtil.isNotBlank(data)){
				QueryCondition queryCondition = JsonUtil.parse(data,QueryCondition.class);
				Map params = JsonUtil.parse(data,Map.class);
				recommend(params,userId);
				if(queryCondition.getId()!=null){
					queryCondition.setUpdateTime(new Date());
					queryConditionMapper.updateSelective(params);
				}else{
					queryCondition.setMerchantId(userId);
					queryCondition.setUpdateTime(new Date());
					queryConditionMapper.save(queryCondition);
				}
			}
		}catch (Exception e){
			logger.info("设置条件失败",e);
		}

	}

	public void recommend(Map<String,Object> condition,Long userId){
		//查询出当前用户拥有哪些用户的数据
		List<MerchantBorrower> hasUserData = merchantBorrowerMapper.queryUserById(userId);
		for (MerchantBorrower hasUserDatum : hasUserData) {
			System.out.println(hasUserDatum.getBorrowerId());
		}
		System.out.println("-=======================-");
		//查询出所有符合条件的用户
		List<MerchantBorrower> user = queryConditionMapper.queryUserByCondition(condition);

		for (MerchantBorrower aLong : user) {
			System.out.println(aLong.getBorrowerId());

		}
	}
}