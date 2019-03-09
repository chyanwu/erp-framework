<!DOCTYPE html>
<html class="loginHtml">
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
    <link rel="stylesheet" href="./css/login.css">
</head>
<body>
<div class="login">
    <h1>erp-framework后台管理</h1>
    <form class="layui-form" action="/login/main" method="post">
        <div class="layui-form-item">
            <input class="layui-input" name="username" value="" placeholder="请输入用户名" lay-verify="required" type="text" autocomplete="off">
        </div>
        <div class="layui-form-item">
            <input class="layui-input" name="password" value="" placeholder="请输入密码" lay-verify="required" type="password" autocomplete="off">
        </div>
        <div class="layui-form-item form_code">
            <input class="layui-input" name="code" placeholder="验证码" lay-verify="required" type="text" autocomplete="off">
            <div class="code"><img src="/genCaptcha" width="116" height="36" id="mycode"></div>
        </div>
        <div class="layui-form-item">
            <input type="checkbox" name="rememberMe" value="true" lay-skin="primary" checked title="记住帐号?">
        </div>
        <div class="layui-form-item">
            <button class="layui-btn login_btn" lay-submit="" lay-filter="login">登录</button>
        </div>
        <#--<div class="layui-form-item">-->
            <#--<fieldset class="layui-elem-field">-->
                <#--<div class="layui-field-box" style="color: #fff;font-size: 20px;">-->
                    <#--用户名:admin &nbsp;&nbsp;&nbsp;密码:1-->
                <#--</div>-->
            <#--</fieldset>-->
        <#--</div>-->
    </form>
</div>
<script type="text/javascript" src="/assets/libs/layui/layui.js"></script>
<script>
    layui.use(['layer', 'form'], function() {
        var layer = layui.layer,
            $ = layui.jquery,
            form = layui.form;

        $("#mycode").on('click',function(){
            var t = Math.random();
            $("#mycode")[0].src="/genCaptcha?t= "+t;
        });

        form.on('submit(login)', function(data) {
            var loadIndex = layer.load(2, {
                shade: [0.3, '#333']
            });
            if($('form').find('input[type="checkbox"]')[0].checked){
                data.field.rememberMe = true;
            }else{
                data.field.rememberMe = false;
            }
            $.post(data.form.action, data.field, function(res) {
                layer.close(loadIndex);
                if(res.code == 200){
                    location.href=res.data.url;
                }else{
                    layer.msg(res.msg);
                    $("#mycode").click();
                }
            });
            return false;
        });
    });
</script>
</body>
</html>