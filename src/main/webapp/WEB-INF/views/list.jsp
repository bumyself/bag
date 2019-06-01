<%--
  Created by IntelliJ IDEA.
  User: 11955
  Date: 19.5.11
  Time: 下午 4:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>
<html>

<head>

    <title>员工列表</title>
    <%
        pageContext.setAttribute("APP_PATH",request.getContextPath());
    %>

    <%--引入jquery--%>
    <script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.3.1.js"></script>

    <%--引入样式--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" rel="stylesheet">

    <script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>


</head>
<body>
    <div class="container">
        <%--标题行--%>
        <div class="row">
            <div class = "col-md-12">
                <h1>SSM-CRUD</h1>
            </div>
        </div>
        <%--按钮--%>
        <div class="row">
            <div class = "col-md-4 col-md-offset-8">
                <button class="btn btn-primary">新增</button>
                <button class="btn btn-danger">删除</button>
            </div>

        </div>
            <br/>
        <%--显示表格数据--%>
        <div class="row">
            <div class = "col-md-12">
                <table class="table table-hover" >

                    <tr>
                        <th>#</th>
                        <th>empName</th>
                        <th>gender</th>
                        <th>email</th>
                        <th>deptName</th>
                        <th>操作</th>
                    </tr>
                    <c:forEach items="${pageInfo.list}" var="emp">
                        <tr>
                            <td>${emp.empId}</td>
                            <td>${emp.empName}</td>
                            <td>${emp.gender}</td>
                            <td>${emp.email}</td>
                            <td>${emp.tblDept.deptName}</td>
                            <th>
                                <button class="btn btn-primary btn-sm">
                                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                    编辑
                                </button>
                                <button class="btn btn-primary btn-sm">
                                    <span class=" glyphicon glyphicon-remove" aria-hidden="true"></span>
                                    删除
                                </button>
                            </th>
                    </c:forEach>
                        </tr>
                </table>
            </div>
        </div>
        <%--显示分页信息--%>
        <div class="row">

             <div class="col-md-6">
                 <div>当前页数： ${pageInfo.pageNum},  总页数: ${pageInfo.pages} 总记录数 : ${pageInfo.total}</div>
             </div>

            <div class="col-md-6">
                <nav aria-label="Page navigation">
                    <ul class="pagination">

                        <c:if test="${pageInfo.pageNum > 0}">
                            <li>
                                <a href=${APP_PATH}?pn=0>首页</a>
                            </li>
                        </c:if>



                            <li>
                                <c:if test="${pageInfo.hasPreviousPage}">
                                    <a href=${APP_PATH}?pn=${pageInfo.pageNum-1} aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </c:if>
                            </li>


                            <c:forEach items="${pageInfo.navigatepageNums}" var="page_num">
                                <c:if test="${page_num == pageInfo.pageNum}">
                                  <li  class ="active"> <a href="#">${page_num}</a></li>
                                </c:if>

                                <c:if test="${page_num != pageInfo.pageNum}">
                                    <li> <a href=${APP_PATH}?pn=${page_num}>${page_num}</a></li>
                                </c:if>
                            </c:forEach>
                        <li>
                            <c:if test="${pageInfo.hasNextPage}">
                                <a href=${APP_PATH}?pn=${pageInfo.pageNum+1} aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </c:if>
                        </li>

                        <c:if test="${pageInfo.pageNum != pageInfo.total }">
                            <li>
                                <a href=${APP_PATH}?pn=${pageInfo.pages}>末页</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</body>
</html>
