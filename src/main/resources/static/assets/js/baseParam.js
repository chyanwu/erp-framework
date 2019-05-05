var testMode = false;

layui.define(['jquery', 'formSelects'], function (exports) {
    var $ = layui.jquery;
    var formSelects = layui.formSelects;

    /**
     * 用于格式化下拉窗口
     * @param result
     * @param obj ctlId: 下拉框id； data：下拉框数据； tip：下拉框提示语
     */
    initSelect = function (ctlId, data, tip) {
        var $sel = $("#"+ ctlId);
        $sel.empty();
        $sel.append('<option value="'+""+'">'+"请选择" + tip +'</option>');
        for(var i=0; i<data.length; i++){
            $sel.append('<option selected value="'+ data[i].id +'">'+ data[i].name +'</option>');
        }
        $sel.val('');
    };

    /**
     * 下载文件
     * @param url
     * @param options
     */
    download = function(url, options) {
        var opt = {
            method: "POST",
            data: null,
            timeout: 1000 * 60 * 2,
            onError: function (text) {
                return text || "没有找到匹配的文件";
            },
        };
        $.extend(opt, options);

        var iframeName = "download-" + Date.now(),
            // 为了避免页面跳转，采用创建临时iframe的方式
            $iframe = $('<iframe name="' + iframeName + '" style="display:none;"></iframe>').appendTo(document.body);

        // 给iframe传递一个错误处理回调函数等遇到错误时调用
        $iframe[0].errorCallback = opt.onError;

        if (opt.method.toUpperCase() === "POST") { // POST 方式
            var iframeDoc = $iframe.prop("contentDocument") || $iframe.prop("contentWindow").document, // 获取iframe的document对象
                $form = null,
                data = opt.data;
            // 为iframe写入一个form元素，利用该form元素发起文件下载请求
            iframeDoc.write('<form method="POST" action="' + url + '"></form>');
            $form = $(iframeDoc).find("form"); // 获取该form元素
            // 带请求参数的情况
            if (data instanceof Object) {
                if (Array.isArray(data)) { // data是数组形式
                    data.forEach(function (o) {
                        if (o.value) $("<input>").prop(o).appendTo($form);
                    });
                } else { // data是对象形式
                    for (var n in data) {
                        $("<input>").prop({ name: n, value: data[n] }).appendTo($form);
                    }
                }
            }
            // 提交表单
            $form.submit();
        } else { // 默认 GET 方式
            url.indexOf("?") < 0 ? url += "?" : url += "&";
            window.open(url + $.param(opt.data), iframeName);
        }

        // 移除临时iframe
        setTimeout(function () {
            $iframe.remove();
        }, opt.timeout);
    };

    // 交集
    intersection = function(arr1, arr2, key) {
        var tmpArr = [];
        for(var i=0;i<arr1.length;i++){
            for(var j=0;j<arr2.length;j++){
                var arr = arr2[j];
                if(arr1[i].id == eval('arr.' + key)){
                    tmpArr.push(arr1[i]);
                }
            }
        }
        return tmpArr;
    };

    // 差集
    difference = function(arr1, arr2, key) {
        var tmpArr = [];
        for(var i=0;i<arr1.length;i++){
            var flag = true;
            for(var j=0;j<arr2.length;j++){
                var arr = arr2[j];
                if(arr1[i].id == eval('arr.' + key)){
                    flag = false;
                }
            }
            if(flag){
                tmpArr.push(arr1[i]);
            }
        }
        return tmpArr;
    };

    // 按ID排序
    arraySort = function(arr) {
        arr.sort(function(a,b){
            if(a.id>b.id) return 1 ;
            if(a.id<b.id) return -1 ;
            return 0 ;
        });
    };

    window.addShow = function (title, url) {
        if (title == null || title === '') {
            title = false;
        }
        if (url == null || url === '') {
            url = "404.html";
        }
        var index = layer.open({
            type: 2,
            title: title,
            area: ['1024px', '100%'],
            content: url
        });
    };
});
