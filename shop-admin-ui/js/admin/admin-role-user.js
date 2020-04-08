layui.use(['form', 'layer', 'eleTree', 'transfer'],
    function() {
        var $ = layui.jquery,
            form = layui.form,
            transfer = layui.transfer,
            layer = layui.layer;

        fengtoos.server({
            url: base_path + "user/role/" + fengtoos.getQueryString("id"),
            type: 'get',
            success : function(resp) {
                if(resp && resp.success){
                    console.log(resp.payload)
                } else {
                    layer.msg(resp.msg, {icon: 2});
                }
            }
        })

        //渲染
        transfer.render({
            elem: '#transfer'  //绑定元素
            ,data: [
                {"value": "1", "title": "<img class='layui-nav-img' src='https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIyQtWHp6QmEX5YHtPv8sFCubCnzyC7u6VN7JT2naMCIulXqsbhlG3ZicjTDSzabIuiaVEsNb5Iibesg/132' />李白", "disabled": "", "checked": ""}
                ,{"value": "2", "title": "杜甫", "disabled": "", "checked": ""}
                ,{"value": "3", "title": "贤心", "disabled": "", "checked": ""}
            ]
            ,value: ["3"]
            ,title: ["可分配用户", "已分配用户"]
            ,showSearch: true
            ,text: {
                none: '无数据' //没有数据时的文案
                ,searchNone: '无匹配数据' //搜索无匹配数据时的文案
            }
            ,id: 'demo1' //定义索引
        });


        //监听提交
        form.on('submit(add)', function(data) {
            delete data.field["eleTree-node"];
            //权限项
            let auths = el.getChecked(true, false);
            for(let i in auths){
                delete auths[i]["elem"];
                delete auths[i]["othis"];
            }

            data.field.authorizations = auths;
            fengtoos.server({
                url: base_path + 'role/add',
                data: JSON.stringify(data.field),
                contentType: 'application/json',
                success: function(resp){
                    if(resp && resp.success){
                        //发异步，把数据提交给php
                        layer.alert("保存成功", {
                            icon: 6
                        }, function() {
                            //关闭当前frame
                            xadmin.close();
                            // 可以对父窗口进行刷新
                            xadmin.father_reload();
                        });
                    } else {
                        layer.msg(resp.msg, {icon: 2});
                    }
                }
            })
            return false;
        });
    });