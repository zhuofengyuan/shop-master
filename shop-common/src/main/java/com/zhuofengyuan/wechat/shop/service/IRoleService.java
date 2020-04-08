package com.zhuofengyuan.wechat.shop.service;

import com.zhuofengyuan.wechat.shop.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fengtoos
 * @since 2020-04-07
 */
public interface IRoleService extends IService<Role> {

    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    Role findById(Serializable id);
}
