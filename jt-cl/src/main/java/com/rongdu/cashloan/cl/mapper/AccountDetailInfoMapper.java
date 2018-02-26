package com.rongdu.cashloan.cl.mapper;

import com.rongdu.cashloan.cl.domain.AccountDetailInfo;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

@RDBatisDao
public interface AccountDetailInfoMapper {
    /**
     *
     * @mbggenerated 2018-02-26
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-02-26
     */
    int insert(AccountDetailInfo record);

    /**
     *
     * @mbggenerated 2018-02-26
     */
    int insertSelective(AccountDetailInfo record);

    /**
     *
     * @mbggenerated 2018-02-26
     */
    AccountDetailInfo selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-02-26
     */
    int updateByPrimaryKeySelective(AccountDetailInfo record);

    /**
     *
     * @mbggenerated 2018-02-26
     */
    int updateByPrimaryKey(AccountDetailInfo record);

    List<AccountDetailInfo> listAccountDetailInfos(Map<String,Object> map);
}