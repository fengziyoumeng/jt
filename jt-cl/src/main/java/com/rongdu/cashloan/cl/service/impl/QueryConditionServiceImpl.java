package com.rongdu.cashloan.cl.service.impl;

import javax.annotation.Resource;

import com.rongdu.cashloan.cl.domain.*;
import com.rongdu.cashloan.cl.mapper.*;
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
    @Resource
    private SjAccWithCheckMapper sjAccWithCheckMapper;
    @Resource
    private AccountInfoMapper accountInfoMapper;
    @Resource
    private AccountDetailInfoMapper accountDetailInfoMapper;

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
		//从数据字典获取当前数据单价
		SysDictDetail detail = sysDictDetailService.findDetail("DATA_PRICE", "USER_DATA_PRICE");
		//获取推荐的用户,包括去重
		List<MerchantBorrower> fetchUserList = getFetchDataList(userId, condition);
		//把处理好的数据插入到关系表
		for (MerchantBorrower fetchuser : fetchUserList) {
			fetchuser.setAddTime(new Date());
			fetchuser.setMerchantId(userId);
			fetchuser.setPrice(new BigDecimal(detail.getItemValue()));
			fetchuser.setAudit(0);
		}
		merchantBorrowerMapper.batchInsert(fetchUserList);
		//给被推荐的用户推荐数加1
		addRecommendTimes(fetchUserList);

		BigDecimal unitPrice = new BigDecimal(detail.getItemValue());
		//花费的总额
		BigDecimal amount = unitPrice.multiply(new BigDecimal(fetchUserList.size()));

		//保存当天统计数据
		statistical(userId,amount,unitPrice,fetchUserList.size());
        //对账户进行扣款操作
		checkOut(userId,amount);
        //添加一条账户流水
		addAccountRecord(userId,amount);
	}



	public List<MerchantBorrower> getFetchDataList(Long userId,Map<String,Object> condition){
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

		return fetchUserList;
	}
	//给被推荐的用户的推荐数加1
	public void addRecommendTimes(List<MerchantBorrower> fetchUserList){
		queryConditionMapper.updateRecommendTimes(fetchUserList);
	}
	//保存当天的统计数据
	public void statistical(Long userId,BigDecimal amount,BigDecimal unitPrice,Integer countBorrower){
		SjAccWithCheck sjAccWithCheck = new SjAccWithCheck();
		sjAccWithCheck.setUser_id(userId);
		sjAccWithCheck.setCount_borrower(Long.valueOf(countBorrower));
		sjAccWithCheck.setDate(new Date());
		sjAccWithCheck.setUnit_price(unitPrice);
		sjAccWithCheck.setAmt(amount);
		sjAccWithCheck.setUpdate_date(new Date());
		//先查询当前用户在当天是否有记录
		HashMap<Object, Object> params = new HashMap<>();
		params.put("userId",userId);
		Integer num = sjAccWithCheckMapper.queryTodayData(params);
		if(num>0){
			sjAccWithCheckMapper.update(sjAccWithCheck);
		}else{
			sjAccWithCheckMapper.insert(sjAccWithCheck);
		}
	}

	//结账扣款
	public void checkOut(Long userId,BigDecimal amount){
		AccountInfo accountInfo = new AccountInfo();
		//balance 在这里设置的是当前花费的金额,不是余额,用于在sql中减去当前花费
		accountInfo.setBalance(amount);
		accountInfo.setUpdate_time(new Date());
		accountInfo.setUser_id(userId);
		accountInfoMapper.updateByUserId(accountInfo);
	}

	//添加账户流水
	public void addAccountRecord(Long userId,BigDecimal amount){
		AccountDetailInfo accountDetailInfo = new AccountDetailInfo();
		accountDetailInfo.setAmt(amount);
		accountDetailInfo.setAmt_type(2);
		accountDetailInfo.setCreate_time(new Date());
		accountDetailInfo.setUpdate_time(new Date());
		accountDetailInfo.setHand_person("系统");
		accountDetailInfo.setUser_id(userId);
		accountDetailInfo.setRemark("添加用户后扣款");
		accountDetailInfoMapper.insert(accountDetailInfo);
	}



}