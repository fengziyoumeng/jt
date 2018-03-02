package com.rongdu.cashloan.manage.job_new;

import com.rongdu.cashloan.cl.service.AccountService;
import com.rongdu.cashloan.cl.service.SjAccWithCheckService;
import com.rongdu.cashloan.core.common.exception.ServiceException;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import com.rongdu.cashloan.manage.domain.QuartzInfo;
import com.rongdu.cashloan.manage.domain.QuartzLog;
import com.rongdu.cashloan.manage.job.DateUtils;
import com.rongdu.cashloan.manage.service.QuartzInfoService;
import com.rongdu.cashloan.manage.service.QuartzLogService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import tool.util.BeanUtil;
import tool.util.DateUtil;

import java.util.HashMap;
import java.util.Map;

@Component
@Lazy(value = false)
public class PushQuartz implements Job {
    private static final Logger logger = LoggerFactory.getLogger(PushQuartz.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {
        QuartzInfoService quartzInfoService = (QuartzInfoService) BeanUtil.getBean("quartzInfoService");
        QuartzLogService quartzLogService = (QuartzLogService) BeanUtil.getBean("quartzLogService");
        QuartzLog quartzLog = new QuartzLog();
        Map<String,Object> quartzInfoData = new HashMap<>();
        QuartzInfo quartzInfo = quartzInfoService.findByCode("pushQuartz");//查询出表中todayQuartz对应的QuartzInfo对象
        try {
            quartzInfoData.put("id", quartzInfo.getId());
            quartzInfoData.put("succeed", quartzInfo.getSucceed()+1);

            String remark = executeContent();//执行内容
            quartzLog.setQuartzId(quartzInfo.getId());
            quartzLog.setStartTime(DateUtil.getNow());
            quartzLog.setTime(DateUtil.getNow().getTime()-quartzLog.getStartTime().getTime());
            quartzLog.setResult("10");//成功状态是10
            quartzLog.setRemark(remark);
        }catch (Exception e) {
            quartzInfoData.put("fail", quartzInfo.getFail()+1);
            quartzLog.setResult("20");//失败状态是20
            logger.error(e.getMessage(),e);
        }finally{
            logger.info("更新定时任务数据");
            quartzInfoService.update(quartzInfoData);
            logger.info("保存定时任务日志");
            quartzLogService.save(quartzLog);
        }
    }

    private String executeContent() throws ServiceException {
        final ShardedJedisClient redisClient = (ShardedJedisClient)BeanUtil.getBean("redisClient");
        String pushQuartz = "pushQuartz:"+ DateUtils.getNowDate();
        boolean flag = redisClient.setnx("pushQuartz",pushQuartz);
        if(!flag){//已存在返回false
            return "pushQuartz未执行，pushQuartz存在缓存";
        }
        redisClient.expire("pushQuartz",12*60*60);

        SjAccWithCheckService sjAccWithCheckService = (SjAccWithCheckService) BeanUtil.getBean("sjAccWithCheckService");
        try {
            sjAccWithCheckService.saveWithholdCheck();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("*********************任务完成*****************************");
        return "任务完成";
    }
}