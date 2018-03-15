<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<div id="templatemo_header">
  <div id="site_title"><h1><a href="${pageContext.request.contextPath }">Online Phone Store</a>
  </h1></div>
  <div id="header_right">
    <p>
      <c:if test="${!empty user }">
        <a href="${pageContext.request.contextPath }/user/personal.jsp">我的个人中心</a> |
      </c:if>
      <a href="${pageContext.request.contextPath }/user/CartServlet?op=findCart">购物车</a> |
    <c:if test="${empty user }">
      <a href="${pageContext.request.contextPath }/user/login.jsp">登录</a> |
      <a href="${pageContext.request.contextPath }/user/register.jsp">注册</a></p>
    </c:if>
    <c:if test="${!empty user }">
      <a href="${pageContext.request.contextPath }/user/OrderServlet?op=myoid">我的订单</a> |
      ${user.nickname }
      <a href="${pageContext.request.contextPath }/UserServlet?op=logout">退出</a></p>
    </c:if>
  </div>
  <div class="cleaner"></div>
</div> <!-- END of templatemo_header -->

<div id="templatemo_menubar">
  <div id="top_nav" class="ddsmoothmenu">
    <ul>
      <li><a href="${pageContext.request.contextPath }/MainServlet" class="selected">主页</a></li>
    </ul>
    <br style="clear: left"/>
  </div> <!-- end of ddsmoothmenu -->
  <div id="templatemo_search">
    <form action="${pageContext.request.contextPath }/ProductServlet" method="get">
      <input type="hidden" name="op" value="findProByKeyword"/>
      <input type="hidden" name="num" value="1"/>
      <input type="text" value="${pname }" name="keyword" id="keyword" title="keyword" onfocus="clearText(this)"
             onblur="clearText(this)" class="txt_field"/>
      <input type="submit" name="Search" value=" " alt="Search" id="searchbutton" title="Search" class="sub_btn"/>
    </form>
  </div>
</div> <!-- END of templatemo_menubar -->
<div class="copyrights">Collect from <a href="#" title="Web商城">Web商城</a></div>
