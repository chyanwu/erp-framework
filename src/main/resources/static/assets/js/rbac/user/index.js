layui.use(['layer', 'form', 'table', 'util', 'formSelects', 'admin'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var util = layui.util;
    var formSelects = layui.formSelects;
    var admin = layui.admin;
    var mRoles = [];  // 全部角色

    // 渲染表格
    table.render({
        elem: '#userTable',
        url: '/erpuser/getlist',
        method: 'post',
        // where: {
        //     access_token: admin.getToken().access_token
        // },
        page: true,
        cellMinWidth: 100,
        response: {
            statusName: 'code' //规定数据状态的字段名称，默认：code
            ,statusCode: 200 //规定成功的状态码，默认：0
            ,msgName: 'msg' //规定状态信息的字段名称，默认：msg
            ,countName: 'count' //规定数据总数的字段名称，默认：count
            ,dataName: 'data' //规定数据列表的字段名称，默认：data
        },
        cols: [[
            {type: 'numbers'},
            {field: 'loginName', title: '账号'},
            {field: 'name', title: '用户名'},
            {field: 'phone', title: '手机号'},
            {field: 'email', title: '邮箱'},
            {
                templet: function (d) {
                    var str = '';
                    for (var i = 0; i < d.roles.length; i++) {
                        str += ('<span class="layui-badge-rim">' + d.roles[i].name + '</span>');
                    }
                    return str;
                }, title: '角色'
            },
            {
                templet: function (d) {
                    return util.toDateString(d.createDate);
                }, title: '创建时间'
            },
            {field: 'state', templet: '#tbaleState', title: '状态', unresize: true, width: 100},
            {align: 'center', toolbar: '#tableBar', title: '操作', unresize: true, width: 200}
        ]],
        // skin: 'line',
        // size: 'lg',
        done: function () {
            layer.closeAll('loading');
        }
    });

    // 搜索按钮点击事件
    $('#userBtnSearch').click(function () {
        var key = $('#sltKey').val();
        var value = $('#edtSearch').val();
        table.reload('userTable', {where: {searchKey: key, searchValue: value}});
    });

    // 添加按钮点击事件
    $('#userBtnAdd').click(function () {
        showEditModel();
    });

    // 工具条点击事件
    table.on('tool(userTable)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'reset') { // 重置密码
            layer.confirm('确定重置此用户的密码吗？<br><span style="color: red;">（初始密码为123456）</span>', function (i) {
                $.post("/erpuser/psw", {id: data.id}, function (res){
                    // layer.closeAll("loading");
                    if(res.code == 200){
                        layer.msg("重置密码成功！",{time: 1000},function(){
                            table.reload("userTable");
                        });
                    }else{
                        layer.msg(res.message);
                    }
                });
            });
        } else if (layEvent === 'edit') { // 修改
            showEditModel(data);
        }
    });

    // 修改user状态
    form.on('switch(ckState)', function (obj) {
        $.post("/erpuser/state", {id: obj.elem.value, state: obj.elem.checked ? 1 : 0}, function (res){
            if(res.code == 200){

            }else{
                layer.msg(res.message);
            }
        });
    });

    // 显示表单弹窗
    function showEditModel(data) {
        layer.open({
            type: 1,
            area: '360px',
            offset: '65px',
            title: data ? '修改用户' : '添加用户',
            content: $('#userForm').html(),
            success: function (layero, index) {
                $(layero).children('.layui-layer-content').css('overflow', 'visible');
                form.render('radio');
                // 渲染角色下拉框
                var roleSelData = new Array();
                for (var i = 0; i < mRoles.length; i++) {
                    roleSelData.push({name: mRoles[i].name, value: mRoles[i].id});
                }
                formSelects.data('selRole', 'local', {arr: roleSelData});
                // 回显user数据
                if (data) {
                    form.val('userForm', data);
                    var rds = new Array();
                    for (var i = 0; i < data.roles.length; i++) {
                        rds.push(data.roles[i].id);
                    }
                    formSelects.value('selRole', rds);  // 回显多选框
                }
                // 表单提交事件
                form.on('submit(userFormSubmit)', function (d) {
                    d.field.roleIds = formSelects.value('selRole', 'valStr');
                    layer.load(2);
                    if(data) {
                        $.post("/erpuser/update", d.field, function (res){
                            layer.closeAll("loading");
                            if(res.code == 200){
                                layer.msg("修改成功！", {icon: 1});
                                layer.closeAll('page');
                                table.reload('userTable');
                            }else{
                                layer.msg(res.msg, {icon: 2});
                            }
                        });
                    } else {
                        $.post("/erpuser/create", d.field, function (res){
                            layer.closeAll("loading");
                            if(res.code == 200){
                                layer.msg("添加成功！", {icon: 1});
                                layer.closeAll('page');
                                table.reload('userTable');
                            }else{
                                layer.msg(res.msg, {icon: 2});
                            }
                        });
                    }
                    return false;
                });
            }
        });
    }

    // 获取所有角色
    layer.load(2);
    $.get("/erprole/getall", {}, function (res) {
        layer.closeAll('loading');
        if(res.code == 200) {
            mRoles = res.data;
        } else {
            layer.msg('获取角色失败');
        }
    });

});