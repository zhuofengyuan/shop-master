package com.zhuofengyuan.wechat.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.zhuofengyuan.wechat.shop.entity.ProductCategory;
import com.zhuofengyuan.wechat.shop.exception.FengtoosException;
import com.zhuofengyuan.wechat.shop.mapper.ProductCategoryMapper;
import com.zhuofengyuan.wechat.shop.service.IProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fengtoos
 * @since 2019-06-14
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements IProductCategoryService {

    @Autowired
    ProductCategoryMapper productCategoryMapper;

    @Override
    public List<ProductCategory> findTree(String pid) {
        if (StringUtils.isEmpty(pid)){
            return this.productCategoryMapper.selectTree();
        } else {
            return this.productCategoryMapper.selectTreeByParent(pid);
        }
    }

    @Override
    public boolean save(ProductCategory entity) {
        String parentId = entity.getParent();
        if(StringUtils.isEmpty(parentId)){
            throw new FengtoosException(500, "entity parent is null");
        }

        super.save(entity);
        var parent = this.getById(parentId);
        entity.setPath(String.format("%s%s.", parent.getPath(), entity.getId()));
        return this.updateById(entity);
    }
}
