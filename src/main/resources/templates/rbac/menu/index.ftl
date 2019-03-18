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
<script type="text/javascript" src="/assets/js/rbac/menu/index.js"></script>

</body>
</html>