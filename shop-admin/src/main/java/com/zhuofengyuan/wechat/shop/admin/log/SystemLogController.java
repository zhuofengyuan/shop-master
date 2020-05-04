package com.zhuofengyuan.wechat.shop.admin.log;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuofengyuan.wechat.shop.entity.SystemLog;
import com.zhuofengyuan.wechat.shop.resp.RestResponseBo;
import com.zhuofengyuan.wechat.shop.service.ISystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return RestResponseBo.normal(this.systemLogService.save(entity));
    }
}
