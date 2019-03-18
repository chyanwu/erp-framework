layui.extend({
    dtree: '../../layui_ext/dtree/dtree'
}).use(['layer', 'form', 'table', 'util', 'admin', 'dtree'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var util = layui.util;
    var admin = layui.admin;
    var dtree = layui.dtree;

    //渲染表格
    table.render({
        elem: '#roleTable',
        url: '/erprole/getlist',
        method: 'post',
        page: false,
        cellMinWidth: 100,
        response: {
            statusName: 'code' //规定数据状态的字段名称，默认：code
            ,statusCode: 200 //规定成功的状态码，默认：0
            ,msgName: 'msg' //规定状态信息的字段名称，默认：msg
            ,countName: 'count' //规定数据总数的字段名称，默认：count
            ,dataName: 'data' //规定数据列表的字段名称，默认：data
        },
        cols: [[
            {type: 'numbers'},
            {field: 'name', title: '角色名'},
            {field: 'comment', title: '备注'},
            {
                templet: function (d) {
                    return util.toDateString(d.createDate);
                }, title: '创建时间'
            },
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
        ]],
        // skin: 'line',
        // size: 'lg'
        done: function () {
            layer.closeAll('loading');
        }
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        showEditModel();
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        var keyword = $('#edtSearch').val();
        table.reload('roleTable', {where: {keyword: keyword}});
    });

    // 工具条点击事件
    table.on('tool(roleTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') { //修改
            showEditModel(data);
        } else if (obj.event === 'del') { // 删除
            doDelete(obj);
        } else if (obj.event === 'auth') {  // 权限管理
            showPermDialog(data.id);
        }
    });

    // 删除
    function doDelete(obj) {
        layer.confirm('确定要删除吗？', {
            offset: '65px',
        }, function (i) {
            layer.close(i);
            layer.load(2);
            $.post("/erprole/delete", obj.data, function (res){
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

    // 显示编辑弹窗
    function showEditModel(data) {
        layer.open({
            type: 1,
            area: '360px',
            offset: '65px',
            title: data ? '修改角色' : '添加角色',
            content: $('#roleForm').html(),
            success: function () {
                form.val('roleForm', data);
                // 表单提交事件
                form.on('submit(roleFormSubmit)', function (d) {
                    layer.load(2);
                    if(data) {
                        $.post("/erprole/update", d.field, function (res){
                            layer.closeAll("loading");
                            if(res.code == 200){
                                layer.msg("修改成功！", {icon: 1});
                                layer.closeAll('page');
                                table.reload('roleTable');
                            }else{
                                layer.msg(res.msg, {icon: 2});
                            }
                        });
                    } else {
                        $.post("/erprole/create", d.field, function (res){
                            layer.closeAll("loading");
                            if(res.code == 200){
                                layer.msg("添加成功！", {icon: 1});
                                layer.closeAll('page');
                                table.reload('roleTable');
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

    // 权限管理
    function showPermDialog(roleId) {
        layer.open({
            type: 1,
            area: '360px',
            offset: '65px',
            title: '角色权限分配',
            area: ["360px", "60%"],
            content: '<ul id="openTree5" class="dtree" data-id="0"></ul>',
            btn: ['确认'],
            success: function (layero, index) {
                var DTree = dtree.render({
                    obj: $(layero).find("#openTree5"),
                    url: "/erpmenu/gettree?id=" + roleId,
                    checkbar: true, // 开启复选框
                    done: function(data, obj){  //使用异步加载回调

                    }
                });
            },
            yes: function(index, layero) {
                var params = dtree.getCheckbarNodesParam("openTree5"); // 获取选中值
                if(params.length == 0){
                    layer.msg("请至少选择一个节点",{icon:2});
                    return false;
                }
                var ids = [], names = [];
                for(var key in params){
                    var param = params[key];
                    ids.push(param.nodeId);
                    names.push(param.context);
                }
                var strIds = ids.join(",");
                $.post("/erprole/auth", {id: roleId, ids: strIds}, function (res){
                    layer.close(index);
                    if(res.code == 200){
                        layer.msg("角色权限分配成功！",{icon: 1});
                    }else{
                        layer.msg(res.msg,{icon:2});
                    }
                });
            }
        });
    }

});