package com.rongdu.cashloan.manage.controller;

import com.rongdu.cashloan.cl.domain.AdInfo;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.web.controller.BaseController;
import com.rongdu.cashloan.manage.domain.QueryCondition;
import com.rongdu.cashloan.manage.service.QueryConditionService;
import com.rongdu.cashloan.system.domain.SysUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
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
public class QueryConditionController extends BaseController {

   @Resource
   private QueryConditionService queryConditionService;

   @RequestMapping("/act/saveorupdate/querycondition.htm")
   public void saveOrUpdateCondition(@RequestParam(value = "form",required=false) String data){
      Map<String,Object> result = new HashMap<String,Object>();
      try {
         SysUser loginUser = this.getLoginUser(request);
         queryConditionService.saveOrUpdate(data,loginUser.getId());
         result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
         result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
      }catch (Exception e){
         result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);

         result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请稍后重试");
      }
      ServletUtils.writeToResponse(response,result);
   }

   @RequestMapping("/act/get/querycondition.htm")
   public void getCondition(){
      Map<String,Object> result = new HashMap<String,Object>();
      try {
         SysUser loginUser = this.getLoginUser(request);
         System.out.println(loginUser);
         QueryCondition queryCondition = queryConditionService.getQueryCondition(loginUser.getId());

         result.put(Constant.RESPONSE_DATA, queryCondition);
         result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
         result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
      }catch (Exception e){
         result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
         result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请稍后重试");
      }
      ServletUtils.writeToResponse(response,result);
   }
}
