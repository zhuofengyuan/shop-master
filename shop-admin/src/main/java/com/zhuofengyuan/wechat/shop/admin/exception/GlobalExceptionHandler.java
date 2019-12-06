package com.zhuofengyuan.wechat.shop.admin.exception;

import com.zhuofengyuan.wechat.shop.exception.FengtoosException;
import com.zhuofengyuan.wechat.shop.resp.RestResponseBo;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FengtoosException.class)
    public @ResponseBody  RestResponseBo customException(FengtoosException e){
        return RestResponseBo.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public @ResponseBody  RestResponseBo normalException(HttpMediaTypeNotSupportedException e, HttpServletResponse resp) throws IOException {
        return RestResponseBo.fail(500, e.getMessage());
    }
}
