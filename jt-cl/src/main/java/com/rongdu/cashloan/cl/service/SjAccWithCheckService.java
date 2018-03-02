package com.rongdu.cashloan.cl.service;

import java.util.Map;

public interface SjAccWithCheckService {

    /**
     * 保存到扣款表单的表中
     * @return
     * @throws Exception
     */
    boolean saveWithholdCheck() throws Exception;
}
