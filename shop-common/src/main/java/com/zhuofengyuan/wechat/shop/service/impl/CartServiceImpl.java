package com.zhuofengyuan.wechat.shop.service.impl;

import com.zhuofengyuan.wechat.shop.entity.Cart;
import com.zhuofengyuan.wechat.shop.mapper.CartMapper;
import com.zhuofengyuan.wechat.shop.service.ICartService;
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
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {

}
