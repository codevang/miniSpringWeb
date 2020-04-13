<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String boardName = (String) request.getAttribute("boardViewName");
	pageContext.setAttribute("categoryName", "게시판 / " + boardName);
	pageContext.setAttribute("viewName", "게시물 작성");
%>

<%@ include file="/WEB-INF/views/layout/top.jsp"%>

<!-- 권한 없는 사람은 로그인 페이지로 리다이렉트 -->
<s:authorize access="isAnonymous()">
	<c:redirect url="/login" />
</s:authorize>

<!--  권한 있는 사용자만 화면 출력 -->
<s:authorize access="isAuthenticated()">
	<!-- 새글쓰기와 답글쓰기 구분 (form action 값으로 조정) -->
	<c:choose>
		<c:when test="${bId == 0}">
			<c:set var="formAction" value="/board/basicBoardWriterAskNew/${boardName}" />
		</c:when>
		<c:otherwise>
			<c:set var="formAction" value="/board/basicBoardWriterAskReply/${boardName}/${bId}" />
		</c:otherwise>
	</c:choose>

	<!-- 게시물 작성 화면 -->
	<div class="col-lg-9">
		<form action="${formAction}" method="post">
			<s:csrfInput />
			<input type="hidden" name="bUserId" value="${user.username}">
			<input type="hidden" name="bUsername" value="${user.userFullName}">
			<input type="text" name="bTitle" class="form-control mb-2" placeholder="제목을 입력해주세요." required>
			<div class="form-group">
				<textarea class="form-control" rows="10" name="bContent" placeholder="내용을 입력해주세요" required></textarea>
			</div>
			<button type="submit" class="btn btn-secondary">제출하기</button>
		</form>
	</div>

	<!-- Right Sidebar -->
	<div class="col-lg-3 border-left">
		사랑해도 헤어질 수 있다면<br> 헤어져도 사랑할 수 있잖아~~
	</div>
</s:authorize>

<%@ include file="/WEB-INF/views/layout/bottom.jsp"%>