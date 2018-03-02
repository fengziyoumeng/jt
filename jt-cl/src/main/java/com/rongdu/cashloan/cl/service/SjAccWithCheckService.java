package com.rongdu.cashloan.cl.service;

import com.github.pagehelper.Page;
import com.rongdu.cashloan.cl.domain.SjAccWithCheck;

import java.util.Map;

public interface SjAccWithCheckService {

    /**
     * 保存到扣款表单的表中
     * @return
     * @throws Exception
     */
    boolean saveWithholdCheck() throws Exception;

    Page<SjAccWithCheck> getAllWithCheckInfo(Map<String, Object> params, int currentPage, int pageSize) throws Exception;
}
