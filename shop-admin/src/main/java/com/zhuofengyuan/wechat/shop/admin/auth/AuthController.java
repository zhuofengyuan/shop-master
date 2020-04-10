package com.zhuofengyuan.wechat.shop.admin.auth;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuofengyuan.wechat.shop.entity.Authorization;
import com.zhuofengyuan.wechat.shop.resp.RestResponseBo;
import com.zhuofengyuan.wechat.shop.service.IAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    IAuthorizationService authorizationService;

    @GetMapping("/tree")
    public RestResponseBo findTree(String parent){
        return RestResponseBo.ok(this.authorizationService.findTree(parent), 200);
    }

    @GetMapping("/list")
    public RestResponseBo list(@RequestParam(name = "page", defaultValue = "0") Integer pageNumber,
                               @RequestParam(name = "limit", defaultValue = "10") Integer pageSize){
        Page<Authorization> page = new Page<>(pageNumber, pageSize);
        return RestResponseBo.ok(this.authorizationService.page(page), 0);
    }

    @PostMapping("/add")
    public RestResponseBo add(@RequestBody Authorization entity){
        return RestResponseBo.normal(this.authorizationService.saveOrUpdate(entity));
    }

    @PostMapping("/update")
    public RestResponseBo update(@RequestBody Authorization entity){
        return RestResponseBo.normal(this.authorizationService.updateById(entity));
    }

    @DeleteMapping("/{id}")
    public RestResponseBo delete(@PathVariable String id){
        return RestResponseBo.normal(this.authorizationService.removeById(id));
    }

    @GetMapping("/menu/{id}")
    public RestResponseBo menu(@PathVariable String id){
        return RestResponseBo.ok(this.authorizationService.getMenus(id));
    }
}
