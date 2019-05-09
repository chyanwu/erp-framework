<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>videoPlay</title>
    <link rel="stylesheet" href="/assets/libs/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/assets/common.css" media="all">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<div class="layui-card-body layui-form">
    <div class="proSchoolUpload" style="margin-left: 10px;margin-right: 10px"></div>
</div>
</body>
<script type="text/javascript" src="/assets/libs/layui/layui.js"></script>
<script type="text/javascript" src="/assets/common.js"></script>
<!--引用layui-element模块显示进度条-->
<script src="/assets/libs/layui/lay/modules/element.js"></script>
<script src="/assets/module/uploadcustom/global.js"></script>
<script type="text/javascript">
    layui.use(["jquery", "layer", "webUploadJs"], function () {
        var webUploadJs = layui.webUploadJs;
        //init($,layer);
        var title = "多视频上传";
        var type = "rm,rmvb,mpeg1-4,mov,mtv,dat,wmv,avi,3gp,amv,dmv,flv,exe";
        //调用插件上传
        webUploadJs.webuploadInit(title, type);
    });
</script>

</html>
