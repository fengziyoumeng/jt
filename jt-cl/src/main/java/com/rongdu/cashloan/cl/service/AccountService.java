package com.rongdu.cashloan.cl.service;

import com.github.pagehelper.Page;
import com.rongdu.cashloan.cl.domain.AccountDetailInfo;
import com.rongdu.cashloan.cl.domain.AccountInfo;
import com.rongdu.cashloan.cl.domain.ClFlowInfo;

import java.util.List;
import java.util.Map;

public interface AccountService {

    /**
     * 获得账户明细
     * @param params
     * @return
     * @throws Exception
     */
    Page<AccountDetailInfo> getAllAccountDetailInfo(Map<String, Object> params, int currentPage, int pageSize) throws Exception;

    /**
     * 获得账户余额
     * @param params
     * @return
     * @throws Exception
     */
    Map<String,Object> getAccInfo(Map<String, Object> params) throws Exception;

    void saveOrUpdate(boolean flag,AccountInfo accountInfo) throws Exception;

    Long getAccount(Map<String, Object> params) throws Exception;;

    void detailInsert(AccountDetailInfo accountDetailInfo) throws Exception;;
}
