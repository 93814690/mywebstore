<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <base href="<%=basePath%>"/>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <title>Shoes Store - Shopping Cart</title>
  <meta name="keywords"
        content="shoes store, shopping cart, free template, ecommerce, online shop, website templates, CSS, HTML"/>
  <meta name="description" content="Shoes Store, Shopping Cart, online store template "/>
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

</head>

<body>

<div id="templatemo_body_wrapper">
  <div id="templatemo_wrapper">

    <%@include file="../header.jsp" %>

    <div id="templatemo_main">
      <div id="sidebar" class="float_l">
        <%@include file="../categorysidebar.jsp" %>

      </div>
      <div id="content" class="float_r">

        <h3>个人资料</h3>
        <hr/>
        <br/>
        <!--
             （仅限修改昵称，密码，邮箱和出生日期）
             nickname
             password
             email
             birthday
         -->
        <form action="${pageContext.request.contextPath }/UserServlet" method="post">
          <input type="hidden" name="op" value="update"/>
          <input type="hidden" name="uid" value="${user.uid }"/>
          用&nbsp;户&nbsp;名： ${user.username }
          <br/>${msg.error.nickname } <br/>
          昵&nbsp;&nbsp;&nbsp;&nbsp;称：<input type="text" name="nickname" value="${user.nickname }">
          <br/>${msg.error.password }<br/>
          密&nbsp;&nbsp;&nbsp;&nbsp;码：<input type="password" name="password" value="${user.password }"/>
          <br/>${msg.error.email }<br/>
          邮&nbsp;&nbsp;&nbsp;&nbsp;箱：<input type="text" name="email" value="${user.email }"/>
          <br/> ${msg.error.birthday }<br/>
          出生日期：<input type="text" name="birthday" value="${user.birthday}"/>
          <br/><br/>
          <%--头像： ${user.headicon}--%>
          <%--<br/><br/>--%>
           注册时间： ${user.updatetime }
          <br/><br/>
          <%-- <span>请准确填写您的信息，确保货物准确到达</span>
          <br /><br />
          详细地址 <input type="text" style="width: 400px" name="address" value="${address }"><span>*</span>
          <br /><br />
          联系电话 <input type="text" name="tel" value="${tel }"><span>*</span>
          <br /><br />
           联系QQ&nbsp;&nbsp;&nbsp;<input type="text" name="qq" value="${qq }">
           <br /><br /> --%>
          <input type="submit" value="保存填写" id="submit">
          <input type="reset" value="撤销重写" id="reset">
        </form>


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