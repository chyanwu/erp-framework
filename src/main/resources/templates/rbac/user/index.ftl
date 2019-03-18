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
<script type="text/javascript" src="/assets/js/rbac/user/index.js"></script>
</body>
</html>