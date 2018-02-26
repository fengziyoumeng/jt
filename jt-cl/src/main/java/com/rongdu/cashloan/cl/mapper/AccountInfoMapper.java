package com.rongdu.cashloan.cl.mapper;

import com.rongdu.cashloan.cl.domain.AccountInfo;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;

import java.util.Map;

@RDBatisDao
public interface AccountInfoMapper {
    /**
     *
     * @mbggenerated 2018-02-26
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-02-26
     */
    int insert(AccountInfo record);

    /**
     *
     * @mbggenerated 2018-02-26
     */
    int insertSelective(AccountInfo record);

    /**
     *
     * @mbggenerated 2018-02-26
     */
    AccountInfo selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated 2018-02-26
     */
    int updateByPrimaryKeySelective(AccountInfo record);

    /**
     *
     * @mbggenerated 2018-02-26
     */
    int updateByPrimaryKey(AccountInfo record);

    AccountInfo getAccountInfo(Map<String,Object> map);
}