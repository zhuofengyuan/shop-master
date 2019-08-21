package com.zhuofengyuan.wechat.shop.service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fengtoos
 * @since 2019-06-14
 */
public interface IWechatService {

    /**
     * 获取微信access_token
     * @return
     */
    String getToken(String id);

    /**
     * 调用远程接口
     * @return
     */
    String executeApi(String id);
}
