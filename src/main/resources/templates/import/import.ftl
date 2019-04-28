<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>上传页面</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/assets/libs/layui/css/layui.css" media="all">
</head>

<body>
<form class="layui-form">


    <div class="layui-form-item">
        <label for="filePath" class="layui-form-label">Excel文件 </label>
        <div class="layui-input-inline">
            <input type="text" id="filePath" name="filePath" class="layui-input" type="text" disabled="disabled">
        </div>
        <div class="layui-btn-container">
            <button class="layui-btn" id="searchFile" lay-filter="searchFile" type="button">选择文件</button>
            <button class="layui-btn" id="uploadFile" lay-submit="" type="button"><i class="layui-icon">&#xe62f;</i>上传
            </button>
            <button class="layui-btn layui-hide" id="downloadError" onclick="downloadErrorExcel()"><i
                        class="layui-icon">&#xe601;</i>错误数据下载
            </button>
        </div>
    </div>
</form>
<table class="layui-hide" id="excel_table" lay-filter="excel_table"></table>
<!--</div>-->
<script type="text/javascript" src="/assets/libs/layui/layui.js"></script>
<script charset="utf-8">
    var getListUrl = '/erpstudentexcel/getlist';

    layui.use(['form', 'jquery', 'util', 'layer', 'table', 'laydate', 'upload'], function () {
        var form = layui.form,
            $ = layui.jquery,
            util = layui.util,
            layer = layui.layer;
        table = layui.table;
        laydate = layui.laydate;
        upload = layui.upload;
        table.render({
            elem: '#excel_table',
            url: getListUrl,
            title: '信息',
            defaultToolbar: [],
            cellMinWidth: 80,
            cols: [[
                {type: 'numbers'},
                {field: 'name', title: '姓名'},
                {field: 'reason', title: '导入失败原因', width: 250},
                {field: 'age', title: '年龄'},
                {field: 'address', title: '地址'}
            ]]
            , response: {
                statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
            }
            ,parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.total, //解析数据长度
                    "data": res.data //解析数据列表
                };
            }
            , page: true
            , done: function (res, curr, count) {
                if (count == 0) {
                    $("#downloadError").attr("class", "layui-btn layui-hide");
                } else {
                    $("#downloadError").attr("class", "layui-btn");
                }
            }
        });
        Callback = function (result) {
            if (result.code == 200) {
            }
        }
        //监听提交
        form.on('submit(add)', function (data) {
            var msgIndex = layer.msg('处理中...', {shade: [0.8, '#393D49'], time: 1500});
            var index = parent.layer.getFrameIndex(window.name); //zhixian
            var f = data.field;

            api('', 'GET', updateUrl, Callback, '');

            return false;

        });
        var updateUrl;
        updateUrl = '/erpstudent/excelImport'
        //选完文件后不自动上传
        upload.render({
            elem: '#searchFile'
            , url: updateUrl
            , auto: false
            , accept: 'file'
            , choose: function (obj) {
                var files = this.files = obj.pushFile();
                obj.preview(function (index, file, result) {

                    $('#filePath').val(file.name);
                });
            }
            , multiple: false
            , bindAction: '#uploadFile'
            , done: function (res, index, upload) {
                if (res.code == 200) {
                    layer.open({
                        title: '提示'
                        , content: "上传成功"
                    });
                    // $("#downloadError").attr("class", "layui-btn");
                    table.reload('excel_table');
                    window.parent.layui.table.reload('studentTable');
                    return delete this.files[index]; //删除文件队列已经上传成功的文件
                }
                this.error(index, upload);
            }
            , error: function (index, upload) {
                var tr = demoListView.find('tr#upload-' + index)
                    , tds = tr.children();
                tds.eq(1).html('<span style="color: #FF5722;">上传失败</span>');
                tds.eq(2).find('.demo-reload').removeClass('layui-hide'); //显示重传
            }
        });
        downloadErrorExcel = function () {
            var exportUrl = '/erpstudent/exportErrol2excel';
            window.open(exportUrl);
        }

    });


</script>

</body>

</html>