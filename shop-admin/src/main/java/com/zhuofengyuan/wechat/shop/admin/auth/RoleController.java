package com.zhuofengyuan.wechat.shop.admin.auth;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuofengyuan.wechat.shop.entity.Role;
import com.zhuofengyuan.wechat.shop.entity.UserRole;
import com.zhuofengyuan.wechat.shop.resp.RestResponseBo;
import com.zhuofengyuan.wechat.shop.service.IRoleService;
import com.zhuofengyuan.wechat.shop.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  角色控制器
 * </p>
 *
 * @author fengtoos
 * @since 2020-04-07
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    IRoleService roleService;
    @Autowired
    IUserRoleService userRoleService;

    @GetMapping("/{id}")
    public RestResponseBo findOne(@PathVariable String id){
        return RestResponseBo.ok(this.roleService.findById(id));
    }

    @GetMapping("/list")
    public RestResponseBo list(@RequestParam(name = "page", defaultValue = "0") Integer pageNumber,
                               @RequestParam(name = "limit", defaultValue = "10") Integer pageSize){
        Page<Role> page = new Page<>(pageNumber, pageSize);
        return RestResponseBo.ok(this.roleService.page(page), 0);
    }

    @PostMapping("/add")
    public RestResponseBo add(@RequestBody Role entity){
        return RestResponseBo.normal(this.roleService.saveOrUpdate(entity));
    }

    @PostMapping("/update")
    public RestResponseBo update(@RequestBody Role entity){
        return RestResponseBo.normal(this.roleService.updateById(entity));
    }

    @DeleteMapping("/{id}")
    public RestResponseBo delete(@PathVariable String id){
        return RestResponseBo.normal(this.roleService.removeById(id));
    }

    @PostMapping("/add/user")
    public RestResponseBo addUser(@RequestBody List<UserRole> data){
        return RestResponseBo.normal(this.userRoleService.saveBatch(data));
    }
}
