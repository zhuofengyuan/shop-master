layui.use(['form', 'layer', 'eleTree', 'transfer'],
    function() {
        var $ = layui.jquery,
            form = layui.form,
            transfer = layui.transfer,
            layer = layui.layer;

        var getValueList = function(){
            let org = [];
            fengtoos.server({
                url: base_path + "user/role/" + fengtoos.getQueryString("id"),
                type: 'get',
                async : false,
                success : function(resp) {
                    if(resp && resp.success){
                        for(let i in resp.payload){
                            org.push(resp.payload[i].id)
                        }
                    } else {
                        layer.msg(resp.msg, {icon: 2});
                    }
                }
            })
            return org;
        }

        var getUserList = function(){
            let val = null;
            fengtoos.server({
                url:base_path + 'user/list',
                data: {page: 0, limit: 10000},
                type: 'get',
                success: function(resp) {
                    if (resp && resp.success) {
                        val = resp.payload.records
                    } else {
                        layer.msg(resp.msg, {icon : 2})
                    }
                }
            })
            return val;
        }

        //渲染
        transfer.render({
            elem: '#transfer'  //绑定元素
            ,data: getUserList()
            ,value: getValueList()
            ,title: ["可分配用户", "已分配用户"]
            ,showSearch: true
            ,text: {
                none: '无数据' //没有数据时的文案
                ,searchNone: '无匹配数据' //搜索无匹配数据时的文案
            }
            ,id: 'roleuser' //定义索引
            ,parseData: function(res){
                return {
                    "value": res.id //数据值
                    ,"title": "<img class='layui-nav-img' src='" + image_path + res.logo + "'>" + res.name //数据标题
                    ,"disabled": res.disabled  //是否禁用
                    ,"checked": res.checked //是否选中
                }
            }
        });


        //监听提交
        form.on('submit(add)', function(data) {
            //权限项
            let users = transfer.getData('roleuser');
            let ids = [];
            for(var i in users){
                ids.push({userId: users[i].value, roleId: fengtoos.getQueryString("id")});
            }

            fengtoos.server({
                url: base_path + 'role/add/user/' + fengtoos.getQueryString("id"),
                data: JSON.stringify(ids),
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