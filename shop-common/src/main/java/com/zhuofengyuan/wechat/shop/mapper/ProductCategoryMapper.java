package com.zhuofengyuan.wechat.shop.mapper;

import com.zhuofengyuan.wechat.shop.entity.ProductCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fengtoos
 * @since 2019-06-14
 */
@Mapper
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {

    /**
     * 查找树结构
     * @return
     */
    List<ProductCategory> selectTree();
}
