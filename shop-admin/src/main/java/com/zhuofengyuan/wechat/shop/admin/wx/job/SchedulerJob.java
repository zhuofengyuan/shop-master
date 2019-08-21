package com.zhuofengyuan.wechat.shop.admin.wx.job;

import com.zhuofengyuan.wechat.shop.service.IWechatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
public class SchedulerJob {

    @Autowired
    IWechatService wxService;

    @Scheduled(cron = "0 */1 * * * ?")
    private void refreshWxToken(){
        log.debug("--------------------------定时任务开始--------------------------");
        wxService.executeApi("fengtoos");
        log.debug("--------------------------定时任务结束--------------------------");
    }
}
