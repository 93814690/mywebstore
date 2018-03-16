<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>添加分类</title>

  <style type="text/css">
    <!--
    body {
      margin-left: 3px;
      margin-top: 0px;
      margin-right: 3px;
      margin-bottom: 0px;
    }

    .STYLE1 {
      color: #e1e2e3;
      font-size: 12px;
    }

    .STYLE6 {
      color: #000000;
      font-size: 12px;
    }

    .STYLE10 {
      color: #000000;
      font-size: 12px;
    }

    .STYLE19 {
      color: #344b50;
      font-size: 12px;
    }

    .STYLE21 {
      font-size: 12px;
      color: #3b6375;
    }

    .STYLE22 {
      font-size: 12px;
      color: #295568;
    }

    -->
  </style>
  <script src="http://apps.bdimg.com/libs/jquery/1.11.3/jquery.min.js"></script>
  <script type="text/javascript">
      <%--function checkCategory() {--%>
          <%--var cname = document.getElementById("cname").value;--%>
<%--//          alert(cname);--%>
          <%--var request = new XMLHttpRequest();--%>
          <%--request.onreadystatechange = function () {--%>
              <%--if (request.readyState === 4) {--%>
                  <%--if (request.status === 200) {--%>
                      <%--document.getElementById("checkCname").innerHTML = request.responseText;--%>
                  <%--}--%>
              <%--}--%>
          <%--};--%>
          <%--request.open("POST", "${pageContext.request.contextPath }/admin/CategoryServlet");--%>
          <%--request.setRequestHeader("CONTENT-TYPE","application/x-www-form-urlencoded");--%>
          <%--request.send("op=checkCname&cname="+cname);--%>
      <%--}--%>

      //使用jQuery
//      $(document).ready(function () {
      //          checkCategory();
      //      });
      function checkCategory(){
          var cname = $('#cname').val();
          var url = "${pageContext.request.contextPath }/admin/CategoryServlet?op=checkCname&cname="+cname;
//          alert(url)
          $.get(url,function (str) {
              if (str == '1'){
                  $('#checkCname').html("该品牌已存在！").css("color","red");
              }
              if (str == '0'){
                  $('#checkCname').html("该品牌可以添加！").css("color","green");
              }
          });
      }
  </script>

</head>
<body>


<form method="post" action="${pageContext.request.contextPath }/admin/CategoryServlet">
  <input type="hidden" name="op" value="addCategory"/>

  <table width="100%" border="0" align="center" cellpadding="0"
         cellspacing="0">
    <tr>
      <td height="30">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="24" bgcolor="#353c44">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="6%" height="19" valign="bottom">
                          <div align="center">
                            <img src="images/tb.gif" width="14" height="14"/>
                          </div>
                        </td>
                        <td width="94%" valign="bottom"><span class="STYLE1">增加分类</span>
                        </td>
                      </tr>
                    </table>
                  </td>
                  <td>
                    <div align="right">
											<span class="STYLE1"> 
												<!-- <input type="button" value="添加"/> -->
                        <!-- <input type="submit" value="删除" /> -->
												&nbsp;&nbsp;
											</span>
                    </div>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td>

        <table width="100%" border="0" cellpadding="0"
               cellspacing="1" bgcolor="#a8c7ce">
          <tr>
            <td width="4%" height="20" bgcolor="d3eaef" class="STYLE10">
              <div align="center"></div>
            </td>
            <td width="10%" height="20" bgcolor="d3eaef" class="STYLE6">
              <div align="center">
                <span class="STYLE10"><span>品牌名：</span></span>
              </div>
            </td>
            <td width="80%" height="20" bgcolor="d3eaef" class="STYLE6">
              <div align="left">
                <span style="color:red">*</span>
                <input type="text" name="cname" id="cname" value="${cname}" onblur="checkCategory()" />
                <span id="checkCname" style="color:red"></span>
              </div>
            </td>

          </tr>

        </table>
      </td>
    </tr>

    <tr>
      <td height="30">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="33%">


            </td>
            <td width="67%">
              <div align="right">
                <input type="submit" value="增加"/>
              </div>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</form>

</body>
</html>