package com.zhuofengyuan.wechat.shop.service.impl;

import com.zhuofengyuan.wechat.shop.entity.User;
import com.zhuofengyuan.wechat.shop.mapper.UserMapper;
import com.zhuofengyuan.wechat.shop.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fengtoos
 * @since 2019-06-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
