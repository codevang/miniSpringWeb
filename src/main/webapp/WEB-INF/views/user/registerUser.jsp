<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	pageContext.setAttribute("viewName", "회원가입");
%>
<%@ include file="/WEB-INF/views/layout/top.jsp"%>



<!-- URL로 직접 접속할 경우 로그되지 않은 사용자만 가입폼을 보여주도록 함 -->
<c:choose>
	<c:when test="${sessionScope.userSession == null}">

		<div class="row my-5">
			<div class="col-lg-3"></div>
			<div class="col-lg-6">

				<form action="/registerAsk" method="post">
					<!-- ID 입력 -->
					<div class="input-group mt-3 mb-1">
						<div class="input-group-prepend">
							<span class="input-group-text" id="basic-addon1">ID</span>
						</div>
						<input type="text" name="userId" class="form-control" placeholder="Input ID"
							aria-label="Input ID" aria-describedby="basic-addon1" required
						>
					</div>
					<!-- PW입력 -->
					<div class="input-group mb-1">
						<div class="input-group-prepend">
							<span class="input-group-text" id="basic-addon1">PW</span>
						</div>
						<input type="password" name="userPw" class="form-control"
							placeholder="Input Password" aria-label="Input Password"
							aria-describedby="basic-addon1" required
						>
					</div>
					<!-- PW 확인 -->
					<div class="input-group mb-2">
						<div class="input-group-prepend">
							<span class="input-group-text" id="basic-addon1">PW확인</span>
						</div>
						<input type="password" name="userPw2" class="form-control"
							placeholder="Confirm Password" aria-label="Confirm Password"
							aria-describedby="basic-addon1" required
						>
					</div>

					<!-- 이름 입력 -->
					<div class="input-group mb-2">
						<div class="input-group-prepend">
							<span class="input-group-text" id="basic-addon1">이름</span>
						</div>
						<input type="text" name="userName" class="form-control"
							placeholder="Input Name" aria-label="Input Name"
							aria-describedby="basic-addon1" required
						>
					</div>

					<!-- 전화번호 입력 -->
					<div class="input-group mb-2">
						<div class="input-group-prepend">
							<span class="input-group-text" id="basic-addon1">전화번호</span>
						</div>
						<input type="text" name="userPhone" class="form-control"
							placeholder="Input PhoneNumber" aria-label="Input PhoneNumber"
							aria-describedby="basic-addon1" required
						>
					</div>

					<!-- 이메일 입력 -->
					<div class="input-group mb-2">
						<div class="input-group-prepend">
							<span class="input-group-text" id="basic-addon1">이메일</span>
						</div>
						<input type="text" name="userEmail" class="form-control"
							placeholder="Input Email" aria-label="Input Email"
							aria-describedby="basic-addon1" required
						>
					</div>

					<!-- 가입 버튼 -->
					<button type="submit" class="btn btn-dark btn-lg btn-block mb-3">가입 하기</button>
				</form>

				<!-- 메세지 있을 경우 출력 (이미 존재하는 ID 등) -->
				${registerUserMsg}

			</div>
			<div class="col-lg-3"></div>
		</div>
	</c:when>

	<c:otherwise>
		<c:redirect url="/" />
	</c:otherwise>
</c:choose>




<%@ include file="/WEB-INF/views/layout/bottom.jsp"%>