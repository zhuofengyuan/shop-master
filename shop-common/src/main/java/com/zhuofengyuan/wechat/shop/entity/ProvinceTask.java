package com.zhuofengyuan.wechat.shop.entity;

import java.math.BigDecimal;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author fengtoos
 * @since 2020-03-06
 */
@Data
@TableName("province_task")
public class ProvinceTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.NONE)
    private Long id;

    private BigDecimal amount;

    private String province;

    private Integer month;

    private Integer year;

    private String dept;
}
