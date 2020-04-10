layui.use(['form', 'eleTree', 'layer'], function () {
    var form = layui.form,
        $ = layui.$,
        eleTree = layui.eleTree,
        layer = layui.layer;
    window.$ = $;

    let auth = getAuth();

    var el = eleTree.render({
        elem: '#ele',
        url: base_path + 'auth/tree',
        headers: {"Authorization": auth.token_type + ' ' + auth.access_token},
        response: {
            statusName: "code",
            statusCode: 200,
            dataName: "payload"
        },
        highlightCurrent: true,
        emptText: '暂无数据',
        request: {
            name: "name"
        },
        contextmenuList: [
            {eventName: "create", text: "新增"},
            {eventName: "update", text: "编辑"},
            {eventName: "delete", text: "删除"},
            {eventName: "cp", text: "复制"},
        ],
        showCheckbox: true,
        lazy: true,
        load: function(data, callback){
            // this.filter=options.elem.attr("lay-filter");
            fengtoos.server({
                url: base_path + 'auth/tree',
                type: 'get',
                data: {parent: data.id},
                success: function(resp){
                    if(resp && resp.success){
                        setTimeout(function() {
                            callback(resp.payload);
                        },100);
                    } else {
                        callback(null);
                    }
                }
            })
        }
    });

    var resetForm = function(){
        form.val("tree-form", {
            id: "",
            parent: "",
            parentName: "根目录",
            name: "",
            url: "",
            icon: "",
            description: "",
            code: "",
            sortOrder: 1,
            status: 0
        })
        $("#icon-show").attr("class", "")
    }

    $("#newRoot").click(function(){
        resetForm();
    });

    //查看事件
    eleTree.on("nodeClick(tree)",function(d) {
        if(d.data.currentData.parent == "" || d.data.currentData.parent == null){
            d.data.currentData.parent = "";
            d.data.currentData.parentName = "根目录";
        }
        form.val("tree-form", d.data.currentData)
        $("#icon-show").attr("class", "layui-icon " + d.data.currentData.icon)
    })

    //新增事件
    eleTree.on("nodeCreate(tree)",function(d) {
        form.val("tree-form", {
            id: "",
            parent: d.data.id,
            parentName: d.data.name,
            name: "",
            url: "",
            icon: "",
            code: d.data.code,
            description: ""
        })
        $("#icon-show").attr("class", "")
    })

    //复制事件
    eleTree.on("nodeCp(tree)",function(d) {
        d.data.id = "";
        if(d.data.parent == "" || d.data.parent == null){
            d.data.parent = "";
            d.data.parentName = "根目录";
        }
        form.val("tree-form", d.data)
        $("#icon-show").attr("class", "layui-icon " + d.data.icon)
    })

    //编辑事件
    eleTree.on("nodeUpdate(tree)",function(d) {
        if(d.data.currentData.parent == "" || d.data.currentData.parent == null){
            d.data.currentData.parent = "";
            d.data.currentData.parentName = "根目录";
        }
        form.val("tree-form", d.data)
        $("#icon-show").attr("class", "layui-icon " + d.data.icon)
    })

    //删除事件
    eleTree.on("nodeDelete(tree)",function(d) {
        layer.confirm('真的删除行么', function (index) {
            //向服务端发送删除指令
            fengtoos.server({
                url: base_path + 'auth/' + d.data.id,
                type: 'delete',
                success: function(resp) {
                    console.log(resp)
                    if(resp && resp.success){
                        layer.alert("删除成功", {icon: 6}, function(index) {
                            el = el.reload();
                            layer.close(index);
                        });
                    } else {
                        layer.msg(resp.msg, {icon: 2});
                    }
                    layer.close(index);
                }
            })
        });
    })

    //自定义验证规则
    form.verify({
        name: function(value) {
            if (value.length < 5) {
                return '昵称至少得5个字符啊';
            }
        },
        pass: [/(.+){6,12}$/, '密码必须6到12位'],
        repass: function(value) {
            if ($('#L_pass').val() != $('#L_repass').val()) {
                return '两次密码不一致';
            }
        }
    });

    //监听提交
    form.on('submit(submit)', function(data) {
        fengtoos.server({
            url: base_path + 'auth/add',
            data: JSON.stringify(data.field),
            contentType: 'application/json',
            success: function(resp){
                if(resp && resp.success){
                    //发异步，把数据提交给php
                    layer.alert("保存成功", {
                        icon: 6
                    }, function(index) {
                        el = el.reload();
                        resetForm();
                        layer.close(index);
                    });
                } else {
                    layer.msg(resp.msg, {icon: 2});
                }
            }
        })
        return false;
    });
});