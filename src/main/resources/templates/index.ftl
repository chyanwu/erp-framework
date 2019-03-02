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
            <#--<ul id="fsTabMenu" class="layui-tab-title custom-tab">-->
                <#--<li class="layui-this"><i class="layui-icon">&#xe68e;</i><em>控制台</em><p class="layui-tab-close" style="display: none;"></p></li>-->
            <#--</ul>-->
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
<script>
    layui.use(['layer', 'form', 'element'], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var element = layui.element;
        var form = layui.form;

        var showMenu = function(){
            layer.load(2);
            $.get("/erpmenu/getUserMenu", {}, function (res) {
                layer.closeAll('loading');
                if(res.code == 200) {
                    var menuTree = res.data;
                    if(menuTree.length > 0){
                        var fsLeftMenu = $("#fsLeftMenu");
                        //一级菜单处理，头部导航菜单
                        $(menuTree).each(function(i1,v){
                            var menuRow = '<li class="layui-nav-item';
                            if(!isEmpty(v.href) && v.href == '/home/index'){//默认选中处理
                                menuRow += ' layui-this';
                            }
                            menuRow += '" lay-id="'+ v.id +'"><a href="javascript:;" menuId="'+ v.id +'" dataUrl="'+ v.href +'">';
                            if(v.icon != null && v.icon != ''){
                                if(v.icon.indexOf("icon-") !== -1){
                                    menuRow += '<i class="iconfont '+ v.icon +'" style="font-size: 20px" data-icon="'+ v.icon +'"></i>';
                                }else{
                                    menuRow += '<i class="layui-icon" style="font-size: 20px" data-icon="'+ v.icon +'">'+ v.icon +'</i>';
                                }
                            }
                            menuRow += ' <em>'+ v.title +'</em></a>';
                            //处理子集菜单
                            var xcontent  = handleLeftMenuData(v.children);

                            if(!isEmpty(xcontent)){
                                menuRow += xcontent;
                            }
                            menuRow += '</li>';

                            fsLeftMenu.append(menuRow);

                        });

                    }

                    element.render("nav");
                    slideSideBar();
                } else {
                    layer.msg('获取菜单数据失败');
                }
            });
        };
        showMenu();

        var isEmpty = function(value) {
            if (value === null || value == undefined || value === '') {
                return true;
            }
            return false;
        };

        var handleLeftMenuData = function(children){
            var content = "";
            if(children.length > 0){

                content = '<dl class="layui-nav-child">';
                $.each(children,function(i,v){
                    // var menuRow3 = '<dd';
                    // if(!isEmpty(v.href) && v.href == '/home/index'){//默认选中处理
                    //     menuRow3 += ' class="layui-this"';
                    // }
                    var menuRow3 = '<dd lay-id="'+ v.id +'"><a href="javascript:;" menuId="'+ v.id +'" dataUrl="'+ (isEmpty(v.children) ? '' : v.href) +'">';
                    if(v.icon != null && v.icon != ''){
                        if(v.icon.indexOf("icon-") !== -1){
                            menuRow3 += '<i class="iconfont '+ v.icon +'" style="font-size: 20px" data-icon="'+ v.icon +'"></i>';
                        }else{
                            menuRow3 += '<i class="layui-icon" style="font-size: 20px" data-icon="'+ v.icon +'">'+ v.icon +'</i>';
                        }
                    }
                    menuRow3 += ' <em>'+ v.title +'</em></a>';
                    content += menuRow3;

                    //多级处理
                    content += handleLeftMenuData(v.children);
                });
                content += '</dl>';
                return content;
            }
            return content;
        };

        var slideSideBar = function() {
            var $slideSidebar = $('.slide-sidebar');
            var isFold = false;
            $slideSidebar.click(function(e){
                e.preventDefault();
                var $this = $(this), $icon = $this.find('i'),
                        $admin = $('body').find('.layui-layout-admin');
                var toggleClass =  'fold-side-bar';
                if($icon.hasClass('ai-menufold')){
                    $icon.removeClass('ai-menufold').addClass('ai-menuunfold');
                    $admin.addClass(toggleClass);
                    isFold = true;
                }else{
                    $icon.removeClass('ai-menuunfold').addClass('ai-menufold');
                    $admin.removeClass(toggleClass);
                    isFold = false;
                }
            });

            var tipIndex;
            // 菜单收起后的模块信息小提示
            $('#fsLeftMenu li > a').hover(function(){
                var $this = $(this);
                if(isFold) {
                    tipIndex = layer.tips($this.find('em').text(),$this);
                }
            }, function(){
                if(isFold && tipIndex ){
                    layer.close(tipIndex);
                    tipIndex = null
                }
            });

            // 侧边栏点击事件
            $('*[dataurl]').click(function () {
                var url = $(this).attr('dataurl');
                if(url == '') {
                    return;
                }
                $('#fsLeftMenu .layui-this').removeClass('layui-this');
                var layId = $(this).parent().attr('lay-id');
                if(!isEmpty(layId)){

                    $('#fsLeftMenu .layui-nav-child>dd[lay-id="'+ layId +'"],#fsLeftMenu>li[lay-id="'+ layId +'"]').addClass('layui-this');

                }
                $('iframe[name=body]').attr('src', url);
            });
        };

        // 修改密码
        $('#btnUP').click(function () {
            layer.open({
                type: 1,
                title: '修改密码',
                area: '360px',
                offset: '65px',
                content: $('#upModel').html()
            });
        });

        // 监听修改密码表单提交
        form.on('submit(submitPsw)', function (d) {
            layer.load(2);
            $.post("/erpuser/updatepsw", d.field, function (res){
                if(res.code == 200){
                    layer.closeAll('loading');
                    layer.closeAll('page');
                    layer.msg('密码修改成功', {icon: 1, time: 1500}, function () {
                        location.href = '/systemLogout';
                    });
                }else{
                    layer.closeAll('loading');
                    layer.msg(res.msg, {icon: 2});
                }
            });
            return false;
        });

        // 添加表单验证方法
        form.verify({
            repsw: function (t) {
                if (t !== $('#formPsw input[name=newPsw]').val()) {
                    return '两次密码输入不一致';
                }
            }
        });

    });
</script>
</body>
</html>