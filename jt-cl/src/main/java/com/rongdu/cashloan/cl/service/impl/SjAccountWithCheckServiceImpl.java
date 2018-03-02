package com.rongdu.cashloan.cl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rongdu.cashloan.cl.domain.AccountDetailInfo;
import com.rongdu.cashloan.cl.domain.AccountInfo;
import com.rongdu.cashloan.cl.domain.SjAccWithCheck;
import com.rongdu.cashloan.cl.mapper.AccountDetailInfoMapper;
import com.rongdu.cashloan.cl.mapper.AccountInfoMapper;
import com.rongdu.cashloan.cl.mapper.MerchantBorrowerMapper;
import com.rongdu.cashloan.cl.service.SjAccWithCheckService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sjAccWithCheckService")
public class SjAccountWithCheckServiceImpl implements SjAccWithCheckService {

    public static final Logger logger = LoggerFactory.getLogger(SjAccountWithCheckServiceImpl.class);

    @Resource
    private MerchantBorrowerMapper merchantBorrowerMapper;

    @Override
    public boolean saveWithholdCheck() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        //得到前一天日期
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String addTime = dateFormat.format(calendar.getTime());
        params.put("addTime",addTime);
        //统计得到前一天所有商户id对应的各自用户数
        List<Map<String, Object>> merList = merchantBorrowerMapper.countBorrowersByGroup(params);
        if(merList!=null && merList.size()>0){
            for(Map<String, Object> map : merList){
                SjAccWithCheck sjAccWithCheck = new SjAccWithCheck();
            }
        }

        //计算出扣款金额
        return false;
    }
}
