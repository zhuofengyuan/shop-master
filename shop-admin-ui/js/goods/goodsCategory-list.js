layui.use(['form'], function () {
    var form = layui.form,
        $ = layui.$;
    window.$ = $;


});

var currentOpen = {
    id: null,
    parentId: 0,
    parentName: null,
    name: null,
    sortOrder: null,
    status: null
}

/*用户-删除*/
function member_del(obj, id) {
    layer.confirm('确认要删除吗？', function (index) {
        //发异步删除数据
        $(obj).parents("tr").remove();
        layer.msg('已删除!', {icon: 1, time: 1000});
    });
}
function delAll(argument) {
    var data = tableCheck.getData();
    layer.confirm('确认要删除吗？' + data, function (index) {
        //捉到所有被选中的，发异步进行删除
        layer.msg('删除成功', {icon: 1});
        $(".layui-form-checked").not('.header').parents('tr').remove();
    });
}
//创建树形表格
var $level = 0;
var createTreeTable = function (data) {
    var html = '';
    fengtoos.server({
        url: base_path + 'admin/product/cate/tree',
        type: 'get',
        data: data,
        success: function(resp){
            if(resp && resp.success){
                var list = resp.payload;
                for(var i in list){
                    $level = 0;
                    list[i].level = $level;
                    html += makeLine(list[i]) + makeTree(list[i]);
                }
            }
        }
    })
    html = html == '' ? '<tr><td colspan="6" style="text-align: center">暂无数据</td></tr>' : html;
    $('.x-cate').html(html);
}
var makeTree = function (data) {
    var html = '';
    if(data.children != null){
        $level++;
        var children = data.children;
        for(var i in children){
            children[i].level = $level;
            html += makeLine(children[i]) + makeTree(children[i])
        }
    }
    return html
}

/**
 * 添加下一级
 */
var openEdit = function (id, parentId, parentName, name, sortOrder, status) {
    currentOpen = {
        id: id,
        parentId: parentId,
        parentName: parentName,
        name: name,
        sortOrder: sortOrder,
        status: status==1?true:false
    }
    xadmin.open("添加分类","goodsCategory-add.html",600,350);
}

var makeLine = function(params) {
    var empty = '';
    var _params = {
        id: null,
        parentId: 0,
        name: null,
        sortOrder: null,
        status: null,
        level: 0,
        isLast: false
    };
    params = $.extend({}, _params, params);
    //开启名称空格
    for(var i = 0; i < params.level; i++){
        empty += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
    }
    //是否为最后一个元素
    if(params.children == null){
        params.isLast = true
    }
    var line = '<tr cate-id="' + params.id + '" fid="' + params.parentId + '">' +
        '<td>' +
        '   <div class="layui-unselect layui-form-checkbox" lay-skin="primary" data-id="2"><i class="layui-icon">&#xe605;</i></div>' +
        '</td>' +
        '<td>' + params.sortOrder + '</td>' +
        '<td>' + empty +
        (params.isLast?'':'<i class="layui-icon x-show" status="true">&#xe623;</i>') +
        params.name + '</td>' +
        '<td><input type="checkbox" name="status" value="' + params.status + '" lay-text="开启|停用" ' +
        (params.status?'checked':'') +
        ' lay-skin="switch"></td>' +
        '<td class="td-manage">' +
        '   <button class="layui-btn layui-btn layui-btn-xs" onclick="x_admin_show(\'编辑\',\'admin-edit.html\')">' +
        '       <i class="layui-icon">&#xe642;</i>编辑</button>' +
        '   <button class="layui-btn layui-btn-warm layui-btn-xs" onclick="openEdit(null,\'' + params.id + '\',\'' + params.name + '\')">' +
        '       <i class="layui-icon">&#xe642;</i>添加子栏目</button>' +
        '   <button class="layui-btn-danger layui-btn layui-btn-xs" onclick="member_del(this,\'要删除的id\')"' +
        ' href="javascript:;"><i class="layui-icon">&#xe640;</i>删除</button>' +
        '</td>' +
        '</tr>';
    return line;
}
createTreeTable(null);
// $('#data_total').text(function(){
//     return '共有数据：' + $('tbody.x-cate tr').length + ' 条';
// });