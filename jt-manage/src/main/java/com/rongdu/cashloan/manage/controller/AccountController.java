package com.rongdu.cashloan.manage.controller;

import com.github.pagehelper.Page;
import com.rongdu.cashloan.cl.domain.AccountDetailInfo;
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
import javax.servlet.http.HttpServletResponse;
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
}
