package com.rongdu.cashloan.cl.mapper;

import com.rongdu.cashloan.cl.domain.SjAccWithCheck;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RDBatisDao
public interface SjAccWithCheckMapper {
    /**
     *
     * @mbggenerated 2018-03-01
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-03-01
     */
    int insert(SjAccWithCheck record);

    /**
     *
     * @mbggenerated 2018-03-01
     */
    int insertSelective(SjAccWithCheck record);

    /**
     *
     * @mbggenerated 2018-03-01
     */
    SjAccWithCheck selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2018-03-01
     */
    int updateByPrimaryKeySelective(SjAccWithCheck record);

    /**
     *
     * @mbggenerated 2018-03-01
     */
    int updateByPrimaryKey(SjAccWithCheck record);

    int insertBatch(List<SjAccWithCheck> sjAccWithCheckList);

    List<SjAccWithCheck> listSjAccWithCheckInfos(Map<String,Object> map);

    Integer queryTodayData(HashMap<Object, Object> params);

    int update(SjAccWithCheck sjAccWithCheck);
}