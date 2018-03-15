<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>

<div class="sidebar_box"><span class="bottom"></span>
  <h3>品牌</h3>
  <div class="content">
    <ul class="sidebar_list">
      <c:forEach items="${allCategory }" var="category" varStatus="vs">
        <c:if test="${vs.index !=0}">
          <c:if test="${vs.index != fn:length(allCategory)-1 }">
            <li>
              <a href="${pageContext.request.contextPath }/ProductServlet?op=byCid&num=1&cid=${category.cid}">${category.cname}</a>
            </li>
          </c:if>
        </c:if>
        <c:if test="${vs.index==0 }">
          <li class="first">
            <a href="${pageContext.request.contextPath }/ProductServlet?op=byCid&num=1&cid=${category.cid}">${category.cname}</a>
          </li>
        </c:if>
        <c:if test="${vs.index == fn:length(allCategory)-1 }">
          <li class="last">
            <a href="${pageContext.request.contextPath }/ProductServlet?op=byCid&num=1&cid=${category.cid}">${category.cname}</a>
          </li>
        </c:if>
      </c:forEach>
    </ul>
  </div>
</div>