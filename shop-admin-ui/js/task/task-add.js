layui.use(['form', 'layer'],
    function() {
        var $ = layui.jquery,
            form = layui.form,
            layer = layui.layer;

        //自定义验证规则
        form.verify({
            name: function(value) {
                if (value.length < 5) {
                    return '昵称至少得5个字符啊';
                }
            }
        });

        //渲染表单
        fengtoos.server({
            url: base_path + 'task/' + fengtoos.getQueryString("id"),
            type: 'get',
            success: function(data) {
                form.val("main-form", data.payload);
            }
        });

        //监听提交
        form.on('submit(add)', function(data) {
            delete data.field["eleTree-node"];

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