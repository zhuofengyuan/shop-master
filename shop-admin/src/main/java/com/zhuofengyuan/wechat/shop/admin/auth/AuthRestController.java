package com.zhuofengyuan.wechat.shop.admin.auth;

import com.zhuofengyuan.wechat.shop.prop.WechatSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wechat")
public class AuthRestController {

    @Autowired
    WechatSettings wechatSettings;

    @GetMapping
    public String conn(String echostr){
//        System.out.println(echostr);
        return echostr;
    }

    @GetMapping("/test")
    public String test() throws Exception {
//        var list = ExcelReader.readTable("F:\\fengtoos\\powerbi\\2020\\template.xlsx");
//        for(var item : list){
//            var parent = this.productCategoryService.getOne(new QueryWrapper<ProductCategory>().eq("name", item.getParentName())
//            .like("path", ",1245518310520758273,").eq("level", 2));
//            var parent = this.productCategoryService.getOne(new QueryWrapper<ProductCategory>().eq("name", item.getParent())
//                    .eq("level", 2).like("path", ",1245518310520758273,"));
//            var parents = this.productCategoryService.getOne(new QueryWrapper<ProductCategory>().eq("parent", parent.getId())
//                    .eq("name", item.getParentName())
//                    .eq("level", 3)
//                    .like("path", ",1245518310520758273,"));
//            item.setParent(parent.getId());
//            item.setStatus(1);
//            item.setIsLeaf(true);
//            this.productCategoryService.save(item);
//        }
        return "hello world";
    }
}
