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
        content="shoes store, products, free template, ecommerce, online shopping, website templates, CSS, HTML"/>
  <meta name="description" content="Shoes Store, Products, free shopping template provided "/>
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

    <%@include file="header.jsp" %>

    <div id="templatemo_main">
      <div id="sidebar" class="float_l">
        <%@include file="categorysidebar.jsp" %>
      </div>
      <div id="content" class="float_r">

        <span><h3 style="width:600px;heigth=40px;background: #EEEEEE; padding: 10px"> Products </h3></span>

        <c:forEach items="${requestScope.page.pageList }" var="product" varStatus="vs">
          <div class="${vs.index % 3 != 2?'product_box':'product_box no_margin_right' }">
            <a href="${pageContext.request.contextPath }/ProductServlet?op=findProductByPid&pid=${product.pid}">
              <img src="${product.imgurl }" width="200" height="150" alt="" title="${product.pname }"/>
            </a>
            <p>${product.pname }</p>
            <p class="product_price">￥ ${product.estoreprice }</p>
            <c:if test="${empty user }">
              <a href="javascript:login()" class="addtocart"></a>
            </c:if>
            <c:if test="${!empty user }">
              <a href="javascript:addCart(${product.pid },${user.uid})" class="addtocart"></a>
            </c:if>
            <a href="${pageContext.request.contextPath }/ProductServlet?op=findProductByPid&pid=${product.pid}"
               class="detail"></a>
          </div>
          <c:if test="${vs.index % 3 == 2}">
            <div class="cleaner"></div>
          </c:if>
        </c:forEach>

        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="33%">
              <div align="left">
                <span class="STYLE22">&nbsp;&nbsp;&nbsp;&nbsp;共有<strong>
                  ${page.totalRecordsNum }</strong> 条记录，当前第<strong>${page.currentPageNum }</strong> 页，共 <strong>${page.totalPageNum }</strong> 页</span>
              </div>
            </td>
            <td width="67%">
              <table width="312" border="0" align="right" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="49">
                    <div align="center">
                      <span class="STYLE22">
                        <a href="${pageContext.request.contextPath }/ProductServlet?op=${op}&sid=${sid}&keyword=${keyword}&num=1">首页</a>
                      </span>
                    </div>
                  </td>
                  <td width="49">
                    <div align="center">
                      <span class="STYLE22">
                        <a href="${pageContext.request.contextPath }/ProductServlet?op=${op}&sid=${sid}&keyword=${keyword}&num=${page.prevPageNum}">上一页</a>
                      </span>
                    </div>
                  </td>
                  <td width="49">
                    <span class="STYLE22">
                   <div align="center">
                        <span class="STYLE22">
                        <a href="${pageContext.request.contextPath }/ProductServlet?op=${op}&sid=${sid}&keyword=${keyword}&num=${page.nextPageNum}">下一页</a>
                        </span>
                    </div>
                    </span>
                  </td>
                  <td width="49">
                    <div align="center">
                      <span class="STYLE22"><a
                          href="${pageContext.request.contextPath }/ProductServlet?op=${op}&sid=${sid}&keyword=${keyword}&num=${page.totalPageNum }">尾页</a></span>
                    </div>
                  </td>
                  <td width="37" class="STYLE22">
                    <div align="center">转到</div>
                  </td>
                  <td width="22">
                    <div align="center">
                      <input type="text" name="num" id="num" value="${page.currentPageNum }"
                             style="width:20px; height:12px; font-size:12px; border:solid 1px #7aaebd;"/>
                    </div>
                  </td>
                  <td width="22" class="STYLE22">
                    <div align="center">页</div>
                  </td>
                  <td width="35">
                    <div align="center">
                      <span class="STYLE22"><a style="cursor:pointer;" onclick="jump()">跳转</a></span>
                    </div>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>

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
        window.location.href = "${pageContext.request.contextPath}/user/CartServlet?op=addCart&snum=1&pid=" + pid + "&uid=" + uid;
    }

    function jump() {
        var num = document.getElementById("num").value;
        if (!/^[1-9][0-9]*$/.test(num)) {
            alert("请输入正确的页码");
            return;
        }
        if (num > ${page.totalPageNum}) {
            alert("页码超出范围");
            return;
        }
        window.location.href = "${pageContext.request.contextPath }/ProductServlet?op=${op}&sid=${sid}&keyword=${keyword}&num="
            + num;

    }
</script>
</body>
</html>