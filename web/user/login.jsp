<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
  String referer = request.getHeader("Referer");
%>
<%
  Cookie[] cookies = request.getCookies();
  String username = "";
  String password = "";
  if (cookies != null) {
    for (Cookie cookie : cookies) {
      if ("username".equals(cookie.getName())) {
        username = cookie.getValue();
      }
      if ("password".equals(cookie.getName())) {
        password = cookie.getValue();
      }
    }
  }
%>
<html lang="en">
<head>
  <base href="<%=basePath%>">
  <meta charset="utf-8">

  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>Login Form</title>
  <link rel="stylesheet" href="user/css/style.css">
  <!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
</head>
<body>

  <section class="container">
    <div class="login">
      <h1>用户登录 <span style="text-align:center;padding-top:2px;"><font color="#ff0000">${requestScope["msg"]}</font>
														</span></h1>
      <form method="post" action="${pageContext.request.contextPath }/UserServlet">
        <input type="hidden" name="referer" value="<%=referer%>"/>
      	<input type="hidden" name="op" value="login"/>
        <p><input type="text" name="username" value="<%=username%>" placeholder="用户名"></p>
        <p><input type="password" name="password" value="<%=password%>" placeholder="密码"></p>
        <p class="remember_me">
          <label>
            <input type="checkbox" name="remember_me" id="remember_me">
            记住密码
          </label>
        </p>
        <p class="submit"><input type="submit" name="commit" value="登录"></p>
      </form>
    </div>
  </section>

</body>
</html>
