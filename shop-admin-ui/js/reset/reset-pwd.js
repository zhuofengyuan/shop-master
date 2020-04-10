layui.use(['form', 'layer', 'upload'],
    function() {
        var $ = layui.jquery,
            form = layui.form,
            layer = layui.layer;

        //自定义验证规则
        form.verify({
            password: [/(.+){6,12}$/, '密码必须6到12位'],
            orgpass: [/(.+){6,12}$/, '密码必须6到12位'],
            repass: function(value) {
                if ($('#L_pass').val() != $('#L_repass').val()) {
                    return '两次密码不一致';
                }
            }
        });

        //监听提交
        form.on('submit(add)', function(data) {
            delete data.field.file;
            fengtoos.server({
                url: base_path + 'user/reset/' + getUser().principal.id,
                data: data.field,
                success: function(resp){
                    if(resp && resp.success){
                        //发异步，把数据提交给php
                        layer.alert("重置成功", {
                            icon: 6
                        }, function() {
                            //关闭当前frame
                            xadmin.close();
                        });
                    } else {
                        layer.msg(resp.msg, {icon: 2});
                    }
                }
            })
            return false;
        });

    });