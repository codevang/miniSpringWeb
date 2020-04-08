<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<% pageContext.setAttribute("viewName", "메인"); %>
<%@ include file="/WEB-INF/views/layout/top.jsp" %>


<s:authorize access="isAuthenticated()">
Password : ${user.password} <br>
이름 : ${user.userFullName}<br>



</s:authorize>

<%@ include file="/WEB-INF/views/layout/bottom.jsp" %>