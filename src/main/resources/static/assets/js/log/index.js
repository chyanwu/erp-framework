layui.use(['layer', 'form', 'table', 'util', 'formSelects', 'admin'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var table = layui.table;
    var util = layui.util;
    var admin = layui.admin;

    // 渲染表格
    table.render({
        elem: '#logTable',
        url: '/erplog/getlist',
        method: 'post',
        page: true,
        cellMinWidth: 100,
        cols: [[
            {type: 'numbers'},
            {field: 'username', title: '用户名'},
            {field: 'uri', title: '请求地址'},
            {field: 'classMethod', title: '请求方法'},
            {field: 'params', title: '请求参数'},
            {field: 'executeTime', title: '执行时长(毫秒)'},
            {field: 'host', title: 'IP地址'},
            {
                templet: function (d) {
                    return util.toDateString(d.startTime);
                }, title: '创建时间'
            },
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
        var key = $('#sltKey').val();
        var value = $('#edtSearch').val();
        table.reload('logTable', {where: {searchKey: key, searchValue: value}});
    });

    // 工具条点击事件
    table.on('tool(logTable)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'del') { // 重置密码
            layer.confirm('是否要删除当前数据？', function (i) {
                $.post("/erplog/softDelete", {id: data.id}, function (res){
                    // layer.closeAll("loading");
                    if(res.code == 200){
                        layer.msg("删除成功！",{time: 1000},function(){
                            table.reload("userTable");
                        });
                    }else{
                        layer.msg(res.message);
                    }
                });
            });
        }
    });

});