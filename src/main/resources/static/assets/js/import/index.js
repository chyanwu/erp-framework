layui.use(['layer', 'form', 'table', 'util', 'formSelects', 'admin'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var util = layui.util;
    var formSelects = layui.formSelects;
    var admin = layui.admin;

    // 渲染表格
    table.render({
        elem: '#studentTable',
        url: '/erpstudent/getlist',
        method: 'post',
        page: true,
        cellMinWidth: 100,
        cols: [[
            {type: 'numbers'},
            {field: 'name', title: '姓名'},
            {field: 'age', title: '年龄'},
            {field: 'address', title: '地址'},
            {align: 'center', toolbar: '#tableBar', title: '操作', unresize: true, width: 200}
        ]],
        response: {
            statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
        },
        parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
            return {
                "code": res.code, //解析接口状态
                "msg": res.msg, //解析提示文本
                "count": res.total, //解析数据长度
                "data": res.data //解析数据列表
            };
        },
        done: function () {
            layer.closeAll('loading');
        }
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        var keyword = $('#edtSearch').val();
        table.reload('studentTable', {where: {keyword: keyword}});
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        showEditModel();
    });

    $('#download').click(function () {
        downloadExcel();
    });

    $('#import').click(function () {
        showExcelModule();
    });

    // 工具条点击事件
    table.on('tool(studentTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') { //修改
            showEditModel(data);
        } else if (obj.event === 'del') { // 删除
            doDelete(obj);
        }
    });

    // 删除
    function doDelete(obj) {
        layer.confirm('确定要删除吗？', {
            offset: '65px',
        }, function (i) {
            layer.close(i);
            layer.load(2);
            $.post("/erpstudent/delete", obj.data, function (res){
                layer.closeAll('loading');
                if(res.code == 200){
                    layer.msg("删除成功");
                    obj.del();
                }else{
                    layer.msg(res.message);
                }
            });
        });
    }

    // 显示表单弹窗
    function showEditModel(data) {
        layer.open({
            type: 1,
            area: '360px',
            offset: '65px',
            title: data ? '修改学生' : '添加添加',
            content: $('#studentForm').html(),
            success: function (layero, index) {
                $(layero).children('.layui-layer-content').css('overflow', 'visible');
                // 回显user数据
                if (data) {
                    form.val('studentForm', data);
                }
                // 表单提交事件
                form.on('submit(formSubmit)', function (d) {
                    layer.load(2);
                    if(data) {
                        $.post("/erpstudent/update", d.field, function (res){
                            layer.closeAll("loading");
                            if(res.code == 200){
                                layer.msg("修改成功！", {icon: 1});
                                layer.closeAll('page');
                                table.reload('studentTable');
                            }else{
                                layer.msg(res.msg, {icon: 2});
                            }
                        });
                    } else {
                        $.post("/erpstudent/create", d.field, function (res){
                            layer.closeAll("loading");
                            if(res.code == 200){
                                layer.msg("添加成功！", {icon: 1});
                                layer.closeAll('page');
                                table.reload('studentTable');
                            }else{
                                layer.msg(res.msg, {icon: 2});
                            }
                        });
                    }
                    return false;
                });
            }
        });
    }

    function downloadExcel() {
        var exportUrl = '/erpstudent/export2excel';
        window.open(exportUrl);
    }

    function showExcelModule() {
        window.addShow("导入", "./import");
    }

});