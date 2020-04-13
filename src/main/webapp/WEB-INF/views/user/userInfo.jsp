<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	pageContext.setAttribute("categoryName", "Home");
	pageContext.setAttribute("viewName", "Main");
%>

<%@ include file="/WEB-INF/views/layout/top.jsp"%>

<div class="col-lg-9">

	<s:authorize access="isAuthenticated()">

		<c:choose>
			<c:when test="${userInfo != null}">

				ID : ${userInfo.userId}<br>
				이름 : ${userInfo.userName}<br>
				전화번호 : ${userInfo.userPhone}<br>
				Email : ${userInfo.userEmail}<br>
				가입일시 : ${userInfo.userInitTime}<br>
		
			</c:when>
		</c:choose>
	</s:authorize>
</div>

<%@ include file="/WEB-INF/views/layout/bottom.jsp"%>