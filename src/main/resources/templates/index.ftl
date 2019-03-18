<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>erp-framework后台管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">

    <link rel="stylesheet" href="/assets/libs/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="./css/admin.css">
    <script type="text/javascript" src="/assets/libs/layui/layui.js"></script>
    <script type="text/javascript" src="/assets/js/index.js"></script>

</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header custom-header">

        <ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item slide-sidebar" lay-unselect>
                <a href="javascript:;" class="icon-font"><i class="ai ai-menufold"></i></a>
            </li>
        </ul>

        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <cite><#if currentUser.name!=''>${currentUser.name}<#else>${currentUser.loginName}</#if></cite>
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="javascript:" id="btnUP">修改密码</a></dd>
                    <dd><a href="/systemLogout" class="signOut">退出</a></dd>
                </dl>
            </li>
        </ul>
    </div>

    <div class="layui-side custom-admin">
        <div class="layui-side-scroll">

            <div class="custom-logo">
                <img src="./images/logo.png" alt=""/>
                <h1>erp-framework</h1>
            </div>
            <ul class="layui-nav layui-nav-tree fsLeftMenu"  lay-filter="fsLeftMenu" lay-shrink="all" id="fsLeftMenu">

            </ul>

        </div>
    </div>

    <div class="layui-body">
        <div class="layui-tab app-container fsTab" lay-filter="fsTab" lay-allowClose="true">
            <div id="appTabPage" class="layui-tab-content">
                <div class="layui-tab-item layui-show" lay-id="1">
                    <iframe name="body" src="/home/index" frameborder="0"></iframe>
                </div>
            </div>
        </div>
    </div>

    <div class="layui-footer">
        <p>© 2017-2019 chenyanwu</p>
    </div>
</div>
<script type="text/html" id="upModel">
    <form class="layui-form model-form" id="formPsw">
        <div class="layui-form-item">
            <label class="layui-form-label">原始密码:</label>
            <div class="layui-input-block">
                <input type="password" name="oldPsw" placeholder="请输入原始密码" class="layui-input"
                       lay-verType="tips" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">新密码:</label>
            <div class="layui-input-block">
                <input type="password" name="newPsw" placeholder="请输入新密码" class="layui-input"
                       lay-verType="tips" lay-verify="required|psw" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">确认密码:</label>
            <div class="layui-input-block">
                <input type="password" name="rePsw" placeholder="请再次输入新密码" class="layui-input"
                       lay-verType="tips" lay-verify="required|repsw" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block text-right">
                <button class="layui-btn layui-btn-primary" type="button" ew-event="closeDialog">取消</button>
                <button class="layui-btn" lay-filter="submitPsw" lay-submit>保存</button>
            </div>
        </div>
    </form>
</script>
</body>
</html>