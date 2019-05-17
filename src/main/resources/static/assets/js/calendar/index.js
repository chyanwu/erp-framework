var addIndex;
var roomId;
var calendar;
var $ = layui.jquery;
var layer = layui.layer;
var form = layui.form;
var laydate = layui.laydate;

roomId = $("#room1").attr("roomId");
$("#room1").css("backgroundColor", "#003399");

document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    calendar = new FullCalendar.Calendar(calendarEl, {
        plugins: [ 'interaction', 'dayGrid', 'timeGrid' ],
        defaultView: 'dayGridMonth',
        // defaultDate: '2019-05-07',
        selectable: true, // dataClick生效
        locale: 'zh-cn', // 设置中文
        eventLimit: 4, // 显示更多
        displayEventEnd: true, // 显示结束时间
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        events: function(info, successCallback, failureCallback) {

            $.get("/erpcalendartask/getByParams", {roomId: roomId, sTime: info.start.valueOf(), eTime: info.end.valueOf()}, function (res) {
                if(res.code == 200) {
                    successCallback(res.data);
                }else {
                    failureCallback(res.errStr);
                }
            });
        }
        ,eventClick: function(info) {
            console.log('eventClick ' + info);
            openEditLayer(info);
        }
        ,dateClick: function(info) {
            console.log('dateClick ' + info.dateStr);
            openLayer(info);
        }
        ,eventRender: function(info) {
            var tooltip = new Tooltip(info.el, {
                title: popContent(info.event.title, info.event.extendedProps.appointPerson, info.event.start, info.event.end, info.event.extendedProps.tel),
                placement: 'top',
                trigger: 'hover',
                container: 'body',
                html: true
            });
        }
        // ,eventRender: function(info) {
        //     var tooltip = new Tooltip(info.el, {
        //         title: info.event.extendedProps.description,
        //         placement: 'top',
        //         trigger: 'hover',
        //         container: 'body'
        //     });
        // }
        // ,select: function(info) {
        //     console.log('selected ' + info.startStr + ' to ' + info.endStr);
        // }
        // ,eventMouseEnter: function(info) {
        //     console.log('eventMouseEnter ');
        // }
        // ,eventMouseLeave: function(info) {
        //     console.log('eventMouseLeave ');
        // }
    });

    calendar.render();
});

function popContent(title, appointPerson, stime, etime, tel) {
    var sDate =new Date(stime);
    var s = sDate.getHours() + '点' + sDate.getMinutes() + '分';
    var eDate =new Date(etime);
    var e = eDate.getHours() + '点' + eDate.getMinutes() + '分';
    var str = '<div style="font-weight: bold;">' + title + '</div>';
    str += '<div style="height: 1px;background-color: black;"></div>'
    str += '<div style="text-align: left;">预订人：' + appointPerson + '</div>';
    str += '<div style="text-align: left;">预订时间：' + s + ' -  ' + e + '</div>';
    str += '<div style="text-align: left;">预订电话：' + tel + '</div>'
    return str;
}

$(document).ready(function () {

});

function initDateCtrl() {
    //日期时间选择器
    laydate.render({
        elem: '#stime' //指定元素
        ,type: 'time'
        ,format: 'HH:mm'
        ,done: function(value, date){
            var etime = $("#etime").val();
            var ehour = parseInt(etime.split(":")[0]);
            var eminute = parseInt(etime.split(":")[1]);
            var hour = date.hours;
            var minute = date.minutes;
            if(ehour < hour) {
                $("#finishtime").val(value);
            } else {
                if(eminute < minute) {
                    $("#etime").val(value);
                }
            }
        }
    });

    //日期时间选择器
    laydate.render({
        elem: '#etime' //指定元素
        ,type: 'time'
        ,format: 'HH:mm'
        ,done: function(value, date){
            var stime = $("#stime").val();
            var shour = parseInt(stime.split(":")[0]);
            var sminute = parseInt(stime.split(":")[1]);
            var hour = date.hours;
            var minute = date.minutes;
            if(shour > hour) {
                $("#stime").val(value);
            } else {
                if(sminute > minute) {
                    $("#stime").val(value);
                }
            }
        }
    });
}

