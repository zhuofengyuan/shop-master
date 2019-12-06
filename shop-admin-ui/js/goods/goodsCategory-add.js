layui.use(['form', 'layer'],
    function () {
        const form = layui.form,
            layer = layui.layer,
            $ = layui.jquery;

        //初始化表单
        form.val("cateForm", $.extend({}, window.parent.currentOpen, {}));
        if(form.val("cateForm").parentName){
            $('#parentName').html(form.val("cateForm").parentName);
        }

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
        form.on('submit(add)', function (params) {
            let data = params.field
            if(data.status){
                data.status = 1;
            } else {
                data.status = 0;
            }
            console.log(data)
            fengtoos.server({
                url: base_path + 'admin/product/cate/add',
                type: 'post',
                data: JSON.stringify(data),
                contentType: 'application/json',
                success: function(resp){
                    if(resp && resp.success){
                        //发异步，把数据提交给后台
                        layer.alert("增加成功", {icon: 6}, function () {
                            //刷新父页面的列表
                            window.parent.createTreeTable(null);
                            // 获得frame索引
                            let index = parent.layer.getFrameIndex(window.name);
                            //关闭当前frame
                            parent.layer.close(index);
                        });
                    } else {
                        layer.alert("操作失败", {icon: 5});
                    }
                }
            });

            return false;
        });
});