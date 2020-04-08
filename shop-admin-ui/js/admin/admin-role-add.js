layui.use(['form', 'layer', 'eleTree'],
    function() {
        var $ = layui.jquery,
            form = layui.form,
            eleTree = layui.eleTree,
            layer = layui.layer;

        var el;

        //自定义验证规则
        form.verify({
            name: function(value) {
                if (value.length < 5) {
                    return '昵称至少得5个字符啊';
                }
            }
        });

        fengtoos.server({
            url: base_path + 'auth/tree',
            type: 'get',
            data: {parent: "all"},
            success: function(resp){
                if(resp && resp.success){
                    setTimeout(function() {
                        el = eleTree.render({
                            elem: '#ele',
                            highlightCurrent: true,
                            emptText: '暂无数据',
                            showCheckbox: true,
                            request: {
                                name: "name"
                            },
                            data: resp.payload
                        });
                        el.expandAll();

                        var id = fengtoos.getQueryString("id");
                        if(id){
                            fengtoos.server({
                                url: base_path + 'role/' + id,
                                type: 'get',
                                success: function(data) {
                                    form.val("main-form", data.payload);
                                    let auths = data.payload.authorizations;
                                    let ids = [];
                                    for(let i in auths){
                                        ids.push(auths[i].id)
                                    }
                                    el.expandAll();
                                    el.setChecked(ids, true);
                                }
                            });
                        }
                    },100);
                } else {

                }
            }
        })

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