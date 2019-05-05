layui.use(['layer', 'form', 'table', 'util', 'formSelects', 'admin'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var util = layui.util;
    var formSelects = layui.formSelects;
    var admin = layui.admin;

    var relationObj = {1: '爸爸', 2: '妈妈', 3: '爷爷', 4: '奶奶'};
    var memberTabData, relationData, removeRelationData;

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
        } else if (obj.event === 'export') { // 导出
            exportPdf(data);
        }
    });

    function exportPdf(data) {
        window.open('/erpstudent/htmltopdf?id=' + data.id,'target','');
    }

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
            area: ['60%', '90%'],
            offset: '65px',
            title: data ? '修改学生' : '添加添加',
            content: $('#studentForm').html(),
            success: function (layero, index) {
                $(layero).children('.layui-layer-content').css('overflow', 'visible');

                // 每次弹框，都要给成员关系初始化数据
                relationData = [{id: 1, name: '爸爸'}, {id: 2, name: '妈妈'}, {id: 3, name: '爷爷'}, {id: 4, name: '奶奶'}];

                // 回显user数据
                if (data) {
                    form.val('studentForm', data);
                    showMemberTab(data.id);
                } else {
                    showMemberTab();
                }
                // 表单提交事件
                form.on('submit(formSubmit)', function (d) {
                    layer.load(2);
                    d.field.erpSFamilyMemberList = memberTabData;
                    if(data) {
                        admin.req("/erpstudent/update", d.field, function (res){
                            layer.closeAll("loading");
                            if(res.code == 200){
                                layer.msg("修改成功！", {icon: 1});
                                layer.closeAll('page');
                                table.reload('studentTable');
                            }else{
                                layer.msg(res.msg, {icon: 2});
                            }
                        }, 'POST');
                    } else {
                        admin.req("/erpstudent/create", d.field, function (res){
                            layer.closeAll("loading");
                            if(res.code == 200){
                                layer.msg("添加成功！", {icon: 1});
                                layer.closeAll('page');
                                table.reload('studentTable');
                            }else{
                                layer.msg(res.msg, {icon: 2});
                            }
                        }, 'POST');
                    }
                    return false;
                });
            }
        });
    }
    
    function showMemberTab(stuId) {
        memberTabData = [];
        removeRelationData = [];
        if(stuId) {
            // 获取项目的数据
            $.get("/erpsfamilymember/getByStuId", {stuId: stuId}, function (res) {
                if(res.code == 200) {
                    memberTabData = res.data;
                    table.reload('memberTable',{data : memberTabData});
                    // 处理 relationData，removeRelationData
                    removeRelationData = intersection(relationData, memberTabData, 'relation');
                    relationData = difference(relationData, memberTabData, 'relation');
                }
            });
        }

        //渲染表格
        table.render({
            elem: '#memberTable',
            data: memberTabData,
            method: 'get',
            page: false,
            cellMinWidth: 100,
            cols: [[
                {
                    templet: function (d) {
                        return relationObj[d.relation];
                    }, title: '成员关系'
                },
                {field: 'name', title: '成员信息'},
                {field: 'job', title: '成员工作'},
                {align: 'center', toolbar: '#tableMemberBar', title: '操作', minWidth: 200}
            ]],
            response: {
                statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
            },
            done: function () {
                layer.closeAll('loading');
            }
        });

        // 添加按钮点击事件
        $('#addMemberButton').click(function () {
            showMemberForm();
        });

        // 工具条点击事件
        table.on('tool(memberTable)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;

            if (layEvent === 'del') { // 删除
                doMemberDelete(obj);
            } else if (layEvent === 'edit') { // 修改
                showMemberForm(data);
            }
        });
    }

    // 项目成员表删除
    function doMemberDelete(obj) {
        var id = obj.data.id;
        layer.confirm('确定要删除吗？', {
            offset: '65px',
            title: '提示'
        }, function (i) {
            layer.close(i);
            var tmpMemberData = [];
            for(var i=0; i<memberTabData.length; i++) {
                if(id != memberTabData[i].id) {
                    tmpMemberData.push(memberTabData[i]);
                }
            }
            memberTabData = tmpMemberData;

            var tempRmData = [];
            for(var i=0; i<removeRelationData.length; i++) {
                if(id != removeRelationData[i].id) {
                    tempRmData.push(removeRelationData[i]);
                } else {
                    relationData.push(removeRelationData[i]);
                }
            }
            removeRelationData = tempRmData;

            layer.msg("删除成功");
            obj.del();
        });
    }

    function initSelect(ctlId, data, tip) {
        var $sel = $("#"+ ctlId);
        $sel.empty();
        $sel.append('<option value="'+""+'">'+"请选择" + tip +'</option>');
        for(var i=0; i<data.length; i++){
            $sel.append('<option selected value="'+ data[i].id +'">'+ data[i].name +'</option>');
        }
        $sel.val('');
    };

    function showMemberForm(data) {
        var index = layer.open({
            type: 1,
            area: ['360px','320px'],
            offset: '65px',
            title: data ? '修改家庭成员' : '添加家庭成员',
            content: $('#memberForm').html(),
            success: function () {
                // 渲染项目成员
                if(data) {
                    var tmp = [{id: data.relation, name: relationObj[data.relation]}].concat(relationData);
                    arraySort(tmp);
                    initSelect("relation", tmp, "");

                    form.val('memberForm', data);
                } else {
                    arraySort(relationData);
                    initSelect("relation", relationData, "");
                }

                form.render();
                // 表单提交事件
                form.on('submit(memberFormSubmit)', function (d) {
                    layer.load(2);
                    if(data) {
                        layer.closeAll("loading");
                        // 实现思路，将form的数据认为是新的数据，直接进行添加，对表格的数据，重新更新memberTabData
                        var tmpMemberData = [];
                        var rowData_ = {};
                        rowData_.relation = d.field.relation;
                        rowData_.name = d.field.name;
                        rowData_.job = d.field.job;

                        for(var i=0; i<memberTabData.length; i++) {
                            if(data.relation == memberTabData[i].relation) {
                                tmpMemberData.push(rowData_);
                            } else {
                                tmpMemberData.push(memberTabData[i]);
                            }
                        }
                        memberTabData = tmpMemberData;
                        table.reload('memberTable',{data : memberTabData});

                        var tempData = [];
                        for(var i=0; i<relationData.length; i++) {
                            if(rowData_.relation != relationData[i].relation) {
                                tempData.push(relationData[i]);
                            } else {
                                removeRelationData.push(relationData[i]);
                            }
                        }
                        relationData = tempData;

                        var tempRmData = [];
                        for(var i=0; i<removeRelationData.length; i++) {
                            if(data.relation != removeRelationData[i].relation) {
                                tempRmData.push(removeRelationData[i]);
                            } else {
                                relationData.push(removeRelationData[i]);
                            }
                        }
                        removeRelationData = tempRmData;

                        layer.msg("修改成功！", {icon: 1});
                        layer.close(index);
                    } else {
                        layer.closeAll("loading");

                        var rowData_ = {};
                        rowData_.relation = d.field.relation;
                        rowData_.name = d.field.name;
                        rowData_.job = d.field.job;
                        memberTabData.push(rowData_);
                        table.reload('memberTable',{data : memberTabData});

                        var tempData = [];
                        for(var i=0; i<relationData.length; i++) {
                            if(rowData_.relation != relationData[i].relation) {
                                tempData.push(relationData[i]);
                            } else {
                                removeRelationData.push(relationData[i]);
                            }
                        }
                        relationData = tempData;

                        layer.msg("添加成功！", {icon: 1});
                        layer.close(index);
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