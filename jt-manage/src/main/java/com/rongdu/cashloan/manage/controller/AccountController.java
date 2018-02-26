package com.rongdu.cashloan.manage.controller;

import com.github.pagehelper.Page;
import com.rongdu.cashloan.cl.domain.AccountDetailInfo;
import com.rongdu.cashloan.cl.domain.ClFlowInfo;
import com.rongdu.cashloan.cl.service.AccountService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.JsonUtil;
import com.rongdu.cashloan.core.common.util.RdPage;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Scope("prototype")
@Controller
public class AccountController extends BaseController {
    public static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Resource
    private AccountService accountService;

    /**
     * 账户充值明细
     * @param searchParams
     * @param current
     * @param pageSize
     * @throws Exception
     */
    @RequestMapping(value = "/acc/accountDetail/list.htm")
    public void  getAllAccountDetail(@RequestParam(value="searchParams",required=false) String searchParams,
                             @RequestParam(value = "current") int current,
                             @RequestParam(value = "pageSize") int pageSize ) throws Exception {
        Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
        if(params!=null){
            params.put("userId",String.valueOf(params.get("userId")));
            params.put("amtType",1);
            params.put("sDate",String.valueOf(params.get("sDate")));
            params.put("eDate",String.valueOf(params.get("eDate")));
        }
        Page<AccountDetailInfo> page = accountService.getAllAccountDetailInfo(params,current,pageSize);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put(Constant.RESPONSE_DATA, page);
        result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
        ServletUtils.writeToResponse(response,result);
    }

//    /**
//     * 扣款账单列表
//     * @param searchParams
//     * @param current
//     * @param pageSize
//     * @throws Exception
//     */
//    @RequestMapping(value = "/acc/withholdCheck/list.htm")
//    public void  getAllWithholdCheck(@RequestParam(value="searchParams",required=false) String searchParams,
//                             @RequestParam(value = "current") int current,
//                             @RequestParam(value = "pageSize") int pageSize ) throws Exception {
//        Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
//        if(params!=null){
//            params.put("userId",String.valueOf(params.get("userId")));
//            params.put("sDate",String.valueOf(params.get("sDate")));
//            params.put("eDate",String.valueOf(params.get("eDate")));
//        }
//        Page<ClFlowInfo> page = accountService.getAllProdctList(params,current,pageSize);
//        Map<String,Object> result = new HashMap<String,Object>();
//        result.put(Constant.RESPONSE_DATA, page);
//        result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
//        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
//        result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
//        ServletUtils.writeToResponse(response,result);
//    }
}
