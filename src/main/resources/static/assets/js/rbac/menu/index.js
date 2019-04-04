layui.config({
    treetable: '/assets/layui_ext/treetable-lay/'
}).use(['layer', 'table', 'treetable', 'form', 'admin'], function () {
    var layer = layui.layer,
        treetable = layui.treetable,
        table = layui.table,
        $ = layui.jquery,
        admin = layui.admin,
        form = layui.form;
    var util = layui.util;

    var setTree = function(data){
        treetable.render({
            treeColIndex: 1,          // 树形图标显示在第几列
            treeSpid: 0,             // treetable新增参数
            treeIdName: 'id',       // treetable新增参数
            treePidName: 'parentId',     // treetable新增参数
            treeDefaultClose: false,   // treetable新增参数
            treeLinkage: true,        // treetable新增参数
            elem: '#menuTable',
            data: data,
            // url: '/erpmenu/getlist',
            // method: 'POST',
            page: false,
            cols: [[
                {type: 'numbers'},
                {field: 'name', title: '菜单名称', minWidth: 160},
                {field: 'url', title: '菜单URL', minWidth: 120},
                {field: 'permission', title: '权限标识', width: 120},
                {field: 'level', title: '层次', width: 80},
                {field: 'sort', title: '排序', width: 80},
                {
                    templet: function (d) {
                        return util.toDateString(d.createDate);
                    }, title: '创建时间'
                },
                // {field: 'id', title: 'id'},
                // {field: 'parentId', title: 'parentId'},
                {
                    field: 'isMenu', width: 80, align: 'center', templet: function (d) {
                        if (d.type == 1) {
                            return '<span class="layui-badge layui-bg-gray">按钮</span>';
                        }
                        if (d.parentId == 0) {
                            return '<span class="layui-badge layui-bg-blue">目录</span>';
                        } else {
                            return '<span class="layui-badge-rim">菜单</span>';
                        }
                    }, title: '类型'
                },
                {templet: '#auth-state', align: 'center', title: '操作',minWidth: 240}
            ]],
            done: function () {
                layer.closeAll('loading');
            }
        });
    };

    var loadDataToTable = function() {
        layer.load(2);
        $.post("/erpmenu/getlist",function(res){
            layer.closeAll("loading");
            if(res.code == 200){
                setTree(res.data);
            }else{
                layer.msg(res.msg);
            }
        });
    };

    loadDataToTable();

    //监听工具条
    table.on('tool(menuTable)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'del') {
            delMenu(obj);
        } else if (layEvent === 'edit') {
            editMenu(data);
        } else if (layEvent === 'addChildMenu') {
            addChildMenu(data);
        }
    });

    // 添加按钮点击事件
    $('#addMenuBtn').click(function () {
        editMenu();
    });

    function addChildMenu(data) {
        layer.open({
            type: 1,
            area: '360px',
            offset: '65px',
            title: '添加子菜单',
            content: $('#childMenuForm').html(),
            success: function () {
                // form.val('childMenuForm', data);
                $("#pname").val(data.name)
                $("#parentId").val(data.id)
                // radio初始化
                var a = $("#childMenuForm1").find("[name='type']");
                a.each(function() {
                    this.value === data.type && (this.checked = !0)
                })
                form.render(null, "childMenuForm");
                // 表单提交事件
                form.on('submit(menuFormSubmit)', function (d) {
                    layer.load(2);
                    $.post("/erpmenu/create", d.field, function (res){
                        layer.closeAll("loading");
                        if(res.code == 200){
                            layer.msg("添加成功！", {icon: 1});
                            layer.closeAll('page');
                            loadDataToTable();
                        }else{
                            layer.msg(res.msg, {icon: 2});
                        }
                    });
                    return false;
                });
            }
        });
    }

    function editMenu(data) {
        layer.open({
            type: 1,
            area: '360px',
            offset: '65px',
            title: data ? '编辑菜单': '添加根菜单',
            content: $('#menuForm').html(),
            success: function () {
                form.val('menuForm', data);
                // 表单提交事件
                form.on('submit(menuFormSubmit)', function (d) {
                    layer.load(2);
                    if(data) {
                        $.post("/erpmenu/update", d.field, function (res){
                            layer.closeAll("loading");
                            if(res.code == 200){
                                layer.msg("修改成功！", {icon: 1});
                                layer.closeAll('page');
                                loadDataToTable();
                            }else{
                                layer.msg(res.msg, {icon: 2});
                            }
                        });
                    } else {
                        $.post("/erpmenu/create", d.field, function (res){
                            layer.closeAll("loading");
                            if(res.code == 200){
                                layer.msg("添加成功！", {icon: 1});
                                layer.closeAll('page');
                                loadDataToTable();
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

    function delMenu(obj) {
        if(obj.data.isParent) {
            layer.msg("该菜单存在子菜单，需先删除子菜单后才可以删除！");
            return false;
        }
        layer.confirm("你确定要删除该菜单么？",{btn:['确定','取消']},
            function(){
                $.post("/erpmenu/deleteByID", {id: obj.data.id}, function (res){
                    if(res.code == 200){
                        layer.msg("删除成功",{time: 1000},function(){
                            // table.reload("menuTable");
                            // loadDataToTable();
                            obj.del();
                        });
                    }else{
                        layer.msg(res.message);
                    }
                });
            }
        )
    }

});