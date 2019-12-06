package com.zhuofengyuan.wechat.shop.admin.product;

import com.zhuofengyuan.wechat.shop.entity.ProductCategory;
import com.zhuofengyuan.wechat.shop.resp.RestResponseBo;
import com.zhuofengyuan.wechat.shop.service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/admin/product/cate")
public class ProductCategoryController {

    @Autowired
    IProductCategoryService productCategoryService;

    @GetMapping("/tree")
    public RestResponseBo findTree(){
        return RestResponseBo.ok(this.productCategoryService.findTree(), 200);
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

    @DeleteMapping("/{id}")
    public RestResponseBo delete(@PathVariable String id){
        this.productCategoryService.removeById(id);
        return RestResponseBo.ok();
    }
}
