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

    <!-- 加载进度条效果 -->
    <link href="./plugins/pace/themes/pink/pace-theme-flash.css" rel="stylesheet"/>
    <script src="./plugins/pace/pace.min.js"></script>

    <!-- 实现页签上右键菜单功能 -->
    <link href="./plugins/contextMenu/jquery.contextMenu.min.css" rel="stylesheet"/>


    <link href="./plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>
<#--<link rel="stylesheet" href="./plugins/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>-->
<#--<link href="./plugins/toastr/toastr.min.css" rel="stylesheet"/>-->
    <link rel="stylesheet" type="text/css" href="./plugins/layui/css/layui.css"/>
    <link rel="stylesheet" href="./css/admin.css">
    <script type="text/javascript" src="./plugins/jquery/jquery.min.js"></script>
    <script src="./plugins/contextMenu/jquery.contextMenu.min.js"></script>
<#--<script type="text/javascript" src="./plugins/ztree/js/jquery.ztree.all.min.js"></script>-->
<#--<script src="./plugins/toastr/toastr.min.js"></script>-->
    <script type="text/javascript" src="./plugins/layui/layui.js"></script>
<#--<script type="text/javascript" src="./plugins/frame/js/fsDict.js?v=2.1.1"></script>-->
    <script type="text/javascript" src="./plugins/frame/js/fs.js?v=2.1.1"></script>
    <script type="text/javascript" src="./plugins/frame/js/main.js"></script>
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
                <a href="javascript:;">FallSea</a>
                <dl class="layui-nav-child">
                    <dd><a href="">帮助中心</a></dd>
                    <dd><a href="login.html">退出</a></dd>
                </dl>
            </li>
        </ul>
    </div>

    <div class="layui-side custom-admin">
        <div class="layui-side-scroll">

            <div class="custom-logo">
                <img src="./images/logo.png" alt=""/>
                <h1>fsLayui2</h1>
            </div>
            <ul class="layui-nav layui-nav-tree fsLeftMenu"  lay-filter="fsLeftMenu" lay-shrink="all" id="fsLeftMenu">

            </ul>

        </div>
    </div>

    <div class="layui-body">
        <div class="layui-tab app-container fsTab" lay-filter="fsTab" lay-allowClose="true">
            <ul id="fsTabMenu" class="layui-tab-title custom-tab">
                <li class="layui-this"><i class="layui-icon">&#xe68e;</i><em>控制台</em><p class="layui-tab-close" style="display: none;"></p></li>
            </ul>
            <div id="appTabPage" class="layui-tab-content">
                <div class="layui-tab-item layui-show" lay-id="1">
                    <iframe src="views/home/index.html?v=2.1.1" frameborder="0"></iframe>
                </div>
            </div>
        </div>
    </div>

    <div class="layui-footer">
        <p>© 2017-2019 chenyanwu</p>
    </div>
</div>
</body>
</html>