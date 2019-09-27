package com.zhuofengyuan.wechat.shop.service;

import com.zhuofengyuan.wechat.shop.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fengtoos
 * @since 2019-06-14
 */
public interface IUserService extends IService<User> {

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findByUsername(String username);

    Serializable findWxUser(User user);
}
