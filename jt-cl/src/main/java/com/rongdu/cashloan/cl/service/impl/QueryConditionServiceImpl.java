package com.rongdu.cashloan.cl.service.impl;

import javax.annotation.Resource;

import com.rongdu.cashloan.cl.domain.MerchantBorrower;
import com.rongdu.cashloan.cl.mapper.MerchantBorrowerMapper;
import com.rongdu.cashloan.cl.mapper.QueryConditionMapper;
import com.rongdu.cashloan.core.common.exception.ServiceException;
import com.rongdu.cashloan.core.common.exception.SimpleMessageException;
import com.rongdu.cashloan.core.common.util.JsonUtil;
import com.rongdu.cashloan.core.common.util.StringUtil;
import com.rongdu.cashloan.cl.service.QueryConditionService;
import com.rongdu.cashloan.system.domain.SysDictDetail;
import com.rongdu.cashloan.system.serviceNoSharding.SysDictDetailService;
import com.rongdu.cashloan.system.serviceNoSharding.SysDictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.service.impl.BaseServiceImpl;
import com.rongdu.cashloan.cl.domain.QueryCondition;

import java.math.BigDecimal;
import java.util.*;


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
    @Resource
    private SysDictDetailService sysDictDetailService;

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
	public void saveOrUpdate(String data,Long userId) throws Exception{
		try{
			if(StringUtil.isNotBlank(data)){
				QueryCondition queryCondition = JsonUtil.parse(data,QueryCondition.class);
				Map params = JsonUtil.parse(data,Map.class);
				if(params.get("dataAmount")==null
						||params.get("zmScoreMin")==null
						||params.get("zmScoreMax")==null
						||params.get("ageMin")==null
						||params.get("ageMax")==null){
					throw new SimpleMessageException("条件设置有误，请重新设置！");
				}
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
			throw e;
		}

	}

	public void recommend(Map<String,Object> condition,Long userId) throws ServiceException {
		//查询出当前用户拥有哪些用户的数据
		List<MerchantBorrower> hasUserDataList = merchantBorrowerMapper.queryUserById(userId);

		//查询出所有符合条件的用户
		List<MerchantBorrower> fetchUserList = queryConditionMapper.queryUserByCondition(condition);

		//去除被重复推荐的用户
		Iterator<MerchantBorrower> itFetchUser = fetchUserList.iterator();
		while(itFetchUser.hasNext()){
			Iterator<MerchantBorrower> itHasUser = hasUserDataList.iterator();
			Long fetch = itFetchUser.next().getBorrowerId();
			while(itHasUser.hasNext()){
				Long hasUser = itHasUser.next().getBorrowerId();
				if(fetch.equals(hasUser)){
					itFetchUser.remove();
				}
			}
		}

		//截取用户要求的数量
		if( Integer.parseInt(condition.get("dataAmount").toString().trim()) <= fetchUserList.size()){
			fetchUserList = fetchUserList.subList(0, Integer.parseInt(condition.get("dataAmount").toString().trim()));
		}else if(fetchUserList.size()!=0){
			throw new RuntimeException("当前符合条件的数据只有"+fetchUserList.size()+"条，如果需要，请把需求数量改为"+fetchUserList.size());
		}else {
			throw new RuntimeException("抱歉，没有找到符合当前条件的数据，请重新更改条件后查询！");
		}

		//从数据字典获取当前数据单价
		SysDictDetail detail = sysDictDetailService.findDetail("DATA_PRICE", "USER_DATA_PRICE");

		//把处理好的数据插入到关系表
		for (MerchantBorrower fetchuser : fetchUserList) {
			fetchuser.setAddTime(new Date());
			fetchuser.setMerchantId(userId);
			fetchuser.setPrice(new BigDecimal(detail.getItemValue()));
			fetchuser.setAudit(0);
			merchantBorrowerMapper.save(fetchuser);
		}
	}
}