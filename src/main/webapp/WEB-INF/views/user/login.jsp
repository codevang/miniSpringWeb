<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	pageContext.setAttribute("viewName", "로그인");
%>
<%@ include file="/WEB-INF/views/layout/top.jsp"%>

<s:authorize url="/admin" var="isTrue"> ${isTrue} </s:authorize>
​

<div class="row">
	<!-- 입력폼 그리드 -->
	<div class="col-lg-4">
		<form action="/loginAsk" method="post">
			<!-- ID입력 -->
			<div class="input-group mt-3 mb-1">
				<div class="input-group-prepend">
					<span class="input-group-text" id="basic-addon1">ID</span>
				</div>
				<input type="text" name="userId" class="form-control" placeholder="Input ID"
					aria-label="Input ID" aria-describedby="basic-addon1" required
				>
			</div>
			<!-- PW입력 -->
			<div class="input-group mb-2">
				<div class="input-group-prepend">
					<span class="input-group-text" id="basic-addon1">PW</span>
				</div>
				<input type="password" name="userPw" class="form-control"
					placeholder="Input Password" aria-label="Input Password"
					aria-describedby="basic-addon1" required
				>
			</div>
			<!-- 자동 로그인 -->
			<div class="custom-control custom-checkbox mb-1">
				<input type="checkbox" class="custom-control-input" name="remember-me" value="True"
					id="customCheck1"
				>
				<label class="custom-control-label" for="customCheck1">로그인 유지</label>
			</div>


			<!-- 로그인 버튼 -->
			<button type="submit" class="btn btn-dark btn-md btn-block">로그인 하기</button>
		</form>

		<!-- 회원가입 버튼 -->
		<button type="button" class="btn btn-dark btn-md btn-block my-1"
			onclick="location.href='/registerUserView'"
		>회원가입 하기</button>

		<!-- 로그인 실패 시 출력할 메세지 -->
		${requestScope.loginFailMsg}

		<!-- 그림 넣을 그리드 -->
		<div class="col-lg-8"></div>
	</div>
</div>



<%@ include file="/WEB-INF/views/layout/bottom.jsp"%>