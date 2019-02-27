<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>菜单管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">

    <link rel="stylesheet" href="/assets/libs/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/assets/common.css" media="all">
    <#--<link rel="stylesheet" href="/root/assets/module/treetable-lay/treetable.css" media="all">-->

    <#--<style type="text/css">-->
    <#--.layui-form.model-form {-->
    <#--padding: 30px 30px 0px 0px;-->
    <#--}-->
    <#--.text-right {-->
    <#--text-align: right;-->
    <#--}-->
    <#--</style>-->
    <style type="text/css">
        .treeTable-empty {
            width: 20px;
            display: inline-block;
        }

        .treeTable-icon {
            cursor: pointer;
        }

        .treeTable-icon .layui-icon-triangle-d:before {
            content: "\e623";
        }

        .treeTable-icon.open .layui-icon-triangle-d:before {
            content: "\e625";
            background-color: transparent;
        }
    </style>
</head>
<body style="background: #EEEEEE;">
<div style="margin: 15px;">
    <div style="border-radius: 2px; background-color: #fff; box-shadow: ">
        <div style="position: relative; padding: 10px 15px; line-height: 24px;">
            <button id="addMenuBtn" class="layui-btn">添加根菜单</button>
            <table id="menuTable" class="layui-table" lay-filter="menuTable"></table>
        </div>
    </div>
</div>
<!-- 操作列 -->
<script type="text/html" id="auth-state">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="addChildMenu">添加子菜单</a>
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">编辑菜单</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<!-- 表单弹窗 -->
<script type="text/html" id="menuForm">
    <form lay-filter="menuForm" class="layui-form model-form">
        <input name="id" type="hidden"/>
        <input name="parentId" type="hidden">
        <div class="layui-form-item">
            <label class="layui-form-label">菜单名称</label>
            <div class="layui-input-block">
                <input name="name" placeholder="请输入菜单名称" type="text" class="layui-input"
                       maxlength="20" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">菜单URL</label>
            <div class="layui-input-block">
                <input name="url" placeholder="请输入菜单URL" type="text" class="layui-input"
                       maxlength="20"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">权限标识</label>
            <div class="layui-input-block">
                <input name="permission" type="text" class="layui-input"
                       maxlength="20"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">层次</label>
            <div class="layui-input-block">
                <input name="level" type="number" class="layui-input"
                       maxlength="20"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">排序</label>
            <div class="layui-input-block">
                <input name="sort" type="number" class="layui-input"
                       maxlength="20"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">是否菜单</label>
            <div class="layui-input-block">
                <input type="radio" name="type" value="0" title="是" checked/>
                <input type="radio" name="type" value="1" title="否"/>
            </div>
        </div>
        <div class="layui-form-item text-right">
            <button class="layui-btn layui-btn-primary" type="button" ew-event="closeDialog">取消</button>
            <button class="layui-btn" lay-filter="menuFormSubmit" lay-submit>保存</button>
        </div>
    </form>
</script>

<script type="text/html" id="childMenuForm">
    <form lay-filter="childMenuForm" class="layui-form model-form" id="childMenuForm1">
        <input name="id" type="hidden"/>
        <input id="parentId" name="parentId" type="hidden">
        <div class="layui-form-item">
            <label class="layui-form-label">父菜单名称</label>
            <div class="layui-input-block">
                <input id="pname" name="pname" type="text" class="layui-input"
                       maxlength="20" disabled />
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">菜单名称</label>
            <div class="layui-input-block">
                <input name="name" placeholder="请输入菜单名称" type="text" class="layui-input"
                       maxlength="20" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">菜单URL</label>
            <div class="layui-input-block">
                <input name="url" placeholder="请输入菜单URL" type="text" class="layui-input"
                       maxlength="20"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">权限标识</label>
            <div class="layui-input-block">
                <input name="permission" type="text" class="layui-input"
                       maxlength="20"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">层次</label>
            <div class="layui-input-block">
                <input name="level" type="number" class="layui-input"
                       maxlength="20"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">排序</label>
            <div class="layui-input-block">
                <input name="sort" type="number" class="layui-input"
                       maxlength="20"/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">是否菜单</label>
            <div class="layui-input-block">
                <input type="radio" name="type" value="0" title="是" checked/>
                <input type="radio" name="type" value="1" title="否"/>
            </div>
        </div>

        <div class="layui-form-item text-right">
            <button class="layui-btn layui-btn-primary" type="button" ew-event="closeDialog">取消</button>
            <button class="layui-btn" lay-filter="menuFormSubmit" lay-submit>保存</button>
        </div>
    </form>
</script>

<script type="text/javascript" src="/assets/libs/layui/layui.js"></script>
<script type="text/javascript" src="/assets/common.js"></script>

