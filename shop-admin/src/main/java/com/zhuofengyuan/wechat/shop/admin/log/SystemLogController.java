package com.zhuofengyuan.wechat.shop.admin.log;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuofengyuan.wechat.shop.admin.auth.security.FengtoosSecurityUser;
import com.zhuofengyuan.wechat.shop.entity.SystemLog;
import com.zhuofengyuan.wechat.shop.resp.RestResponseBo;
import com.zhuofengyuan.wechat.shop.service.ISystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fengtoos
 * @since 2020-05-04
 */
@RestController
@RequestMapping("/log")
public class SystemLogController {

    @Autowired
    ISystemLogService systemLogService;

    @GetMapping("/list")
    public RestResponseBo list(@RequestParam(name = "page", defaultValue = "0") Integer pageNumber,
                               @RequestParam(name = "limit", defaultValue = "10") Integer pageSize){
        Page<SystemLog> page = new Page<>(pageNumber, pageSize);
        return RestResponseBo.ok(this.systemLogService.page(page), 0);
    }

    @PostMapping("/add")
    public RestResponseBo add(@RequestBody SystemLog entity){
        var currentUser = (FengtoosSecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        entity.setUser(currentUser==null?null:currentUser.getId());
        entity.setUserName(currentUser==null?null:currentUser.getNickName());
        entity.setOperationDate(LocalDateTime.now());
        return RestResponseBo.normal(this.systemLogService.save(entity));
    }
}
