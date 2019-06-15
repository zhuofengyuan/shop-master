package com.zhuofengyuan.wechat.shop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("cart_item")
public class CartItem {

    private String id;
    private String cartId;
    private BigDecimal price;
    private String productId;
}
