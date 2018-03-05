package com.rongdu.cashloan.manage.controller;

import com.github.pagehelper.Page;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.JsonUtil;
import com.rongdu.cashloan.core.common.util.RdPage;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.web.controller.BaseController;
import com.rongdu.cashloan.cl.domain.MerchantBorrower;
import com.rongdu.cashloan.cl.service.MerchantBorrowerService;
import com.rongdu.cashloan.system.domain.SysUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
* 用户消息Controller
*
* @author Yang
* @version 1.0.0
* @date 2018-02-26 11:20:01
* Copyright 杭州民华金融信息服务有限公司  cashloan All Rights Reserved
* 官方网站：www.yongqianbei.com
* 未经授权不得进行修改、复制、出售及商业使用
*/
@Controller
public class MerchantBorrowerController extends BaseController {

   @Resource
   private MerchantBorrowerService merchantBorrowerService;

   /**
    * 获取当前商户所拥有的数据
    * @param searchParams
    * @param current
    * @param pageSize
    */
   @RequestMapping("/act/get/userdata/list.htm")
   public void getCondition(@RequestParam(value="searchParams",required=false) String searchParams,
                            @RequestParam(value = "current") int current,
                            @RequestParam(value = "pageSize") int pageSize){
      Map<String,Object> result = new HashMap<String,Object>();
      try {
         Map params = JsonUtil.parse(searchParams,Map.class);
         SysUser loginUser = this.getLoginUser(request);
         if(params == null){
            params = new HashMap();
         }

         params.put("merchantId",loginUser.getId());
         params.put("current",current);
         params.put("pageSize",pageSize);
         Page<MerchantBorrower> dataList = merchantBorrowerService.getUserDataList(params);

         result.put(Constant.RESPONSE_DATA, dataList);
         result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(dataList));
         result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
         result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
      }catch (Exception e){
         result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
         result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请稍后重试");
      }
      ServletUtils.writeToResponse(response,result);
   }
   @RequestMapping("/act/set/userdata/status.htm")
   public void setStatus(@RequestParam(value="audit") String audit,
                         @RequestParam(value="borrowerId") String borrowerId){
      Map<String,Object> result = new HashMap<String,Object>();
      try {
         SysUser loginUser = this.getLoginUser(request);
         merchantBorrowerService.setAuditStatus(loginUser.getId(),borrowerId,audit);
         result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
         result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
      }catch (Exception e){
         result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
         result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请稍后重试");
      }
      ServletUtils.writeToResponse(response,result);
   }
}
