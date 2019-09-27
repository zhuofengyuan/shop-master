layui.use(['form', 'layer'],
    function () {
        var form = layui.form,
            layer = layui.layer,
            $ = layui.jquery;

        //自定义验证规则
        form.verify({
            name: function (value) {
                if (value.length < 2) {
                    return '昵称至少得2个字符啊';
                }
            },
            /*pass: [/(.+){6,12}$/, '密码必须6到12位'],
            repass: function (value) {
                if ($('#L_pass').val() != $('#L_repass').val()) {
                    return '两次密码不一致';
                }
            }*/
            sortOrder: [/^[0-9]+$/, '序号必须为正整数']
        });

        //监听提交
        form.on('submit(add)', function (data) {
            console.log(data.field);

            fengtoos.server({
                url: base_path + 'admin/product/cate/add',
                type: 'post',
                data: data.field,
                success: function(resp){
                    if(resp && resp.success){
                        //发异步，把数据提交给后台
                        layer.alert("增加成功", {icon: 6}, function () {
                            // 获得frame索引
                            var index = parent.layer.getFrameIndex(window.name);
                            //关闭当前frame
                            parent.layer.close(index);
                        });
                    }
                }
            });

            return false;
        });

        console.log(window.parent.currentOpen)
        //初始化表单
        form.val("cateForm", window.parent.currentOpen);
});