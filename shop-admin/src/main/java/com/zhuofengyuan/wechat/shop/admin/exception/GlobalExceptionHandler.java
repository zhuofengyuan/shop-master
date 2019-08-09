package com.zhuofengyuan.wechat.shop.admin.exception;

import com.zhuofengyuan.wechat.shop.exception.FengtoosException;
import com.zhuofengyuan.wechat.shop.resp.RestResponseBo;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OAuth2Exception.class)
    public @ResponseBody  RestResponseBo authException(OAuth2Exception e){
        return RestResponseBo.fail(500, e.getMessage());
    }

    @ExceptionHandler(FengtoosException.class)
    public @ResponseBody  RestResponseBo normalException(FengtoosException e){
        return RestResponseBo.fail(e.getCode(), e.getMessage());
    }

}
