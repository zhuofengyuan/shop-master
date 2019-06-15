package com.zhuofengyuan.wechat.shop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@TableName("cart")
public class Cart {
    private String id;
    private String userId;
    private BigDecimal price;
    private List<CartItem> items = new ArrayList<CartItem>();
}
