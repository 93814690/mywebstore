<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!DOCTYPE html>
<%
  String referer = request.getHeader("Referer");
%>
<html>
<head>
  <title>创建一个免费的新帐户！</title>
  <meta charset="utf-8">
  <link href="${pageContext.request.contextPath }/user/css/style.css" rel='stylesheet' type='text/css'/>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!--webfonts-->
  <!--//webfonts-->
  <script src="http://apps.bdimg.com/libs/jquery/1.11.3/jquery.min.js"></script>
  <script type="text/javascript">
      function checkUsername() {
          <%--var username = document.getElementById("username").value;--%>
          <%--var request = new XMLHttpRequest();--%>
          <%--request.onreadystatechange = function () {--%>
              <%--if (request.readyState == 4) {--%>
                  <%--if (request.status == 200) {--%>
                      <%--document.getElementById("checkUsername").innerHTML = request.responseText;--%>
                  <%--}--%>
              <%--}--%>
          <%--};--%>
          <%--request.open("POST", "${pageContext.request.contextPath }/UserServlet");--%>
          <%--request.setRequestHeader("CONTENT-TYPE", "application/x-www-form-urlencoded");--%>
          <%--request.send("op=checkUsername&username=" + username);--%>

          var username = $('#username').val();
          var url = "${pageContext.request.contextPath}/UserServlet";
          $.post(url, {
              op: "checkUsername",
              username: username
          },function (str) {
              if (str === '1') {
                  $('#checkUsername').html("该用户名已存在！").css('color', 'red');
              }
              if (str === '0') {
                  $('#checkUsername').html("该用户名可以使用！").css('color', 'green');
              }
          });
      }

      function checkEmail() {
          var email = document.getElementById("email").value;
          var regex = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
          var check = regex.test(email);
          var msg = document.getElementById("checkEmail");
          if (!check) {
              msg.innerHTML = "email格式不正确";
          } else {
              msg.innerHTML = "";
          }
      }

      function checkBirthday() {
          var birthday = document.getElementById("birthday").value;
          var regex = /^((((19|20)\d{2})-(0?(1|[3-9])|1[012])-(0?[1-9]|[12]\d|30))|(((19|20)\d{2})-(0?[13578]|1[02])-31)|(((19|20)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))-0?2-29))$/;
          var check = regex.test(birthday);
          var msg = document.getElementById("checkBirthday");
          if (!check) {
              msg.innerHTML = "日期格式不正确";
          } else {
              msg.innerHTML = "";
          }
      }

  </script>
</head>

<body>
<div class="main" align="center">
  <div class="header">
    <h1>创建一个免费的新帐户！</h1>
  </div>
  <p></p>
  <form method="post" action="${pageContext.request.contextPath }/UserServlet">
    <input type="hidden" name="op" value="register"/>
    <input type="hidden" name="referer" value="<%=referer%>"/>
    <ul class="left-form">
      <li>
        ${msg.error.username }<span id="checkUsername"></span><br/>
        <input type="text" name="username" id="username" placeholder="用户名" onblur="checkUsername()"
               value="${msg.username}" required="required"/>
        <a href="#" class="icon ticker"> </a>
        <div class="clear"></div>
      </li>
      <li>
        ${msg.error.nickname }<br/>
        <input type="text" name="nickname" placeholder="昵称" value="${msg.nickname}" required="required"/>
        <a href="#" class="icon ticker"> </a>
        <div class="clear"></div>
      </li>
      <li>
        ${msg.error.email }<span id="checkEmail"></span><br/>
        <input type="text" name="email" id="email" placeholder="邮箱" value="${msg.email}" required="required"
               onblur="checkEmail()"/>
        <a href="#" class="icon ticker"> </a>
        <div class="clear"></div>
      </li>
      <li>
        ${msg.error.password }<br/>
        <input type="password" name="password" placeholder="密码" value="${msg.password}" required="required"/>
        <a href="#" class="icon into"> </a>
        <div class="clear"></div>
      </li>
      <li>
        ${msg.error.birthday }<span id="checkBirthday"></span><br/>
        <input type="text" placeholder="出生日期:1990-01-01" name="birthday" id="birthday" value="${msg.birthday}"
               onblur="checkBirthday()" size="15"/>
        <div class="clear"></div>
      </li>
      <li>
        ${msg.error.checkcode }<br/>
        <input type="text" placeholder="验证码" name="verifycode" value="" size="15" required="required"
               style="width: 80px"/>
        <img src="${pageContext.request.contextPath}/code.png" width="120" heigth="50">
        <div class="clear"></div>
      </li>
      <li>
        <input type="submit" value="创建账户">
        <div class="clear"></div>
      </li>
    </ul>

    <div class="clear"></div>

  </form>

</div>
<!-----start-copyright---->

<!-----//end-copyright---->

</body>

</html>