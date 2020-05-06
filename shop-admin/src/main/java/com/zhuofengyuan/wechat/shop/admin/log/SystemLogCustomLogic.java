package com.zhuofengyuan.wechat.shop.admin.log;

import com.zhuofengyuan.wechat.shop.admin.auth.security.FengtoosSecurityUser;
import com.zhuofengyuan.wechat.shop.aspect.ISystemLogCustomLogic;
import com.zhuofengyuan.wechat.shop.entity.SystemLog;
import com.zhuofengyuan.wechat.shop.service.ISystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Order(1)
public class SystemLogCustomLogic implements ISystemLogCustomLogic {

    @Autowired
    ISystemLogService systemService;

    @Override
    public void execute(Map<String, Object> params) {
        var currentUser = (FengtoosSecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SystemLog entity = new SystemLog();
        entity.setMethod(params.get("method").toString());
        entity.setOperationDate(LocalDateTime.now());
        entity.setType(2);
        entity.setUser(currentUser==null?null:currentUser.getId());
        entity.setUserName(currentUser==null?null:currentUser.getNickName());
        entity.setParams(String.format("%s|%s", params.get("paramName"), params.get("paramVal")));
        entity.setDescription(params.get("desc").toString());
        this.systemService.save(entity);
    }
}
