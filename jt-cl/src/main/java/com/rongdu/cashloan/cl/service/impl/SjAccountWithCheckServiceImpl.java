package com.rongdu.cashloan.cl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rongdu.cashloan.cl.domain.AccountDetailInfo;
import com.rongdu.cashloan.cl.domain.AccountInfo;
import com.rongdu.cashloan.cl.domain.SjAccWithCheck;
import com.rongdu.cashloan.cl.mapper.AccountDetailInfoMapper;
import com.rongdu.cashloan.cl.mapper.AccountInfoMapper;
import com.rongdu.cashloan.cl.mapper.MerchantBorrowerMapper;
import com.rongdu.cashloan.cl.mapper.SjAccWithCheckMapper;
import com.rongdu.cashloan.cl.service.SjAccWithCheckService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("sjAccWithCheckService")
public class SjAccountWithCheckServiceImpl implements SjAccWithCheckService {

    public static final Logger logger = LoggerFactory.getLogger(SjAccountWithCheckServiceImpl.class);

    @Resource
    private MerchantBorrowerMapper merchantBorrowerMapper;

    @Resource
    private SjAccWithCheckMapper sjAccWithCheckMapper;

    @Override
    public boolean saveWithholdCheck() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        //得到前一天日期
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String addTime = dateFormat.format(calendar.getTime());
        params.put("addTime",addTime);
        try{
            //统计得到前一天所有商户id对应的各自用户数,并计算出扣款金额
            List<Map<String, Object>> merList = merchantBorrowerMapper.countBorrowersByGroup(params);
            List<SjAccWithCheck> sjAccWithCheckList = new ArrayList<SjAccWithCheck>();
            if(merList!=null && merList.size()>0){
                for(Map<String, Object> map : merList){
                    SjAccWithCheck sjAccWithCheck = new SjAccWithCheck();
                    BigDecimal countMan = new BigDecimal((Long) map.get("merchant_count"));
                    BigDecimal price = (BigDecimal)map.get("price");
                    BigDecimal tolPrice = countMan.multiply(price);
                    sjAccWithCheck.setAmt(tolPrice);
                    sjAccWithCheck.setUser_id((Long)map.get("merchant_id"));
                    sjAccWithCheck.setCount_borrower((Long) map.get("merchant_count"));
                    sjAccWithCheck.setUnit_price(price);
                    sjAccWithCheck.setDate(dateFormat.parse(addTime));
                    sjAccWithCheck.setUpdate_date(new Date());
                    sjAccWithCheckList.add(sjAccWithCheck);
                }
                sjAccWithCheckMapper.insertBatch(sjAccWithCheckList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Page<SjAccWithCheck> getAllWithCheckInfo(Map<String, Object> params, int currentPage, int pageSize) throws Exception {
        try {
            PageHelper.startPage(currentPage, pageSize);
            return(Page<SjAccWithCheck>)sjAccWithCheckMapper.listSjAccWithCheckInfos(params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage(),e, Constant.FAIL_CODE_VALUE);
        }
    }
}
