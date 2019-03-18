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
<script type="text/javascript" src="/assets/js/rbac/role/index.js"></script>
</body>
</html>