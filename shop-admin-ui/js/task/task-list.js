layui.use(['laydate', 'table'], function () {
    let laydate = layui.laydate, //日期
        table = layui.table, //表格
        form = layui.form,
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
    let userTable = table.render({
        elem: '#admin_table'
        , url: base_path + 'task/list' //数据接口
        , title: '用户表'
        , page: true //开启分页
        // ,toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        // ,totalRow: true //开启合计行
        , parseData: function (res) {
            return {
                data: res.payload.records,
                count: res.payload.total,
                code: res.code
            }
        }
        , height: 'full-200'
        , limit: 10
        , limits: [10, 20, 50, 100]
        , headers: getToken()
        , cols: [[ //表头
            {type: 'checkbox'}
            , {field: 'dept', title: '事业部名称'}
            , {field: 'province', title: '省份'}
            , {field: 'yearmonth', title: '省份', templet: function(d){
                return d.year + '年' + d.month + '月';
            }}
            , {field: 'amount', title: '金额'}
            , {title: '操作', align: 'center', toolbar: '#barUser'}
        ]]
    });
    $("#admin_add").click(function () {
        x_admin_show_back({title: '添加任务', url: './task-add.html', end: function () {
                reloadTable();
            }
        });
    });
    function reloadTable(){
        userTable.reload();
    }
    //监听行工具事件
    table.on('tool(table)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            , layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'del') {
            layer.confirm('真的删除行么', function (index) {
                // obj.del(); //删除对应行（tr）的DOM结构
                //向服务端发送删除指令
                fengtoos.server({
                    url: base_path + 'task/' + data.id,
                    type: 'delete',
                    success: function(resp) {
                        if(resp & resp.success){
                            layer.msg('删除成功', {icon: 1});
                        } else {
                            layer.msg(resp.msg, {icon: 2});
                        }
                        layer.close(index);
                    }
                })
            });
        } else if (layEvent === 'edit') {
            xadmin.open('编辑任务','./task-add.html?id=' + data.id,590,560)
        }
    });
});