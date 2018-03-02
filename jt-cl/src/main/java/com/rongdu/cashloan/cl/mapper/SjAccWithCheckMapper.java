package com.rongdu.cashloan.cl.mapper;

import com.rongdu.cashloan.cl.domain.SjAccWithCheck;

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
}