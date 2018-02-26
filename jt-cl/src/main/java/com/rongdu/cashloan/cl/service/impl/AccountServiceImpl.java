package com.rongdu.cashloan.cl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rongdu.cashloan.cl.domain.AccountDetailInfo;
import com.rongdu.cashloan.cl.domain.AccountInfo;
import com.rongdu.cashloan.cl.mapper.AccountDetailInfoMapper;
import com.rongdu.cashloan.cl.mapper.AccountInfoMapper;
import com.rongdu.cashloan.cl.service.AccountService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    public static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Resource
    private AccountInfoMapper accountInfoMapper;

    @Resource
    private AccountDetailInfoMapper accountDetailInfoMapper;

    @Override
    public Page<AccountDetailInfo> getAllAccountDetailInfo(Map<String, Object> params,int currentPage,int pageSize) throws Exception {
        try {
            PageHelper.startPage(currentPage, pageSize);
            return(Page<AccountDetailInfo>)accountDetailInfoMapper.listAccountDetailInfos(params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage(),e, Constant.FAIL_CODE_VALUE);
        }
    }

    @Override
    public AccountInfo getAccountInfo(Map<String, Object> params) throws Exception {
        return accountInfoMapper.getAccountInfo(params);
    }
}
