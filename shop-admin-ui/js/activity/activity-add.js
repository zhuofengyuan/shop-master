layui.use(['form', 'layer', 'carousel', 'upload', 'layedit', 'laydate', 'table'], function () {
    var form = layui.form,
        layer = layui.layer,
        carousel = layui.carousel,
        upload = layui.upload,
        $ = layui.jquery,
        table = layui.table,
        layedit = layui.layedit,
        laydate = layui.laydate;

    //设定各种参数
    var ins3 = carousel.render({
        elem: '#carousel'
    });

    //富文本
    layedit.build("description");

    //日期控件
    laydate.render({
        elem: '#beginTime',
        calendar: true
    });
    laydate.render({
        elem: '#endTime',
        calendar: true
    });

    //切换活动简介
    form.on('switch(news_switch)', function(data){
        if(data.elem.checked){
            $('#news_content').show();
        } else {
            $('#news_content').hide();
        }
    });

    //自定义验证规则
    form.verify({
        nikename: function (value) {
            if (value.length < 5) {
                return '昵称至少得5个字符啊';
            }
        },
        pass: [/(.+){6,12}$/, '密码必须6到12位'],
        repass: function (value) {
            if ($('#L_pass').val() != $('#L_repass').val()) {
                return '两次密码不一致';
            }
        }
    });

    //监听提交
    form.on('submit(add)',
        function (data) {
            console.log(data);
            //发异步，把数据提交给php
            layer.alert("增加成功", {
                    icon: 6
                },
                function () {
                    //关闭当前frame
                    xadmin.close();

                    // 可以对父窗口进行刷新
                    xadmin.father_reload();
                });
            return false;
        });
    //多文件列表示例
    var demoListView = $('#demoList'), uploadListIns = upload.render({
        elem: '#testList'
        , headers: {
            "Authorization": getAuth().token_type + ' ' + getAuth().access_token,
        }
        , url: base_path + 'admin/file'
        , accept: 'images'
        , multiple: true
        , auto: false
        , bindAction: '#testListAction'
        , before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
            obj.preview(function (index, file, result) {
                delete file.type
                console.log(file.type);
            })
            layer.load(); //上传loading
        }
        , choose: function (obj) {
            var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
            //读取本地文件
            obj.preview(function (index, file, result) {
                var tr = $(['<tr id="upload-' + index + '">'
                    , '<td>' + file.name + '</td>'
                    , '<td>' + (file.size / 1014).toFixed(1) + 'kb</td>'
                    , '<td>等待上传</td>'
                    , '<td>'
                    , '<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
                    , '<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
                    , '</td>'
                    , '</tr>'].join(''));

                //单个重传
                tr.find('.demo-reload').on('click', function () {
                    obj.upload(index, file);
                });

                //删除
                tr.find('.demo-delete').on('click', function () {
                    delete files[index]; //删除对应的文件
                    tr.remove();
                    uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选

                    //刪除轮播图
                    $('#carousel-items div:eq(' + ($(this).index() - 1) + ')').remove();
                    ins3.reload();
                });

                demoListView.append(tr);

                //新增轮播图
                $('#carousel-items').append('<div><img src="' + result + '" alt="' + file.name + '" class="layui-upload-img"></div>')
                ins3.reload();
            });
        }
        , done: function (res, index, upload) {
            layer.closeAll('loading'); //关闭loading
            if (res.code == 200) { //上传成功
                var tr = demoListView.find('tr#upload-' + index)
                    , tds = tr.children();
                tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                tds.eq(3).html(''); //清空操作
                return delete this.files[index]; //删除文件队列已经上传成功的文件
            }
            this.error(index, upload)
        }
        , error: function (index, upload) {
            layer.closeAll('loading'); //关闭loading
            var tr = demoListView.find('tr#upload-' + index)
                , tds = tr.children();
            tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
            tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
        }
    });

    //执行一个 table 实例
    var childTable = table.render({
        elem: '#child_table'
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
});