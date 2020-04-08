package com.zhuofengyuan.wechat.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuofengyuan.wechat.shop.entity.Role;
import com.zhuofengyuan.wechat.shop.entity.RoleAuthorization;
import com.zhuofengyuan.wechat.shop.entity.User;
import com.zhuofengyuan.wechat.shop.mapper.RoleMapper;
import com.zhuofengyuan.wechat.shop.service.IRoleAuthorizationService;
import com.zhuofengyuan.wechat.shop.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fengtoos
 * @since 2020-04-07
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    IRoleAuthorizationService roleAuthorizationService;

    @Override
    public boolean saveOrUpdate(Role entity) {

        //先保存角色
        super.saveOrUpdate(entity);
        //删除所有权限项再重新赋值
        this.roleAuthorizationService.remove(new QueryWrapper<RoleAuthorization>().eq("role_id", entity.getId()));
        //重新新增
        var auths = entity.getAuthorizations();
        List<RoleAuthorization> ras = new ArrayList<>();
        auths.forEach(auth -> {
            ras.add(new RoleAuthorization(entity.getId(), auth.getId()));
        });
        return this.roleAuthorizationService.saveBatch(ras);
    }

    @Override
    public Role findById(Serializable id) {
        return this.getBaseMapper().findById(id);
    }
}
