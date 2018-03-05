package com.rongdu.cashloan.manage.controller;

import com.github.pagehelper.Page;
import com.rongdu.cashloan.cl.domain.AccountDetailInfo;
import com.rongdu.cashloan.cl.domain.AccountInfo;
import com.rongdu.cashloan.cl.domain.ClFlowInfo;
import com.rongdu.cashloan.cl.domain.SjAccWithCheck;
import com.rongdu.cashloan.cl.service.AccountService;
import com.rongdu.cashloan.cl.service.SjAccWithCheckService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.JsonUtil;
import com.rongdu.cashloan.core.common.util.RdPage;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.web.controller.BaseController;
import com.rongdu.cashloan.core.constant.AppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Scope("prototype")
@Controller
public class AccountController extends BaseController {
    public static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Resource
    private AccountService accountService;

    @Resource
    private SjAccWithCheckService sjAccWithCheckService;

    /**
     * 账户充值明细
     * @param searchParams
     * @param current
     * @param pageSize
     * @throws Exception
     */
    @RequestMapping(value = "/acc/accountDetail/list.htm")
    public void  getAllAccountDetail(@RequestParam(value="searchParams",required=false) String searchParams,
                             @RequestParam(value = "current",required=false) int current,
                             @RequestParam(value = "pageSize",required=false) int pageSize,
                             @RequestParam(value = "userId",required=false) int userId ) throws Exception {
        Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
        if(params!=null){
            params.put("sDate",String.valueOf(params.get("beginTime")));
            params.put("eDate",String.valueOf(params.get("endTime")));
        }else{
            params = new HashMap<String,Object>();
        }
        params.put("userId",userId);
        params.put("amtType",1);
        Page<AccountDetailInfo> page = accountService.getAllAccountDetailInfo(params,current,pageSize);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put(Constant.RESPONSE_DATA, page);
        result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * 获得账户余额和用户数
     * @throws Exception
     */
    @RequestMapping(value = "/acc/account/balance.htm")
    public void homeInfo(@RequestParam(value = "userId",required=false) int userId) throws Exception {
        Map<String,Object> resultMap = null ;
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("userId",userId);
        resultMap = accountService.getAccInfo(paramMap);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put(Constant.RESPONSE_DATA, resultMap);
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * 扣款账单
     * @param searchParams
     * @param current
     * @param pageSize
     * @throws Exception
     */
    @RequestMapping(value = "/acc/withCheck/list.htm")
    public void  getAllWithCheck(@RequestParam(value="searchParams",required=false) String searchParams,
                                     @RequestParam(value = "current",required=false) int current,
                                     @RequestParam(value = "pageSize",required=false) int pageSize,
                                     @RequestParam(value = "userId",required=false) int userId ) throws Exception {
        Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
        if(params!=null){
            params.put("sDate",String.valueOf(params.get("beginTime")));
            params.put("eDate",String.valueOf(params.get("endTime")));
        }else{
            params = new HashMap<String,Object>();
        }
        params.put("userId",userId);
        Page<SjAccWithCheck> page = sjAccWithCheckService.getAllWithCheckInfo(params,current,pageSize);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put(Constant.RESPONSE_DATA, page);
        result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * 账户充值
     * @throws Exception
     */
    @RequestMapping(value = "/acc/account/charge.htm")
    public void chargeAccount(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(value = "form") String data) throws Exception {
        //将前台传的数据进行解析
        HashMap<String, Object> dataMap = JsonUtil.parse(data, HashMap.class);
        String user_id = dataMap.get("user_id")!=null ? dataMap.get("user_id").toString() :"";
        String amt = dataMap.get("amt")!=null ? dataMap.get("amt").toString() :"";
        String pay_type = dataMap.get("pay_type")!=null ? dataMap.get("pay_type").toString() :"";
        String pay_account = dataMap.get("pay_account")!=null ? dataMap.get("pay_account").toString() :"";
        String hand_person = dataMap.get("hand_person")!=null ? dataMap.get("hand_person").toString() :"";
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setCreate_time(new Date());
        accountInfo.setUpdate_time(new Date());
        accountInfo.setUser_id(user_id == null ?0:Long.parseLong(user_id));
        accountInfo.setBalance(new BigDecimal(amt == null ?0:Double.parseDouble(amt)));
        boolean flag = false;
        Map<String,Object> paraMap = new HashMap<String,Object>();
        paraMap.put("userId",accountInfo.getUser_id());
        Long id = accountService.getAccount(paraMap);
        if(id==null){ //不存在，直接保存
            accountService.saveOrUpdate(flag,accountInfo);
        }else{ //存在，更新
            flag = true;
            accountInfo.setId(id);
            accountInfo.setBalance(new BigDecimal(amt == null ?0:Double.parseDouble(amt)));
            accountService.saveOrUpdate(flag,accountInfo);
        }
        AccountDetailInfo accountDetailInfo = new AccountDetailInfo();
        accountDetailInfo.setAmt(new BigDecimal(amt == null ?0:Double.parseDouble(amt)));//充值金额
        accountDetailInfo.setAmt_type(1);
        accountDetailInfo.setUser_id(user_id == null ?0:Long.parseLong(user_id));
        accountDetailInfo.setPay_type(pay_type == null ?0:Integer.parseInt(pay_type));
        accountDetailInfo.setHand_person(hand_person);
        accountDetailInfo.setPay_account(pay_account);
        accountDetailInfo.setCreate_time(new Date());
        accountDetailInfo.setUpdate_time(new Date());
        accountService.detailInsert(accountDetailInfo);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "保存成功");
        ServletUtils.writeToResponse(response,result);
    }
}