function openEditLayer(info){
    addIndex=layer.open({
        title : '<i class="fa fa-plus"></i>&nbsp;编辑日程',
        type : 1,
        fix : false,
        skin : 'layui-layer-rim',
        // 加上边框
        area : [ '470px', '450px' ],
        // 宽高
        content : $('#calendarForm').html(),
        success: function (layero, index) {
            initDateCtrl();
            $("#delEdit").show();

            var data = {};
            data.id = info.event.id;
            var sDate =new Date(info.event.start);
            data.stime = sDate.getHours() + ':' + sDate.getMinutes() ;
            var eDate =new Date(info.event.end);
            data.etime = eDate.getHours() + ':' + eDate.getMinutes() ;
            data.appointTheme = info.event.title;
            data.appointPerson = info.event.extendedProps.appointPerson;
            data.tel = info.event.extendedProps.tel;
            form.val('calendarForm', data);

            // 表单提交事件
            form.on('submit(formSubmit)', function (d) {
                var dataStr = sDate.getFullYear() + '-' + (sDate.getMonth() + 1) + '-' + sDate.getDate();
                d.field.roomId = roomId;
                d.field.stime = dataStr + " " + d.field.stime + ":00";
                d.field.etime = dataStr + " " + d.field.etime + ":00";
                $.ajax({
                    type: 'POST',
                    url: '/erpcalendartask/update',//发送请求
                    contentType: "application/json; charset=utf-8",
                    async: true,
                    data: JSON.stringify(d.field),
                    dataType: "json",
                    success: function (res) {
                        layer.closeAll("loading");
                        if(res.code == 200){
                            // layer.msg("添加成功！", {icon: 1});
                            calendar.refetchEvents();
                            layer.closeAll('page');
                        }else{
                            layer.msg(res.msg, {icon: 2});
                        }
                    }
                });

                return false;
            });
        }
    });
}

function openLayer(info){
    addIndex=layer.open({
        title : '<i class="fa fa-plus"></i>&nbsp;新增日程',
        type : 1,
        fix : false,
        skin : 'layui-layer-rim',
        // 加上边框
        area : [ '470px', '450px' ],
        // 宽高
        content : $('#calendarForm').html(),
        success: function (layero, index) {
            initDateCtrl();
            $("#delEdit").hide();
            // 表单提交事件
            form.on('submit(formSubmit)', function (d) {
                d.field.roomId = roomId;
                d.field.stime = info.dateStr + " " + d.field.stime + ":00";
                d.field.etime = info.dateStr + " " + d.field.etime + ":00";

                $.ajax({
                    type: 'POST',
                    url: '/erpcalendartask/create',//发送请求
                    contentType: "application/json; charset=utf-8",
                    async: true,
                    data: JSON.stringify(d.field),
                    dataType: "json",
                    success: function (res) {
                        layer.closeAll("loading");
                        if(res.code == 200){
                            // layer.msg("添加成功！", {icon: 1});
                            calendar.refetchEvents();
                            layer.closeAll('page');
                        }else{
                            layer.msg(res.msg, {icon: 2});
                        }
                    }
                });


                return false;
            });
        }
    });
}

function changeRoom(id){
    $(".external-event").each(function(i){
        if(this.id==id){
            $(this).css("backgroundColor", "#003399");
            roomId = $(this).attr("roomId");
        }else{
            $(this).css("backgroundColor", "#3366CC");
        }
    });
    calendar.refetchEvents();
}

function del(){
    var id=$("#id").val();
    layer.confirm('确定要删除吗？', {
        offset: '65px',
        title: '提示'
    }, function (i) {
        layer.close(i);
        layer.load(2);
        $.post("/erpcalendartask/deleteByID", {id: id}, function (res){
            layer.closeAll('loading');
            if(res.code == 200){
                // layer.msg("删除成功");
                calendar.refetchEvents();
                layer.closeAll('page');
            }else{
                layer.msg(res.message);
            }
        });
    });
}

// 所有ew-event
$('body').on('click', '*[ew-event]', function () {
    var event = $(this).attr('ew-event');
    if (event == 'closeDialog') {
        var id = $(this).parents('.layui-layer').attr('id').substring(11);
        layer.close(id);
    }
});
