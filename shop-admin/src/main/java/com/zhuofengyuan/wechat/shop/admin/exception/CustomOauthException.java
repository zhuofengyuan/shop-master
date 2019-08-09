package com.zhuofengyuan.wechat.shop.admin.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@Data
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {

    private Integer code;

    public CustomOauthException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public CustomOauthException(String msg) {
        super(msg);
    }
}
