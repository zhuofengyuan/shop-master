package com.zhuofengyuan.wechat.shop.service;

import com.zhuofengyuan.wechat.shop.entity.Authorization;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuofengyuan.wechat.shop.entity.ProductCategory;

import java.util.List;

/**
 * <p>
 *  权限服务类
 * </p>
 *
 * @author fengtoos
 * @since 2019-06-29
 */
public interface IAuthorizationService extends IService<Authorization> {

    /**
     * 根据用户ID获取权限项
     * @param id
     * @return
     */
    List<Authorization> selectByUserId(Long id);

    /**
     * 根据父ID获取下一级树结构
     * @param pid
     * @return
     */
    List<Authorization> findTree(String pid);
}
