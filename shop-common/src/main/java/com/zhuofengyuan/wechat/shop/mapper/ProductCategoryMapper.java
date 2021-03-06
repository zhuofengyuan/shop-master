package com.zhuofengyuan.wechat.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhuofengyuan.wechat.shop.entity.ProductCategory;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fengtoos
 * @since 2019-06-14
 */
//@Mapper
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {

    /**
     * 查找树结构
     * @return
     */
    List<ProductCategory> selectTree();

    /**
     * 根据父ID获取树
     * @param parent
     * @return
     */
    List<ProductCategory> selectTreeByParent(String parent);
}
