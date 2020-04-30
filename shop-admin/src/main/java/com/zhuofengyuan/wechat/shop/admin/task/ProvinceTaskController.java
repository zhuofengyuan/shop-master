package com.zhuofengyuan.wechat.shop.admin.task;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuofengyuan.wechat.shop.entity.ProvinceTask;
import com.zhuofengyuan.wechat.shop.entity.User;
import com.zhuofengyuan.wechat.shop.resp.RestResponseBo;
import com.zhuofengyuan.wechat.shop.service.IProvinceTaskService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fengtoos
 * @since 2020-03-06
 */
@RestController
@RequestMapping("/task")
public class ProvinceTaskController {

    @Autowired
    IProvinceTaskService provinceTaskService;

    @GetMapping("/list")
    public RestResponseBo list(@RequestParam(name = "page", defaultValue = "0") Integer pageNumber,
                               @RequestParam(name = "limit", defaultValue = "10") Integer pageSize){
        Page<ProvinceTask> page = new Page<>(pageNumber, pageSize);
        return RestResponseBo.ok(this.provinceTaskService.page(page), 0);
    }

    @PostMapping("/add")
    public RestResponseBo add(@RequestBody ProvinceTask entity){
        return RestResponseBo.normal(this.provinceTaskService.save(entity));
    }

    @PostMapping("/update")
    public RestResponseBo update(@RequestBody ProvinceTask entity){
        return RestResponseBo.normal(this.provinceTaskService.updateById(entity));
    }

    @DeleteMapping("/{id}")
    public RestResponseBo delete(@PathVariable String id){
        return RestResponseBo.normal(this.provinceTaskService.removeById(id));
    }
}
