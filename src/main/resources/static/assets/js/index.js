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
            window.sessionStorage.setItem("href", url);  //当前打开的url
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

    // 当界面刷新，则需要显示到当前打开的界面
    if(window.sessionStorage.getItem("href") != null){
        var href = window.sessionStorage.getItem("href");
        if(href != '/home/index') {
            $('iframe[name=body]').attr('src', href);
        }
    }

});