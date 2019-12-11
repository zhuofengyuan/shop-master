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

            let url = base_path + 'admin/product/cate/add';
            if(data.id){
                url = base_path + 'admin/product/cate/update'
            }
            fengtoos.server({
                url: url,
                type: 'post',
                data: JSON.stringify(data),
                contentType: 'application/json',
                success: function(resp){
                    if(resp && resp.success){
                        //发异步，把数据提交给后台
                        layer.msg("操作成功", {icon: 6, time: 800}, function () {

                            // 获得frame索引
                            let index = parent.layer.getFrameIndex(window.name);
                            //关闭当前frame
                            parent.layer.close(index);

                            //刷新父页面的列表
                            window.parent.createTreeTable(null);
                        });

                    } else {
                        layer.msg("操作失败", {icon: 5});
                    }
                }
            });

            return false;
        });
});