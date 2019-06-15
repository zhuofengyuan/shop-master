package com.zhuofengyuan.wechat.shopweb.product;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuofengyuan.wechat.shop.entity.Product;
import com.zhuofengyuan.wechat.shop.resp.RestResponseBo;
import com.zhuofengyuan.wechat.shop.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wechat")
public class ProductController {

    @Autowired
    IProductService productService;

    @GetMapping("/product")
    public RestResponseBo list(Integer pageNumber, Integer pageSize){
        Page<Product> page = new Page<>(pageNumber, pageSize);
        return RestResponseBo.ok(this.productService.page(page));
    }
}
