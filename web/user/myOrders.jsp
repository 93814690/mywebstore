<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <title>Shoes Store - Shopping Cart</title>
  <meta name="keywords"
        content="shoes store, shopping cart, free template, ecommerce, online shop, website templates, CSS, HTML"/>
  <meta name="description" content="Shoes Store, Shopping Cart, online store template "/>
  <link href="../templatemo_style.css" rel="stylesheet" type="text/css"/>

  <link rel="stylesheet" type="text/css" href="../css/ddsmoothmenu.css"/>

  <script type="text/javascript" src="../js/jquery.min.js"></script>
  <script type="text/javascript" src="../js/ddsmoothmenu.js">


  </script>

  <script type="text/javascript">

      ddsmoothmenu.init({
          mainmenuid: "top_nav", //menu DIV id
          orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
          classname: 'ddsmoothmenu', //class added to menu's outer DIV
          //customtheme: ["#1c5a80", "#18374a"],
          contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
      })

  </script>

</head>

<body>

<div id="templatemo_body_wrapper">
  <div id="templatemo_wrapper">


  <%@include file="../header.jsp"%>

    <div id="templatemo_main">
      <div id="sidebar" class="float_l">
        <%@include file="../categorysidebar.jsp" %>

      </div>
      <div id="content" class="float_r">

        <h4>我所有的订单</h4>
        <table border="1" width="700" style="border-style: inherit; border-color: black;">

          <tr style="color: grey">
            <td>订单号</td>
            <td>下单时间</td>
            <td>订单总金额</td>
            <td>订单状态</td>
            <td>操作</td>
          </tr>
          <c:forEach items="${orders }" var="order" varStatus="status">
            <%-- -1表示已删除 --%>
            <c:if test="${order.state != -1}">
              <tr style="color: grey">
                <td>${status.count}</td>
                <td>${order.ordertime}</td>
                <td>${order.money }</td>
                <td>
                  <c:if test="${order.state == 0}">订单已取消</c:if>
                  <c:if test="${order.state == 1}">已下单</c:if>
                  <c:if test="${order.state == 2}">已支付</c:if>
                  <c:if test="${order.state == 3}">已发货</c:if>
                  <c:if test="${order.state == 4}">已收货</c:if>
                </td>
                <td>
                  <c:if test="${order.state==1}">
                    <%-- 如果直接传state则用户可以直接修改订单状态为已支付 --%>
                    <a href="${pageContext.request.contextPath}/user/OrderServlet?op=cancelOrder&oid=${order.oid}">取消订单</a> |
                    <a href="${pageContext.request.contextPath}/pay.jsp?oid=${order.oid}">支付订单</a>

                  </c:if>

                  <c:if test="${order.state==0 || order.state==4}">
                    <a href="${pageContext.request.contextPath}/user/OrderServlet?op=deleteOrder&oid=${order.oid}">删除订单</a>

                  </c:if>
                </td>
              </tr>
            </c:if>
          </c:forEach>
        </table>


        <p><a href="${pageContext.request.contextPath}/index.jsp">继续购物</a></p>


      </div>
      <div class="cleaner"></div>
    </div>

    <div id="templatemo_footer">
      Copyright (c) 2016 <a href="#">Web商城</a> | <a href="#">Web工作室</a>
    </div>

  </div>
</div>

</body>
</html>