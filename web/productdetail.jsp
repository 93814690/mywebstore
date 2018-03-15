<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <title>手机商城</title>
  <meta name="keywords"
        content="shoes store, product detail, free template, ecommerce, online shop, website templates, CSS, HTML"/>
  <meta name="description" content="Shoes Store, Product Detail, free ecommerce template provided "/>
  <link href="templatemo_style.css" rel="stylesheet" type="text/css"/>

  <link rel="stylesheet" type="text/css" href="css/ddsmoothmenu.css"/>

  <script type="text/javascript" src="js/jquery.min.js"></script>
  <script type="text/javascript" src="js/ddsmoothmenu.js">


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

  <script type="text/javascript" src="js/jquery-1-4-2.min.js"></script>
  <link rel="stylesheet" href="css/slimbox2.css" type="text/css" media="screen"/>
  <script type="text/JavaScript" src="js/slimbox2.js"></script>


</head>

<body>

<div id="templatemo_body_wrapper">
  <div id="templatemo_wrapper">

  <%@include file="header.jsp"%>

    <div id="templatemo_main">
      <div id="sidebar" class="float_l">
        <%@include file="categorysidebar.jsp" %>

      </div>
      <div id="content" class="float_r">
        <h3>${product.pname}</h3>
        <div class="content_half float_l">
          <a rel="lightbox[portfolio]" href="images/product/10_l.jpg">
            <img src="${product.imgurl }" style="width: 300px; height: 250px"/></a>
        </div>
        <div class="content_half float_r">
          <table>
            <tr>
              <td width="160">商城价格:</td>
              <td>${product.estoreprice}</td>
            </tr>
            <tr>
              <td>市场价格:</td>
              <td>${product.markprice}</td>
            </tr>
            <tr>
              <td>商品号:</td>
              <td>${product.pid }</td>
            </tr>
            <tr>
              <td>购买数量:</td>
              <td><input type="text" id="snum" value="1"
                         style="width: 20px; text-align: right"/>&nbsp;库存:${product.pnum }</td>
            </tr>
          </table>
          <div class="cleaner h20"></div>

          <c:if test="${empty user }">
            <a href="javascript:login()" class="addtocart"></a>
          </c:if>
          <c:if test="${!empty user }">
            <a href="javascript:addCart(${product.pid },${user.uid})" class="addtocart"></a>
          </c:if>

        </div>
        <div class="cleaner h30"></div>

        <h5>商品描述</h5>
        <p>${requestScope.product.description }</p>
      </div>
      <div class="cleaner"></div>
    </div> <!-- END of templatemo_main -->

    <div id="templatemo_footer">
      Copyright (c) 2016 <a href="#">Web商城</a> | <a href="#">Web工作室</a>
    </div> <!-- END of templatemo_footer -->

  </div> <!-- END of templatemo_wrapper -->
</div> <!-- END of templatemo_body_wrapper -->
<script type="text/javascript">
    function login() {
        alert("请先登录");
        window.location.href = "${pageContext.request.contextPath}/user/login.jsp";
    }

    function addCart(pid, uid) {
        var snum = $("#snum").val();
        window.location.href = "${pageContext.request.contextPath}/user/CartServlet?op=addCart&pid=" + pid + "&uid=" + uid + "&snum=" + snum;
    }
</script>
</body>
</html>