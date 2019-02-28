<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>角色管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="/assets/libs/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/assets/common.css" media="all">
    <link rel="stylesheet" href="/layui_ext/dtree/dtree.css">
    <link rel="stylesheet" href="/layui_ext/dtree/font/dtreefont.css">
</head>

<body>

<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-card-body layui-form">
            搜索：
            <div style="display: inline-block;margin-right: 10px;width: 160px;">
                <input id="edtSearch" class="layui-input" type="text" placeholder="输入关键字"/>
            </div>
            <button id="btnSearch" class="layui-btn icon-btn"><i class="layui-icon">&#xe615;</i>搜索</button>
            <button id="btnAdd" class="layui-btn icon-btn"><i class="layui-icon">&#xe654;</i>添加</button>

            <table class="layui-table" id="roleTable" lay-filter="roleTable"></table>
        </div>
    </div>
</div>

<!-- 表格操作列 -->
<script type="text/html" id="tableBar">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">修改</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    <a class="layui-btn layui-btn-xs" lay-event="auth">权限分配</a>
</script>
<!-- 表单弹窗 -->
<script type="text/html" id="roleForm">
    <form lay-filter="roleForm" class="layui-form model-form">
        <input name="id" type="hidden"/>
        <div class="layui-form-item">
            <label class="layui-form-label">角色名</label>
            <div class="layui-input-block">
                <input name="name" placeholder="请输入角色名" type="text" class="layui-input"
                       maxlength="20" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                <textarea name="comment" placeholder="请输入备注" class="layui-textarea" maxlength="200"></textarea>
            </div>
        </div>
        <div class="layui-form-item text-right">
            <button class="layui-btn layui-btn-primary" type="button" ew-event="closeDialog">取消</button>
            <button class="layui-btn" lay-filter="roleFormSubmit" lay-submit>保存</button>
        </div>
    </form>
</script>

<!-- js部分 -->
<script type="text/javascript" src="/assets/libs/layui/layui.js"></script>
<script type="text/javascript" src="/assets/common.js"></script>
<script>
    layui.extend({
        dtree: '../../layui_ext/dtree/dtree'
    }).use(['layer', 'form', 'table', 'util', 'admin', 'dtree'], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;
        var table = layui.table;
        var util = layui.util;
        var admin = layui.admin;
        var dtree = layui.dtree;

        //渲染表格
        table.render({
            elem: '#roleTable',
            url: '/erprole/getlist',
            method: 'post',
            page: false,
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
                {field: 'name', title: '角色名'},
                {field: 'comment', title: '备注'},
                {
                    templet: function (d) {
                        return util.toDateString(d.createDate);
                    }, title: '创建时间'
                },
                {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
            ]],
            // skin: 'line',
            // size: 'lg'
            done: function () {
                layer.closeAll('loading');
            }
        });

        // 添加按钮点击事件
        $('#btnAdd').click(function () {
            showEditModel();
        });

        // 搜索按钮点击事件
        $('#btnSearch').click(function () {
            var keyword = $('#edtSearch').val();
            table.reload('roleTable', {where: {keyword: keyword}});
        });

        // 工具条点击事件
        table.on('tool(roleTable)', function (obj) {
            var data = obj.data;
            if (obj.event === 'edit') { //修改
                showEditModel(data);
            } else if (obj.event === 'del') { // 删除
                doDelete(obj);
            } else if (obj.event === 'auth') {  // 权限管理
                showPermDialog(data.id);
            }
        });

        // 删除
        function doDelete(obj) {
            layer.confirm('确定要删除吗？', {
                offset: '65px',
            }, function (i) {
                layer.close(i);
                layer.load(2);
                $.post("/erprole/delete", obj.data, function (res){
                    layer.closeAll('loading');
                    if(res.code == 200){
                        layer.msg("删除成功");
                        obj.del();
                    }else{
                        layer.msg(res.message);
                    }
                });
            });
        }

        // 显示编辑弹窗
        function showEditModel(data) {
            layer.open({
                type: 1,
                area: '360px',
                offset: '65px',
                title: data ? '修改角色' : '添加角色',
                content: $('#roleForm').html(),
                success: function () {
                    form.val('roleForm', data);
                    // 表单提交事件
                    form.on('submit(roleFormSubmit)', function (d) {
                        layer.load(2);
                        if(data) {
                            $.post("/erprole/update", d.field, function (res){
                                layer.closeAll("loading");
                                if(res.code == 200){
                                    layer.msg("修改成功！", {icon: 1});
                                    layer.closeAll('page');
                                    table.reload('roleTable');
                                }else{
                                    layer.msg(res.msg, {icon: 2});
                                }
                            });
                        } else {
                            $.post("/erprole/create", d.field, function (res){
                                layer.closeAll("loading");
                                if(res.code == 200){
                                    layer.msg("添加成功！", {icon: 1});
                                    layer.closeAll('page');
                                    table.reload('roleTable');
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

        // 权限管理
        function showPermDialog(roleId) {
            layer.open({
                type: 1,
                area: '360px',
                offset: '65px',
                title: '角色权限分配',
                area: ["360px", "60%"],
                content: '<ul id="openTree5" class="dtree" data-id="0"></ul>',
                btn: ['确认'],
                success: function (layero, index) {
                    var DTree = dtree.render({
                        obj: $(layero).find("#openTree5"),
                        url: "/erpmenu/gettree?id=" + roleId,
                        checkbar: true, // 开启复选框
                        done: function(data, obj){  //使用异步加载回调

                        }
                    });
                },
                yes: function(index, layero) {
                    var params = dtree.getCheckbarNodesParam("openTree5"); // 获取选中值
                    if(params.length == 0){
                        layer.msg("请至少选择一个节点",{icon:2});
                        return false;
                    }
                    var ids = [], names = [];
                    for(var key in params){
                        var param = params[key];
                        ids.push(param.nodeId);
                        names.push(param.context);
                    }
                    var strIds = ids.join(",");
                    $.post("/erprole/auth", {id: roleId, ids: strIds}, function (res){
                        layer.close(index);
                        if(res.code == 200){
                            layer.msg("角色权限分配成功！",{icon: 1});
                        }else{
                            layer.msg(res.msg,{icon:2});
                        }
                    });
                }
            });
        }

    });

</script>

</body>
</html>