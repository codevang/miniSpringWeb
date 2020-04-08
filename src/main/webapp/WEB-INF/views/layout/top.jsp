<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="jdk1.8/java8/Spring3/Tomcat9.0/Mabatis3.2.8">
<meta name="author" content="Codevang">

<title>Welcome to codevang's web</title>

<!-- 부트스트랩 CSS -->
<link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<!-- 커스텀 CSS -->
<link href="/resources/css/simple-sidebar.css" rel="stylesheet">
</head>


<body>

	<div class="d-flex" id="wrapper">

		<!-- 인증 사용자 정보 -->
		<s:authentication property="principal" var="user" />

		<!-- 사이드바 -->
		<div class="bg-light border-right" id="sidebar-wrapper">
			<div class="sidebar-heading">
				<span class="red-line-menu">Menu</span>
			</div>
			<div class="list-group list-group-flush">
				<a href="#" class="list-group-item list-group-item-action bg-light">일반게시판</a>
				<a href="#" class="list-group-item list-group-item-action bg-light">선물옵션게시판</a>
				<a href="#" class="list-group-item list-group-item-action bg-light">수급정보</a>
			</div>
		</div>
		<!-- /#사이드바 -->

		<!-- 네비바 -->
		<div id="page-content-wrapper">

			<nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">

				<!-- 메뉴 확장 버튼 -->
				<button class="btn navbar-toggler-icon" id="menu-toggle"></button>

				<button class="navbar-toggler" type="button" data-toggle="collapse"
					data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
					aria-expanded="false" aria-label="Toggle navigation"
				>
					<span class="navbar-toggler-icon"></span>
				</button>

				<!-- 네비 메뉴 -->
				<div class="collapse navbar-collapse" id="navbarSupportedContent">
					<ul class="navbar-nav ml-auto mt-2 mt-lg-0">

						<!-- 익명 사용자일 경우 -->
						<s:authorize access="isAnonymous()">
							<li class="nav-item">
								<a class="nav-link" href="/loginView">로그인</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="/registerUserView">회원가입</a>
							</li>
							<li class="nav-item dropdown">
								<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
									role="button" data-toggle="dropdown" aria-haspopup="true"
									aria-expanded="false"
								> Dropdown </a>
								<div class="dropdown-menu dropdown-menu-right"
									aria-labelledby="navbarDropdown"
								>
									<a class="dropdown-item" href="#">Action</a>
									<a class="dropdown-item" href="#">Another action</a>
									<div class="dropdown-divider"></div>
									<a class="dropdown-item" href="#">Something else here</a>
								</div>
							</li>
						</s:authorize>


						<!-- 로그인된 사용자일 경우 -->
						<s:authorize access="isAuthenticated()">
							<li class="nav-item">
								<a class="nav-link" href="/logoutAsk">로그아웃</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="/logoutAsk?logoutAllWeb=${user.username}">
									모든기기 로그아웃</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="/userInfoAsk">회원정보</a>
							</li>

							<!-- 관리자 페이지 -->
							<s:authorize access="hasRole('admin')">
								<li class="nav-item">
									<a class="nav-link" href="/admin/userInfo">관리자페이지</a>
								</li>
							</s:authorize>

							<li class="nav-item dropdown">
								<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
									role="button" data-toggle="dropdown" aria-haspopup="true"
									aria-expanded="false"
								> Dropdown </a>
								<div class="dropdown-menu dropdown-menu-right"
									aria-labelledby="navbarDropdown"
								>
									<a class="dropdown-item" href="#">Action</a>
									<a class="dropdown-item" href="#">Another action</a>
									<div class="dropdown-divider"></div>
									<a class="dropdown-item" href="#">Something else here</a>
								</div>
							</li>
						</s:authorize>
					</ul>
				</div>
			</nav>

			<!-- 본문 컨텐츠 전체 -->
			<div class="container-fluid my-4 mx-2">




				<!-- 좌측 본문창 시작 -->
				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-8">

							<!-- 페이지 이름 및  인증 사용자 출력 -->
							<div class="row mb-3">
								<table class="table">
									<thead>
										<tr class="thead-dark">
											<th scope="col" style="width: 30%"><h3>${viewName}</h3></th>
											<!-- 인증된 사용자일 경우만 출력 -->
											<s:authorize access="isAuthenticated()">
												<th scope="col" style="width: 70%" class="text-right font-weight-normal"><h6>[${user.username}]
														님이 접속했습니다.</h6></th>
											</s:authorize>
										</tr>
									</thead>
								</table>
							</div>