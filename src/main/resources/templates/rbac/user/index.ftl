<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>用户管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">

    <link rel="stylesheet" href="/assets/libs/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/assets/module/formSelects/formSelects-v4.css" media="all">
    <link rel="stylesheet" href="/assets/common.css" media="all">
</head>
<body>
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-card-body layui-form">
            <div style="display: inline-block;width: 100px;margin-right: 10px;">
                <select id="sltKey">
                    <option value="">搜索条件</option>
                    <option value="loginName">账号</option>
                    <option value="name">用户名</option>
                    <option value="phone">手机号</option>
                </select>
            </div>
            <div style="display: inline-block;margin-right: 10px;width: 160px;">
                <input id="edtSearch" class="layui-input" type="text" placeholder="输入关键字"/>
            </div>
            <div class="layui-inline">
                <button id="userBtnSearch" class="layui-btn icon-btn"><i class="layui-icon">&#xe615;</i>搜索</button>
                <button id="userBtnAdd" class="layui-btn icon-btn"><i class="layui-icon">&#xe654;</i>添加</button>
            </div>

            <table class="layui-table" id="userTable" lay-filter="userTable"></table>
        </div>
    </div>

</div>

<!-- 表格操作列 -->
<script type="text/html" id="tableBar">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">修改</a>
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="reset">重置密码</a>
    <#--<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>-->
</script>
<!-- 表格状态列 -->
<script type="text/html" id="tbaleState">
    <input type="checkbox" lay-filter="ckState" lay-skin="switch" lay-text="启用|停用"
           value="{{d.id}}" {{d.enabled==1?'checked':''}}/>
</script>
<!-- 表单弹窗 -->
<script type="text/html" id="userForm">
    <form lay-filter="userForm" class="layui-form model-form">
        <input name="id" type="hidden"/>
        <div class="layui-form-item">
            <label class="layui-form-label">账号</label>
            <div class="layui-input-block">
                <input name="loginName" placeholder="请输入账号" type="text" class="layui-input"
                       maxlength="20" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">用户名</label>
            <div class="layui-input-block">
                <input name="name" placeholder="请输入用户名" type="text" class="layui-input"
                       maxlength="20" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">手机号</label>
            <div class="layui-input-block">
                <input name="phone" placeholder="请输入手机号" type="text" class="layui-input"
                       lay-verify="required|phone" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">邮箱</label>
            <div class="layui-input-block">
                <input name="email" placeholder="请输入邮箱" type="text" class="layui-input"
                       lay-verify="email" autocomplete="off" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">角色</label>
            <div class="layui-input-block">
                <select name="roleId" xm-select="selRole" xm-select-skin="default" lay-verify="required"></select>
            </div>
        </div>
        <div class="layui-form-item text-right">
            <button class="layui-btn layui-btn-primary" type="button" ew-event="closeDialog">取消</button>
            <button class="layui-btn" lay-filter="userFormSubmit" lay-submit>保存</button>
        </div>
    </form>
</script>

<!-- js部分 -->
<script type="text/javascript" src="/assets/libs/layui/layui.js"></script>
<script type="text/javascript" src="/assets/common.js"></script>
<script>
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

            // if (layEvent === 'del') { // 删除
            //     layer.confirm('确定删除此用户吗？', function (i) {
            //
            //     });
            // } else
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
            // layer.load(2);
            $.post("/erpuser/state", {id: obj.elem.value, state: obj.elem.checked ? 1 : 0}, function (res){
                // layer.closeAll("loading");
                if(res.code == 200){
                    // layer.msg("！",{time: 1000},function(){
                    //     table.reload("userTable");
                    // });
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
</script>
</body>
</html>