<script>
    layui.config({
        treetable: '/assets/layui_ext/treetable-lay/'
    }).use(['layer', 'table', 'treetable', 'form', 'admin'], function () {
        var layer = layui.layer,
            treetable = layui.treetable,
            table = layui.table,
            $ = layui.jquery,
            admin = layui.admin,
            form = layui.form;

        var setTree = function(data){
            treetable.render({
                treeColIndex: 1,          // 树形图标显示在第几列
                treeSpid: 0,             // treetable新增参数
                treeIdName: 'id',       // treetable新增参数
                treePidName: 'parentId',     // treetable新增参数
                treeDefaultClose: false,   // treetable新增参数
                treeLinkage: true,        // treetable新增参数
                elem: '#menuTable',
                data: data,
                // url: '/erpmenu/getlist',
                // method: 'POST',
                page: false,
                cols: [[
                    {type: 'numbers'},
                    {field: 'name', title: '菜单名称', minWidth: 160},
                    {field: 'url', title: '菜单URL', minWidth: 120},
                    {field: 'permission', title: '权限标识', width: 120},
                    {field: 'level', title: '层次', width: 80},
                    {field: 'sort', title: '排序', width: 80},
                    {field: 'createDate', title: '创建时间'},
                    // {field: 'id', title: 'id'},
                    // {field: 'parentId', title: 'parentId'},
                    {
                        field: 'isMenu', width: 80, align: 'center', templet: function (d) {
                            if (d.type == 1) {
                                return '<span class="layui-badge layui-bg-gray">按钮</span>';
                            }
                            if (d.parentId == 0) {
                                return '<span class="layui-badge layui-bg-blue">目录</span>';
                            } else {
                                return '<span class="layui-badge-rim">菜单</span>';
                            }
                        }, title: '类型'
                    },
                    {templet: '#auth-state', align: 'center', title: '操作',minWidth: 240}
                ]],
                done: function () {
                    layer.closeAll('loading');
                }
            });
        };

        var loadDataToTable = function() {
            layer.load(2);
            $.post("/erpmenu/getlist",function(res){
                layer.closeAll("loading");
                if(res.code == 200){
                    setTree(res.data);
                }else{
                    layer.msg(res.message);
                }
            });
        };

        loadDataToTable();

        //监听工具条
        table.on('tool(menuTable)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;

            if (layEvent === 'del') {
                delMenu(obj);
            } else if (layEvent === 'edit') {
                editMenu(data);
            } else if (layEvent === 'addChildMenu') {
                addChildMenu(data);
            }
        });

        // 添加按钮点击事件
        $('#addMenuBtn').click(function () {
            editMenu();
        });

        function addChildMenu(data) {
            layer.open({
                type: 1,
                area: '360px',
                offset: '65px',
                title: '添加子菜单',
                content: $('#childMenuForm').html(),
                success: function () {
                    // form.val('childMenuForm', data);
                    $("#pname").val(data.name)
                    $("#parentId").val(data.id)
                    // radio初始化
                    var a = $("#childMenuForm1").find("[name='type']");
                    a.each(function() {
                        this.value === data.type && (this.checked = !0)
                    })
                    form.render(null, "childMenuForm");
                    // 表单提交事件
                    form.on('submit(menuFormSubmit)', function (d) {
                        $.ajaxSettings.async = false;
                        $.post("/erpmenu/create", d.field, function (res){
                            if(res.code == 200){
                                layer.msg("添加成功",{time: 1000},function(){
                                    table.reload("menuTable");
                                    // loadDataToTable();
                                });
                            }else{
                                layer.msg(res.message);
                            }
                        });
                        $.ajaxSettings.async = true;
                    });
                }
            });
        }

        function editMenu(data) {
            layer.open({
                type: 1,
                area: '360px',
                offset: '65px',
                title: data ? '编辑菜单': '添加根菜单',
                content: $('#menuForm').html(),
                success: function () {
                    form.val('menuForm', data);
                    // 表单提交事件
                    form.on('submit(menuFormSubmit)', function (d) {
                        $.ajaxSettings.async = false;
                        if(data) {
                            $.post("/erpmenu/update", d.field, function (res){
                                if(res.code == 200){
                                    layer.msg("修改成功",{time: 1000},function(){
                                        table.reload("menuTable");
                                        // loadDataToTable();
                                    });
                                }else{
                                    layer.msg(res.message);
                                }
                            });
                        } else {
                            $.post("/erpmenu/create", d.field, function (res){
                                if(res.code == 200){
                                    layer.msg("插入成功",{time: 1000},function(){
                                        table.reload("menuTable");
                                        // loadDataToTable();
                                    });
                                }else{
                                    layer.msg(res.message);
                                }
                            });
                        }
                        $.ajaxSettings.async = true;
                    });
                }
            });
        }

        function delMenu(obj) {
            if(obj.data.isParent) {
                layer.msg("该菜单存在子菜单，需先删除子菜单后才可以删除！");
                return false;
            }
            layer.confirm("你确定要删除该菜单么？",{btn:['确定','取消']},
                function(){
                    $.post("/erpmenu/deleteByID", {id: obj.data.id}, function (res){
                        if(res.code == 200){
                            layer.msg("删除成功",{time: 1000},function(){
                                // table.reload("menuTable");
                                // loadDataToTable();
                                obj.del();
                            });
                        }else{
                            layer.msg(res.message);
                        }
                    });
                }
            )
        }

    });
</script>

</body>
</html>