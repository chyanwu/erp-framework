<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>学生管理</title>
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
                    <option value="name">姓名</option>
                </select>
            </div>
            <div style="display: inline-block;margin-right: 10px;width: 160px;">
                <input id="edtSearch" class="layui-input" type="text" placeholder="输入关键字"/>
            </div>
            <div class="layui-inline">
                <button id="btnSearch" class="layui-btn icon-btn"><i class="layui-icon">&#xe615;</i>搜索</button>
                <button id="btnAdd" class="layui-btn icon-btn"><i class="layui-icon">&#xe654;</i>添加</button>
                <button class="layui-btn" id="import"><i class="layui-icon">&#xe62f;</i>批量导入</button>
                <button class="layui-btn" id="download"><i class="layui-icon">&#xe601;</i>模板下载</button>
            </div>

            <table class="layui-table" id="studentTable" lay-filter="studentTable"></table>
        </div>
    </div>

</div>

<!-- 表格操作列 -->
<script type="text/html" id="tableBar">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">修改</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="export">导出</a>
</script>
<!-- 表格操作列 -->
<script type="text/html" id="tableMemberBar">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<!-- 表单弹窗 -->
<script type="text/html" id="studentForm">
    <form lay-filter="studentForm" class="layui-form model-form">
        <input name="id" type="hidden"/>
        <div class="layui-form-item">
            <label class="layui-form-label">姓名</label>
            <div class="layui-input-block">
                <input name="name" placeholder="请输入姓名" type="text" class="layui-input"
                       maxlength="20" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">年龄</label>
            <div class="layui-input-block">
                <input name="age" placeholder="请输入年龄" type="text" class="layui-input"
                       maxlength="20" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">地址</label>
            <div class="layui-input-block">
                <input name="address" placeholder="请输入地址" type="text" class="layui-input"
                       lay-verify="required" required/>
            </div>
        </div>

        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>家庭成员</legend>
        </fieldset>
        <div style="margin-left: 10px;">
            <button class="layui-btn layui-btn-sm" type="button" id="addMemberButton">新增</button>
            <table class="layui-table" id="memberTable" lay-filter="memberTable"></table>
        </div>

        <div class="layui-form-item text-right">
            <button class="layui-btn layui-btn-primary" type="button" ew-event="closeDialog">取消</button>
            <button class="layui-btn" lay-filter="formSubmit" lay-submit>保存</button>
        </div>
    </form>
</script>
<!-- 表单弹窗 -->
<script type="text/html" id="memberForm">
    <form lay-filter="memberForm" class="layui-form model-form">
        <div class="layui-form-item">
            <label class="layui-form-label">成员关系</label>
            <div class="layui-input-block">
                <select name="relation" id="relation" lay-filter="relation" lay-search="" lay-verify="required" required></select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">成员姓名</label>
            <div class="layui-input-block">
                <input name="name" placeholder="请输入姓名" type="text" class="layui-input" maxlength="20" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">成员工作</label>
            <div class="layui-input-block">
                <input name="job" placeholder="请输入工作" type="text" class="layui-input" maxlength="50" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item text-right">
            <button class="layui-btn layui-btn-primary" type="button" ew-event="closeDialog">取消</button>
            <button class="layui-btn" lay-filter="memberFormSubmit" lay-submit>保存</button>
        </div>
    </form>
</script>

<!-- js部分 -->
<script type="text/javascript" src="/assets/libs/layui/layui.js"></script>
<script type="text/javascript" src="/assets/common.js"></script>
<script type="text/javascript" src="/assets/js/import/index.js"></script>
<script type="text/javascript" src="/assets/js/baseParam.js"></script>
</body>
</html>