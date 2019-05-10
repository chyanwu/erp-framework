layui.define(['layer', 'jquery'], function (exports) {
    var layer = layui.layer;
    var $ = layui.jquery;
    var obj = {
        webuploadInit: function (title,type) {
            var div = $(".proSchoolUpload");
            if (div != undefined) {
                //引用js按顺序加载否则出错
                $("body").append("<script src='/assets/libs/jquery/jquery-3.2.1.min.js'></script>");
                $("body").append("<script src='/webuploader/webuploader.min.js'>");
                //引用css
                $("head").append("<link>");
                var css = $("head").children(":last");
                css.attr({
                    rel: "stylesheet",
                    href: "/webuploader/webuploader.css"
                });
                div.append("<div class=\"layui-upload\">\n" +
                    "    <div id=\"picker\" >"+title+"</div>\n" +
                    "    <!--用来存放文件信息-->\n" +
                    "    <div id=\"thelist\" class=\"layui-upload-list\">\n" +
                    "        <table class=\"layui-table\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "            <thead>\n" +
                    "            <tr>\n" +
                    "                <th width=\"10%\" class=\"file-num\">预览区</th>\n" +
                    "                <th width=\"40%\" class=\"file-name\">名称</th>\n" +
                    "                <th width=\"10%\" class=\"file-size\">大小</th>\n" +
                    "                <th width=\"20%\" class=\"file-pro\">进度</th>\n" +
                    "                <th width=\"10%\" class=\"file-status\">状态</th>\n" +
                    "                <th width=\"10%\" class=\"file-manage\">操作</th>\n" +
                    "            </tr>\n" +
                    "            </thead>\n" +
                    "        </table>\n" +
                    "    </div>\n" +
                    "</div>");
            }
            else {
                return;
            }
            //视频上传 start
            var $list = $('#thelist .layui-table'),
                $btn = $('#ctlBtn');
            //监听分块上传过程中的三个时间点
            WebUploader.Uploader.register({
                //在文件发送之前request，此时还没有分片（如果配置了分片的话），可以用来做文件整体md5验证。
                "before-send-file": "beforeSendFile",
                //在分片发送之前request，可以用来做分片验证，如果此分片已经上传成功了，可返回一个rejected promise来跳过此分片上传
                "before-send": "beforeSend",
                //在所有分片都上传完毕后，且没有错误后request，用来做分片验证，此时如果promise被reject，当前文件上传会触发错误。
                "after-send-file": "afterSendFile"
            }, {
                //时间点1：所有分块进行上传之前调用此函数
                beforeSendFile: function (file) {
                    var deferred = WebUploader.Deferred();
                    var owner = this.owner;
                    //1、计算文件的唯一标记，用于断点续传
                    owner.md5File(file, 0, 5 * 1024 * 1024)
                        .progress(function (percentage) {
                            $('#' + file.id).find(".file-status").text("读取中...");
                            console.log('计算MD5进度：', percentage);
                            //赋值文件初始进度
                            //0-1
                            file.process = 0;
                        })
                        .then(function (md5Value) {
                            file.wholeMd5 = md5Value;
                            $('#' + file.id).find(".file-status").text("预读取完毕...");
                            //获取文件信息后进入下一步
                            deferred.resolve();
                        })
                    return deferred.promise();
                },
                //时间点2：如果有分块上传，则每个分块上传之前调用此函数
                beforeSend: function (block) {
                    var deferred = WebUploader.Deferred();
                    $.ajax({
                        type: "POST",
                        url:  "/fileupload/checkChunk",
                        data: {
                            //文件唯一标记
                            fileMd5: block.file.wholeMd5,
                            //文件名称
                            fileName: block.file.name,
                            //当前分块下标
                            chunk: block.chunk,
                            //当前分块大小
                            chunkSize: block.end - block.start
                        },
                        dataType: "json",
                        success: function (result) {
                            var data;
                            if (typeof(result) === "string") {
                                var data = JSON.parse(result); //部分用户解析出来的是字符串，转换一下
                            } else {
                                data = result;
                            }
                            if (data.code == 200) {
                                console.log("跳过" + block.chunk);
                                //分块存在，跳过
                                deferred.reject();
                            } else {
                                //分块不存在或不完整，重新发送该分块内容
                                deferred.resolve();
                            }
                        }
                    });
                    //绑定参数fileMd5为md5值
                    this.owner.options.formData.fileMd5 = block.file.wholeMd5;
                    deferred.resolve();
                    return deferred.promise();
                },
                afterSendFile: function (file) {
                    //如果分块上传成功，则通知后台合并分块
                    $('#' + file.id).find('.file-status').html('<p style="color:#ff8e3d;">转码中...</p>');
                    var $percent = $('#' + file.id).find(".file-pro").find('.layui-progress').find('.layui-progress-bar');
                    $.ajax({
                        type: "POST",
                        url: "/fileupload/combine",
                        data: {
                            name: file.name,
                            fileMd5: file.wholeMd5,
                            size: file.size
                        },
                        success: function (result) {
                            var data;
                            if (typeof(result) === "string") {
                                var data = JSON.parse(result); //部分用户解析出来的是字符串，转换一下
                            } else {
                                data = result;
                            }
                            if (data.code == 200) {
                                layer.msg(data.msg, {icon: 1, time: 3000});

                                $('#' + file.id).find('.file-status').html('<p style="color:#429b34;">上传成功</p>');
                                //进度条显示100%
                                $percent.css('width', '100%');
                                $percent.attr('lay-percent', '100%');
                                $percent.html("<span class='layui-progress-text'>100%</span>");
                                //删除和暂停都不见，显示不可操作
                                //$( '#'+file.id ).find('.file-manage').find('.stop-btn').addClass('layui-hide');
                                //$( '#'+file.id ).find('.file-manage').find('.remove-this').addClass('layui-hide');
                            } else {
                                layer.msg("合并异常!");
                                $('#' + file.id).find('.file-status').html('<p style="color:red;">上传出错</p>');
                                //隐藏暂停.隐藏不可操作
                                $('#' + file.id).find('.file-manage').find('.stop-btn').addClass("layui-hide");
                                $('#' + file.id).find('.file-manage').find('.layui-btn-disabled').addClass("layui-hide");
                                //显示重传。显示删除
                                $('#' + file.id).find('.file-manage').find('.demo-reload').removeClass("layui-hide");
                                $('#' + file.id).find('.file-manage').find('.remove-this').removeClass("layui-hide");
                            }
                        },
                        error: function () {
                            $('#' + file.id).find('.file-status').html('<p style="color:red;">上传出错</p>');
                            //显示重传。显示删除
                            $('#' + file.id).find('.file-manage').find('.demo-reload').removeClass("layui-hide");
                            $('#' + file.id).find('.file-manage').find('.remove-this').removeClass("layui-hide");
                            //隐藏暂停.隐藏不可操作
                            $('#' + file.id).find('.file-manage').find('.stop-btn').addClass("layui-hide");
                            $('#' + file.id).find('.file-manage').find('.layui-btn-disabled').addClass("layui-hide");
                        }
                    });
                }
            })
            var uploader = WebUploader.create({
                //dnd:"#dndArea",
                resize: false, // 不压缩image
                swf: '/webuploader/Uploader.swf', // swf文件路径
                server: '/fileupload/upload', // 文件接收服务端。
                pick: '#picker', // 选择文件的按钮。可选
                chunked: true, //是否要分片处理大文件上传
                chunkSize: 5 * 1024 * 1024, //分片上传，每片2M，默认是5M
                auto: true, //选择文件后是否自动上传
                chunkRetry: 5, //如果某个分片由于网络问题出错，允许自动重传次数
                threads: 8,
                duplicate: false, //是否支持重复上传
                accept:
                    {
                        title: 'upload',
                        extensions: type
                    }
            });
            // 当有文件被添加进队列的时候
            uploader.on('fileQueued', function (file) {

                $list.append('<tr id="' + file.id + '" class="file-item">'
                    + '<td class="file-num"><img></td>'
                    + '<td class="file-name">' + file.name + '</td>'
                    + '<td class="file-size">' + (file.size / 1024 / 1024).toFixed(1) + 'M' + '</td>'
                    + '<td class="file-pro"><div class="layui-progress" lay-showpercent="true"><div class="layui-progress-bar layui-bg-blue" lay-percent="0%"></div></div></td>'
                    + '<td class="file-status">等待上传...</td>'
                    + '<td class="file-manage">' +
                    '<button class="continue-btn layui-btn layui-btn-xs layui-hide">继续</button>' +
                    '<button class="stop-btn layui-btn layui-btn-xs layui-hide">暂停</button>' +
                    '<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>' +
                    '<button class="layui-btn layui-btn-disabled layui-hide">不可操作</button>' +
                    '<button class="remove-this layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button></td>' + '</tr>');
                //暂停上传的文件
                $list.on('click', '.stop-btn', function () {
                    //debugger
                    uploader.stop(true);
                    //STATUS 0-上传中，1-上传完成，2-暂停，3-上传出错
                    file.status = '2';
                    //显示继续
                    $(this).parents('.file-manage').find('.continue-btn').removeClass('layui-hide');
                    //隐藏暂停
                    $(this).addClass('layui-hide');
                });
                //删除上传的文件
                $list.on('click', '.remove-this', function () {
                    uploader.removeFile($(this).parents(".file-item").attr('id'));
                    $(this).parents(".file-item").remove();
                    //继续往下上传
                    uploader.upload();
                    if (file.status == '2') {//暂停的保持暂停
                        uploader.stop(true);
                    }
                });
                //继续上传
                $list.on('click', '.continue-btn', function () {
                    if ($(this).parents(".file-item").attr('id') == file.id) {
                        //继续上传
                        uploader.upload();
                        //继续上传
                        file.status = '0';
                        //显示暂停
                        $(this).parents('.file-manage').find('.stop-btn').removeClass('layui-hide');
                        //隐藏继续
                        $(this).addClass('layui-hide');
                    }

                });
                //重新上传
                $list.on('click', '.demo-reload', function () {
                    if ($(this).parents(".file-item").attr('id') == file.id) {
                        //重新上传
                        uploader.retry(file);
                        file.status = '0';
                        //显示暂停
                        $(this).parents('.file-manage').find('.stop-btn').removeClass('layui-hide');
                        //隐藏重传
                        $(this).addClass('layui-hide');
                        //显示等待上传
                        $(this).parents('.file-manage').siblings(".file-status").text('等待上传...');

                    }
                });
                uploader.makeThumb(file, function (error, src) {
                    if (error) {
                        $("#" + file.id).find('img').replaceWith('<span>不能预览</span>');
                        return;
                    }
                    $('#' + file.id).find('img').attr('src', src);
                }, 100, 100)
            });
            //重复添加文件
            var timer1;
            uploader.onError = function (code) {
                clearTimeout(timer1);
                timer1 = setTimeout(function () {
                    if (code == 'Q_TYPE_DENIED') {
                        layer.msg('上传格式错误', {icon: 2, time: 3000});
                    } else if (code == 'F_DUPLICATE') {
                        layer.msg('该文件已存在', {icon: 2, time: 3000});
                    }
                }, 250);
            }

            // 文件上传过程中创建进度条实时显示
            uploader.on('uploadProgress', function (file, percentage) {
                var $li = $('#' + file.id).find('.file-pro'),
                    $percent = $li.find('.layui-progress').find('.layui-progress-bar');
                // //$("td.file-pro").text("");
                //如果文件进度比当前上传进度小赋值
                //暂停后进度条续传
                // if((percentage * 100).toFixed(0)+'%'<$percent.attr('lay-percent')){
                //     alert((percentage * 100).toFixed(0)+'%'+'vs'+$percent.attr('lay-percent'));
                // }
                $li.siblings('.file-status').html('<p style="color:#ff8e3d;">上传中...</p>');
                //正在上传的文件显示暂停
                $li.siblings('.file-manage').find(".stop-btn").removeClass("layui-hide");
                //$li.find('.per').text((percentage * 100).toFixed(2) + '%');
                $percent.css('width', (percentage * 100).toFixed(0) + '%');
                $percent.attr('lay-percent', (percentage * 100).toFixed(0) + '%');
                $percent.html("<span class='layui-progress-text'>" + (percentage * 100).toFixed(0) + '%' + "</span>");
                // if (file.process < percentage && percentage < 1) {
                //     file.process = percentage;
                // }


                // 避免重复创建
                // if ( !$percent.length ) {
                //     $percent = $('<div class="file-progress progress-striped active">' +
                //         '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                //         '</div>' +
                //         '</div>' + '<br/><div class="per">0%</div>').appendTo( $li ).find('.layui-progress-bar');
                // }

            });
            // 文件上传成功
            uploader.on('uploadSuccess', function (file) {
                //显示不可操作
                $('#' + file.id).find('.file-manage').find('.layui-btn-disabled').removeClass('layui-hide');
                $('#' + file.id).find('.file-manage').find('.stop-btn').addClass('layui-hide');
                $('#' + file.id).find('.file-manage').find('.remove-this').addClass('layui-hide');
            });

            // 文件上传失败，显示上传出错
            uploader.on('uploadError', function (file) {
                $('#' + file.id).find('.file-status').html('<p style="color:red;">上传出错</p>');
                //显示重传。显示删除
                $('#' + file.id).find('.file-manage').find('.demo-reload').removeClass("layui-hide");
                $('#' + file.id).find('.file-manage').find('.remove-this').removeClass("layui-hide");
                //隐藏暂停.隐藏不可操作
                $('#' + file.id).find('.file-manage').find('.stop-btn').addClass("layui-hide");
                $('#' + file.id).find('.file-manage').find('.layui-btn-disabled').addClass("layui-hide");
            });
            // 完成上传完后将视频添加到视频列表，显示状态为：转码中
            uploader.on('uploadComplete', function (file) {
                //$( '#'+file.id ).find('.file-progress').fadeOut();
            });
        }
    }
    //输出接口
    exports('webUploadJs', obj);
})
