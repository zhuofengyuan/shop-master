package com.zhuofengyuan.wechat.shop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@TableName("order")
public class Order {

    private String id;
    private List<OrderItem> items = new ArrayList<OrderItem>();
    private BigDecimal price;
}
