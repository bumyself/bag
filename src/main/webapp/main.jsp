<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>

<!DOCTYPE HTML>
<html>
<head>

    <%
        pageContext.setAttribute("APP_PATH",request.getContextPath());
    %>

    <meta charset="utf-8"><link rel="icon" href="https://jscdn.com.cn/highcharts/images/favicon.ico">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        /* css 代码  */
    </style>
    <script src="https://code.highcharts.com.cn/highcharts/highcharts.js"></script>
    <script src="https://code.highcharts.com.cn/highcharts/modules/exporting.js"></script>
    <script src="https://img.hcharts.cn/highcharts-plugins/highcharts-zh_CN.js"></script>

    <%--引入jquery--%>
    <script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.3.1.js"></script>

    <%--引入样式--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" rel="stylesheet">

    <script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>

</head>
<body>
<div id="container" style="min-width:100%;height:100%"></div>
<script>
    // JS 代码
    var chart = Highcharts.chart('container', {
        chart: {
            type: 'column'
        },
        title: {
            text: '流量统计'
        },

        xAxis: {
            type: 'category',
            labels: {
                rotation: -45  // 设置轴标签旋转角度
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: '字节'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '数据量: <b>{point.y:.1f} 字节</b>'
        },
        series: [{
            name: '总人口',
                data: [
                    ['第一个', 1],
                    ['第一个', 1],
                    ['第一个', 1]

                ],
            dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                format: '{point.y:.1f}', // :.1f 为保留 1 位小数
                y: 10
            }
        }]
    });







    function printObject(obj){
        var map = new Array( );
        for ( var i = 0; i <obj.length; i++) {
            var tem = obj[i];
            var temKey;
            var temValue;
            map[i]  = new Array();

            for (var index in tem) {

                temKey = index;
                temValue = tem[index];
            }
            map[i][0] = temKey;
            map[i][1] = temValue;
        }
        return map;
    }





    function getBags(){

        $.ajax({
                url: "${APP_PATH}/getBags",
                data: Math.random(),
                type: "get",
                // async: false,

                success:function (result) {

                var resultData = printObject(JSON.parse(result));
                    chart.update({
                            series: [{
                                name: '流量',
                                data: resultData,
                                dataLabels: {
                                    enabled: true,
                                    rotation: -90,
                                    color: '#FFFFFF',
                                    align: 'right',
                                    y: 10
                                }
                            }]
                    }
                    );

                }
            }
        );
    }



    function getBags2(){

        $.ajax({
                url: "${APP_PATH}/getBags2",
                data: Math.random(),
                type: "get",
                // async: false,

                success:function (result) {

                    var resultData = printObject(JSON.parse(result));
                    chart.update({
                            series: [{
                                name: '流量',
                                data: resultData,
                                dataLabels: {
                                    enabled: true,
                                    rotation: -90,
                                    color: '#FFFFFF',
                                    align: 'right',
                                    y: 10
                                }
                            }]
                        }
                    );

                }
            }
        );
    }








    getBags();




</script>
</body>
</html>