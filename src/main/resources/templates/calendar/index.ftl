<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='utf-8' />
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">

    <link rel="stylesheet" href='/fullcalendar/core/main.css' rel='stylesheet' />
    <link rel="stylesheet" href='/fullcalendar/daygrid/main.css' rel='stylesheet' />
    <link rel="stylesheet" href='/fullcalendar/timegrid/main.css' rel='stylesheet' />
    <link rel="stylesheet" href="/assets/libs/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/assets/common.css" media="all">

    <style>
        #calendar {
            max-width: 1200px;
            margin: 0 auto;
        }

        #external-events {
            float: left;
            width: 150px;
            padding: 0 10px;
            border: 1px solid #ccc;
            background: #eee;
            text-align: left;
        }

        #external-events h4 span {
            font-size: 16px;
            margin-top: 0;
            padding-top: 1em;
        }

        .external-event { /* try to mimick the look of a real event */
            margin: 10px 0;
            padding: 2px 4px;
            background: #3366CC;
            color: #fff;
            font-size: .85em;
            cursor: pointer;
        }

        /*日期控件*/
        .layui-laydate-content>.layui-laydate-list {
            padding-bottom: 0px;
            overflow: hidden;
        }
        .layui-laydate-content>.layui-laydate-list>li{
            width:50%
        }

        /* pop */
        .popper,
        .tooltip {
            position: absolute;
            z-index: 9999;
            background: #FFC107;
            color: black;
            width: 150px;
            border-radius: 3px;
            box-shadow: 0 0 2px rgba(0,0,0,0.5);
            padding: 10px;
            text-align: center;
        }
        .style5 .tooltip {
            background: #1E252B;
            color: #FFFFFF;
            max-width: 200px;
            width: auto;
            font-size: .8rem;
            padding: .5em 1em;
        }
        .popper .popper__arrow,
        .tooltip .tooltip-arrow {
            width: 0;
            height: 0;
            border-style: solid;
            position: absolute;
            margin: 5px;
        }

        .tooltip .tooltip-arrow,
        .popper .popper__arrow {
            border-color: #FFC107;
        }
        .style5 .tooltip .tooltip-arrow {
            border-color: #1E252B;
        }
        .popper[x-placement^="top"],
        .tooltip[x-placement^="top"] {
            margin-bottom: 5px;
        }
        .popper[x-placement^="top"] .popper__arrow,
        .tooltip[x-placement^="top"] .tooltip-arrow {
            border-width: 5px 5px 0 5px;
            border-left-color: transparent;
            border-right-color: transparent;
            border-bottom-color: transparent;
            bottom: -5px;
            left: calc(50% - 5px);
            margin-top: 0;
            margin-bottom: 0;
        }
        .popper[x-placement^="bottom"],
        .tooltip[x-placement^="bottom"] {
            margin-top: 5px;
        }
        .tooltip[x-placement^="bottom"] .tooltip-arrow,
        .popper[x-placement^="bottom"] .popper__arrow {
            border-width: 0 5px 5px 5px;
            border-left-color: transparent;
            border-right-color: transparent;
            border-top-color: transparent;
            top: -5px;
            left: calc(50% - 5px);
            margin-top: 0;
            margin-bottom: 0;
        }
        .tooltip[x-placement^="right"],
        .popper[x-placement^="right"] {
            margin-left: 5px;
        }
        .popper[x-placement^="right"] .popper__arrow,
        .tooltip[x-placement^="right"] .tooltip-arrow {
            border-width: 5px 5px 5px 0;
            border-left-color: transparent;
            border-top-color: transparent;
            border-bottom-color: transparent;
            left: -5px;
            top: calc(50% - 5px);
            margin-left: 0;
            margin-right: 0;
        }
        .popper[x-placement^="left"],
        .tooltip[x-placement^="left"] {
            margin-right: 5px;
        }
        .popper[x-placement^="left"] .popper__arrow,
        .tooltip[x-placement^="left"] .tooltip-arrow {
            border-width: 5px 0 5px 5px;
            border-top-color: transparent;
            border-right-color: transparent;
            border-bottom-color: transparent;
            right: -5px;
            top: calc(50% - 5px);
            margin-left: 0;
            margin-right: 0;
        }
    </style>
</head>
<body>

<div class="layui-fluid">
    <div class="layui-card" style="padding-top: 5px; margin-bottom: 8px;">
        <div class="layui-row layui-col-space15">
            <div class="layui-col-xs12 layui-col-md2">
                <div id='external-events'>
                    <h4>会议室</h4>
                    <#list roomList as room>
                        <div class="external-event" id="room${room.id}" roomId="${room.id}" onclick="changeRoom(this.id)">${room.name}</div>
                    </#list>
                    <#--<div id="1" roomId="1" onclick="changeRoom(this.id)">会议室1</div>-->
                    <#--<div id="2" roomId="2" onclick="changeRoom(this.id)">会议室2</div>-->
                    <#--<div id="3" roomId="3" onclick="changeRoom(this.id)">会议室3</div>-->
                    <#--<div id="4" roomId="4" onclick="changeRoom(this.id)">会议室4</div>-->
                </div>
            </div>
            <div class="layui-col-xs12 layui-col-md10">
                <div id='calendar'></div>
            </div>
        </div>
    </div>
</div>


<!-- 表单弹框 -->
<script  type="text/html" id="calendarForm">
    <form lay-filter="calendarForm" class="layui-form model-form">
        <input name="id" id="id" type="hidden"/>
        <div class="layui-form-item">
            <label class="layui-form-label">预约主题</label>
            <div class="layui-input-block">
                <input name="appointTheme" placeholder="请输入会议主题" type="text" class="layui-input" maxlength="50" lay-verify="required" required/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">预约开始时间</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" id="stime" name="stime" placeholder="HH:mm" lay-verify="required" required>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">预约结束时间</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" id="etime" name="etime" placeholder="HH:mm" lay-verify="required" required>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">预约人</label>
            <div class="layui-input-block">

                <input name="appointPerson" value="<#if currentUser.name!=''>${currentUser.name}<#else>${currentUser.loginName}</#if>" type="text" class="layui-input" maxlength="50" readonly />
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">联系方式</label>
            <div class="layui-input-block">
                <input name="tel" value="<#if currentUser.phone??>${currentUser.phone}</#if>" type="text" class="layui-input" maxlength="20" readonly />
            </div>
        </div>
        <div class="layui-form-item text-right">
            <button class="layui-btn layui-btn-primary" type="button" ew-event="closeDialog">取消</button>
            <button class="layui-btn" lay-filter="formSubmit" lay-submit>保存</button>
            <button class="layui-btn" type="button" id="delEdit" onclick="del()">删除</button>
        </div>
     </form>
</script>

<!-- js部分 -->
<script src='/fullcalendar/core/main.js'></script>
<script src='/fullcalendar/core/locales-all.js'></script>
<script src='/fullcalendar/daygrid/main.js'></script>
<script src='/fullcalendar/interaction/main.js'></script>
<script src='/fullcalendar/timegrid/main.js'></script>
<script src='/fullcalendar/core/locales/zh-cn.js'></script>
<script src="/assets/libs/jquery/jquery-3.2.1.min.js"></script>
<script src='/fullcalendar/popper.min.js'></script>
<script src='/fullcalendar/tooltip.min.js'></script>
<script type="text/javascript" src="/assets/libs/layui/layui.all.js"></script>
<script type="text/javascript" src="/assets/js/calendar/index.js"></script>
</body>
</html>