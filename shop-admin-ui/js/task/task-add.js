layui.use(['form', 'layer','code', 'laydate'], function() {
        var $ = layui.jquery,
            laydate = layui.laydate,
            layer = layui.layer;
        form = layui.form;

        layui.code();

        //地区
        $('#province').xcity('广东省','中山市','小榄镇');

        //渲染表单
        if(fengtoos.getQueryString("id")){

            fengtoos.server({
                url: base_path + 'task/' + fengtoos.getQueryString("id"),
                type: 'get',
                success: function(data) {
                    form.val("main-form", data.payload);

                    //年月选择器
                    laydate.render({
                        elem: '#ym'
                        ,type: 'month'
                        ,format: 'yyyy年MM月'
                        ,theme: '#393D49'
                        ,value: data.payload.year + '年' + data.payload.month + '月'
                        ,done: function(value, date, endDate){
                            $('#year').val(date.year)
                            $('#month').val(date.month)
                        }
                    });
                }
            });
        } else {
            //年月选择器
            laydate.render({
                elem: '#ym'
                ,type: 'month'
                ,format: 'yyyy年MM月'
                ,theme: '#393D49'
                ,done: function(value, date, endDate){
                    $('#year').val(date.year)
                    $('#month').val(date.month)
                }
            });
        }

        //自定义验证规则
        form.verify({
            name: function(value) {
                if (value.length < 5) {
                    return '昵称至少得5个字符啊';
                }
            }
        });

        //监听提交
        form.on('submit(add)', function(data) {
            var params = data.field;
            delete params['city'];
            delete params['area'];
            fengtoos.server({
                url: base_path + 'task/add',
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