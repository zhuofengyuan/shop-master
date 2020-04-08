package com.zhuofengyuan.wechat.shop.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author fengtoos
 * @since 2020-04-08
 */
@Data
@TableName("role_authorization")
@AllArgsConstructor
public class RoleAuthorization implements Serializable {

    private static final long serialVersionUID = 1L;

    private String roleId;

    private String authorizationId;


}
