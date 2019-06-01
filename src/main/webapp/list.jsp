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


    <%--引入jquery--%>
    <script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.3.1.js"></script>

    <%--引入样式--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" rel="stylesheet">

    <script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>

</head>
<body>
<%--<div id="container" style="min-width:100%;height:100%"></div>--%>

<div id="modal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">


            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">网卡信息</h4>
            </div>



            <div class="modal-body">
                <p id="networkCark"></p>


                <div class="input-group">
                    <span class="input-group-addon">输入你想监听网卡的编号</span>
                    <input id="index" type="text" class="form-control"   aria-describedby="basic-addon3">
                </div>
                <br>
                <div class="input-group">
                    <span class="input-group-addon" time> &nbsp &nbsp &nbsp &nbsp 输入监听时长 &nbsp &nbsp  &nbsp &nbsp &nbsp</span>
                    <input id="time" type="text" class="form-control"   aria-describedby="basic-addon3">
                </div>

            </div>




            <div class="modal-footer">
                <button id="save_btn" type="button" class="btn btn-primary" >确定</button>
            </div>


        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<div class = "col-md-12">
    <table id="data_table" class="table table-condensed">
        <tr>
            <th>ip A</th>
            <th>ip B</th>
            <th>流量（字节）</th>
        </tr>
    </table>

</div>



<script>













    function getDatas(index, time){
        $.ajax({
            url: "${APP_PATH}/tblJ",
            data: "index=" + index + "&time=" + time,
            type: "get",
            async: false,
            success:function (result) {

                filling(result.extend.datas);


            }
        });
    }

    function filling(data){
        $.each(data, function (index, item) {
            var srcIp = $("<td></td>").append(item.srcIp);
            var dtcIp = $("<td></td>").append(item.dtcIp);
            var flow =  $("<td></td>").append(item.data);
            $("<tr></tr>")
                .append(srcIp)
                .append(dtcIp)
                .append(flow)
                .appendTo("#data_table");
        });
    }





    $("#save_btn").click(function () {
        var index = $("#index").val();
        var time = $("#time").val();
        $("#modal").modal('hide');
        getDatas(index, time);

    });


    function getDev(){
        $.ajax({
            url: "${APP_PATH}/getD",
            async: false,
            success:function (result) {
                $("#networkCark").append(result);
            }
        });
    }

    $(function () {

        getDev();

        $('#modal').modal({
            backdrop:"static"
        });
    });















</script>
</body>
</html>