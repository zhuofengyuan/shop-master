package com.zhuofengyuan.wechat.shop.admin.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuofengyuan.wechat.shop.entity.ProductCategory;
import com.zhuofengyuan.wechat.shop.resp.RestResponseBo;
import com.zhuofengyuan.wechat.shop.service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 产品分类接口
 * @author fengtoos.yuan
 * @date 2019年12月11日17:28:36
 */
@RestController
@RequestMapping("/admin/product/cate")
public class ProductCategoryController {

    @Autowired
    IProductCategoryService productCategoryService;

    @GetMapping("/tree")
    public RestResponseBo findTree(String parent){
        return RestResponseBo.ok(this.productCategoryService.findTree(parent), 200);
    }

    @PostMapping("/add")
    public RestResponseBo add(@RequestBody ProductCategory entity){
        this.productCategoryService.save(entity);
        return RestResponseBo.ok();
    }

    @PostMapping("/update")
    public RestResponseBo update(@RequestBody ProductCategory entity){
        this.productCategoryService.updateById(entity);
        return RestResponseBo.ok();
    }

    /**
     * 根据ID删除该分类以及子分类
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public RestResponseBo delete(@PathVariable String id){
        var entity = this.productCategoryService.getById(id);
        this.productCategoryService.remove(new QueryWrapper<ProductCategory>().likeRight("path", entity.getPath()));
        return RestResponseBo.ok();
    }
}
