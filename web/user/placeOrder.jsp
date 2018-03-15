<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <base href="<%=basePath%>">
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


  <%@include file="../header.jsp"%>
    <div id="templatemo_main">


      <div id="sidebar" class="float_l">
        <%@include file="../categorysidebar.jsp" %>

      </div>
      <div id="content" class="float_r">


        <h3>确认订单</h3>
        <form action="${pageContext.request.contextPath }/user/OrderServlet" method="post">
          <%--从session中拿user的uid--%>
          <%--<input type="hidden" name="uid" value="${user.uid }">--%>
          <input type="hidden" name="op" value="placeOrder"/>
          <div>
            <table style="width: 700px;border-style:1px solid " border="1">
              <tr>
                <td>收件人:</td>
                <td><input type="text" name="recipients" required="required"/></td>
              </tr>
              <tr>
                <td>电话:</td>
                <td><input type="text" name="tel" required="required"></td>
              </tr>
              <tr>
                <td>收件人地址:</td>
                <td><input type="text" name="address" size="80" required="required"/></td>
              </tr>
            </table>
          </div>
          <div style="padding-top: 30px;">
            <table style="width: 700px;border-style:1px solid " border="1">
              <tr>
                <th><input type="checkbox" id="cb" checked></th>
                <th>图片</th>
                <th>描述</th>
                <th>数量</th>
                <th>单价</th>
                <th>总价</th>
              </tr>
              <c:set var="sum" value="0"> </c:set>
              <c:forEach items="${shoppingCart.shoppingItems}" var="item">
                <tr id="tr${item.product.pid}">
                  <td><input type="checkbox" class="pids" name="pids" value="${item.product.pid}" checked ></td>
                  <td><img src="${pageContext.request.contextPath }/${item.product.imgurl}" width="100" height="100"/></td>
                  <td>${item.product.pname}</td>
                  <td>${item.snum}</td>
                  <td>${item.product.estoreprice}</td>
                  <td id="price">${item.snum*item.product.estoreprice}</td>
                </tr>
                <c:set var="sum" value="${sum+item.snum*item.product.estoreprice}"> </c:set>
              </c:forEach>
            </table>
          </div>
          <input type="hidden" id="qq" name="money" value="${sum}"/>

          <h4>总金额:<span class="sumPrice"> ${sum}</span>元</h4>
          <input type="submit" value="去下单" style="font-size: 18px;" align="right">
        </form>


        <%--   <div style="float:right; width: 215px; margin-top: 20px;">

          <c:if test="${!empty shoppingCar.shoppingItems}"><p><a href="${pageContext.request.contextPath }/placeOrder.jsp">去下单</a></p></c:if>
          <p><a href="${pageContext.request.contextPath}/index.jsp">继续购物</a></p>

          </div> --%>
      </div>
      <div class="cleaner"></div>
    </div>

    <div id="templatemo_footer">
      Copyright (c) 2016 <a href="#">shoe商城</a> | <a href="#">版权所有</a>
    </div>

  </div>
</div>

<script type="text/javascript">

    $(function () {
        $(".pids").click(function () {
            var price = 0;
            $(".pids:checked").each(function (i, obj) {
                var id = $(obj).val();
                //console.log(id);
                //console.log($("#tr"+id).find("#price").text());
                price += parseInt($("#tr" + id).find("#price").text());
            })
            $(".sumPrice").text(price);
            $("#qq").val(price);
        })

        $("#cb").click(function () {
            var price = 0;
            if ($(this).attr("checked")) {

                $(":checkbox").attr("checked", true);
                $(".pids:checked").each(function (i, obj) {
                    var id = $(obj).val();

                    price += parseInt($("#tr" + id).find("#price").text());
                })
                $(".sumPrice").html(price);
                $("#qq").val(price);
                //alert("price");
                //console.log($("#money").val);
            } else {
                $(":checkbox").attr("checked", false);
                $(".sumPrice").html(0);
            }
        })
    })


</script>
</body>
</html>