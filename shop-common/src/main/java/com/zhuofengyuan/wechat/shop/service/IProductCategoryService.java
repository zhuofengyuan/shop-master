package com.zhuofengyuan.wechat.shop.service;

import com.zhuofengyuan.wechat.shop.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fengtoos
 * @since 2019-06-14
 */
public interface IProductCategoryService extends IService<ProductCategory> {

    List<ProductCategory> findTree(String pid);
}
