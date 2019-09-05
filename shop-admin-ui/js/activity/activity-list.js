layui.use(['laydate', 'form', 'table'], function() {
    var laydate = layui.laydate, //日期
        table = layui.table, //表格
        $ = layui.jquery; //jquery
    window.$ = $;

    //执行一个laydate实例
    laydate.render({
        elem: '#start' //指定元素
    });

    //执行一个laydate实例
    laydate.render({
        elem: '#end' //指定元素
    });

    //执行一个 table 实例
    var activityTable = table.render({
        elem: '#activity_table'
        , url: base_path + 'admin/activity/list' //数据接口
        , title: '用户表'
        , page: true //开启分页
        // ,toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        // ,totalRow: true //开启合计行
        , parseData: function (res) {
            return {
                data: res.payload.list,
                count: res.payload.total,
                code: res.code
            }
        }
        , limit: 10
        , limits: [10, 15, 20, 25]
        , headers: getToken()
        , cols: [[ //表头
            {type: 'checkbox'}
            , {field: 'name', title: '名称'}
            , {field: 'screenName', title: '昵称'}
            , {field: 'email', title: '电子邮件'}
            , {field: 'phone', title: '手机号码'}
            , {field: 'created', title: '创建时间'}
            , {title: '操作', align: 'center', toolbar: '#barActivity'}
        ]]
    });

    $("#activity_add").click(function () {
        xadmin.open_back({
            title: '添加用户',
            url: './activity-add.html',
            full: true,
            back: function () {
                reloadTable();
            }
        });
    });

    function reloadTable(){
        activityTable.reload();
    }

    //监听行工具事件
    table.on('tool(table)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            , layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'detail') {
            layer.msg('查看操作');
        } else if (layEvent === 'del') {
            layer.confirm('真的删除行么', function (index) {
                obj.del(); //删除对应行（tr）的DOM结构
                layer.close(index);
                //向服务端发送删除指令
            });
        } else if (layEvent === 'edit') {
            layer.msg('编辑操作');
        }
    });
});

/*用户-停用*/
function member_stop(obj, id) {
    layer.confirm('确认要停用吗？',
        function(index) {

            if ($(obj).attr('title') == '启用') {

                //发异步把用户状态进行更改
                $(obj).attr('title', '停用');
                $(obj).find('i').html('&#xe62f;');

                $(obj).parents("tr").find(".td-status").find('span').addClass('layui-btn-disabled').html('已停用');
                layer.msg('已停用!', {
                    icon: 5,
                    time: 1000
                });

            } else {
                $(obj).attr('title', '启用');
                $(obj).find('i').html('&#xe601;');

                $(obj).parents("tr").find(".td-status").find('span').removeClass('layui-btn-disabled').html('已启用');
                layer.msg('已启用!', {
                    icon: 5,
                    time: 1000
                });
            }

        });
}

/*用户-删除*/
function member_del(obj, id) {
    layer.confirm('确认要删除吗？',
        function(index) {
            //发异步删除数据
            $(obj).parents("tr").remove();
            layer.msg('已删除!', {
                icon: 1,
                time: 1000
            });
        });
}

function delAll(argument) {

    var data = tableCheck.getData();

    layer.confirm('确认要删除吗？' + data,
        function(index) {
            //捉到所有被选中的，发异步进行删除
            layer.msg('删除成功', {
                icon: 1
            });
            $(".layui-form-checked").not('.header').parents('tr').remove();
        });
}