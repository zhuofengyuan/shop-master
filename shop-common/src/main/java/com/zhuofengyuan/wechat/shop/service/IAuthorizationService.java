package com.zhuofengyuan.wechat.shop.service;

import com.zhuofengyuan.wechat.shop.entity.Authorization;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fengtoos
 * @since 2019-06-29
 */
public interface IAuthorizationService extends IService<Authorization> {

    List<Authorization> selectByUserId(Long id);
}
