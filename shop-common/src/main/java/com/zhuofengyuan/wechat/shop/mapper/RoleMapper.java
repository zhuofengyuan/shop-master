package com.zhuofengyuan.wechat.shop.mapper;

import com.zhuofengyuan.wechat.shop.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fengtoos
 * @since 2020-04-07
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    Role findById(Serializable id);
}
