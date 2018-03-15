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
      });

      $().ready(function () {
          $("#checkbox11").click(function () {
              if ($(this).attr("checked")) {
                  $(":checkbox").attr("checked", true);
              } else {
                  $(":checkbox").attr("checked", false);
              }
          })
      });

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
        <h4><img src="${pageContext.request.contextPath }/images/cart.gif"/>购物车</h4>
        <form action="">
          <table width="680px" cellspacing="0" cellpadding="5">
            <tr bgcolor="#ddd">
              <td width="4%" height="20" bgcolor="d3eaef" class="STYLE10">
                <div align="center">
                  <input type="checkbox" name="checkbox" id="checkbox11" onclick="checkAll(this.checked)"/>
                </div>
              </td>
              <th width="220" align="left">图片</th>
              <th width="180" align="left">描述</th>
              <th width="100" align="center">数量</th>
              <th width="60" align="right">价格</th>
              <th width="60" align="right">总价</th>
              <th width="90">删除</th>

            </tr>
            <c:if test="${!empty user }">
              <c:forEach items="${shoppingCart.shoppingItems}" var="item">
                <tr>
                  <td height="20" bgcolor="#FFFFFF">
                    <div align="center">
                      <input type="checkbox" name="itemid" id="itemid" value="${item.itemid}"/>
                    </div>
                  </td>
                  <td><img src="${pageContext.request.contextPath }/${item.product.imgurl }" style="width: 100px;height: 100px" alt=""/></td>
                  <td>${item.product.pname }</td>
                  <td align="center"><font style="width: 20px; text-align: right">${item.snum}</font> </td>
                  <td align="right">${item.product.estoreprice } </td>
                  <td align="right">${item.product.estoreprice*item.snum } </td>
                  <td align="center">
                    <a href="${pageContext.request.contextPath }/user/CartServlet?op=delItem&itemid=${item.itemid}">
                      <!-- <img src="images/remove_x.gif" alt="remove" /> <br />-->
                      Remove
                    </a>
                  </td>
                </tr>
              </c:forEach>
            </c:if>
          </table>
        </form>
        <div style="float:right; width: 255px; margin-top: 20px;">

          <c:if test="${!empty shoppingCart.shoppingItems}"><p><a
              href="${pageContext.request.contextPath}/user/placeOrder.jsp">立即购买</a></p>
          </c:if>
          <p><a href="${pageContext.request.contextPath}/index.jsp">继续购物</a></p>

        </div>
      </div>
      <div class="cleaner"></div>
    </div>

    <div id="templatemo_footer">
      Copyright (c) 2016 <a href="#">shoe商城</a> | <a href="#">版权所有</a>
    </div>

  </div>
</div>

</body>
</html>