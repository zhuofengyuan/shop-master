package com.zhuofengyuan.wechat.shop.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author fengtoos
 * @since 2020-05-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SystemLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private String method;

    private String params;

    private Integer type;

    private String user;

    private LocalDateTime operationDate;

    private String userName;

    private String description;


}
