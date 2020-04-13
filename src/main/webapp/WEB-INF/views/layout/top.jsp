<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>Welcome to Codevang's WEB</title>
<meta content="" name="descriptison">
<meta content="" name="keywords">

<!-- Favicons -->
<link href="/resources/assets/img/favicon.png" rel="icon">
<link href="/resources/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

<!-- Google Fonts -->
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i
	|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,
	500i,600,600i,700,700i"
	rel="stylesheet"
>
<!-- font-family: 'Nanum Gothic', sans-serif; -->
<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">

<!-- Vendor CSS Files -->
<link href="/resources/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="/resources/assets/vendor/icofont/icofont.min.css" rel="stylesheet">
<link href="/resources/assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
<link href="/resources/assets/vendor/animate.css/animate.min.css" rel="stylesheet">
<link href="/resources/assets/vendor/owl.carousel/assets/owl.carousel.min.css" rel="stylesheet">
<link href="/resources/assets/vendor/venobox/venobox.css" rel="stylesheet">

<!-- Template Main CSS File -->
<link href="/resources/assets/css/style.css?ver=<%=System.currentTimeMillis()%>" rel="stylesheet">

<!-- =======================================================
  * Template Name: Eterna - v2.0.0
  * Template URL: https://bootstrapmade.com/eterna-free-multipurpose-bootstrap-template/
  * Author: BootstrapMade.com
  * License: https://bootstrapmade.com/license/
  ======================================================== -->

<!-- jQuery -->
<script src="/resources/assets/vendor/jquery/jquery.min.js"></script>
</head>

<body>

	<!-- 인증 사용자 정보 -->
	<s:authentication property="principal" var="user" />

	<!-- ======= Top Bar ======= -->
	<section id="topbar" class="d-none d-lg-block">
		<div class="container d-flex">
			<div class="contact-info mr-auto">
				<i class="icofont-envelope"></i>
				<a href="mailto:codevang@naver.com">codevang@naver.com</a>
				<i class="icofont-phone"></i> +82 10-1234-5678
			</div>
			<div class="social-links">
				<a href="#" class="twitter">
					<i class="icofont-twitter"></i>
				</a>
				<a href="#" class="facebook">
					<i class="icofont-facebook"></i>
				</a>
				<a href="#" class="instagram">
					<i class="icofont-instagram"></i>
				</a>
				<a href="#" class="skype">
					<i class="icofont-skype"></i>
				</a>
				<a href="#" class="linkedin">
					<i class="icofont-linkedin"></i>
				</a>
			</div>
		</div>
	</section>

	<!-- ======= Header ======= -->
	<header id="header">
		<div class="container d-flex">

			<div class="logo mr-auto">
				<h1 class="text-light">
					<a href="/">
						<span>CODEVANG'S WEB</span>
					</a>
				</h1>
			</div>

			<nav class="nav-menu d-none d-lg-block">
				<ul>
					<li class="active"><a href="/">Home</a></li>

					<s:authorize access="isAnonymous()">
						<li><a href="/loginView">로그인</a></li>
						<li><a href="/registerUserView">회원가입</a></li>
					</s:authorize>

					<s:authorize access="isAuthenticated()">
						<li><a href="#" onclick="javascript:btnClick(logoutAskOne);">로그아웃</a></li>
						<s:authorize access="hasRole('admin')">
							<li><a href="/admin/adminUserInfoView">관리자페이지</a></li>
						</s:authorize>
						<li class="drop-down"><a href="#">회원 메뉴</a>
							<ul>
								<li><a href="/user/userInfoAsk">회원 정보 보기</a></li>
								<li><a href="#" onclick="javascript:btnClick(logoutAskAll)">자동로그인 일괄 해제</a></li>
							</ul></li>
					</s:authorize>
				</ul>
			</nav>
			<!-- .nav-menu -->
		</div>
	</header>
	<!-- End Header -->


	<!-- ======= Menu ======= -->
	<div class="container">
		<ul class="nav nav-tabs">
			<li class="nav-item"><a class="nav-link active">Menu</a></li>
			<li class="nav-item dropdown"><a class="nav-link dropdown-toggle" data-toggle="dropdown"
					href="#" role="button" aria-haspopup="true" aria-expanded="false"
				>게시판</a>
				<div class="dropdown-menu">
					<a class="dropdown-item" href="/board/basicBoardList/board_usual/1">일반 게시판</a>
					<a class="dropdown-item" href="/board/basicBoardList/board_info/1">정보 게시판</a>
					<!-- <div class="dropdown-divider"></div> 구분 라인-->
				</div></li>
			<!-- <li class="nav-item"><a class="nav-link" href="#">Link</a></li> -->
		</ul>
	</div>
	<!-- End Menu -->

	<s:authorize access="isAuthenticated()">
		<!-- 로그아웃 token 정보 (현재기기 로그아웃) -->
		<form name="logoutAskOne" action="/logoutAsk" method="post">
			<s:csrfInput />
		</form>

		<!-- 로그아웃 token 정보 (모든기기 로그아웃) -->
		<form name="logoutAskAll" action="/logoutAsk" method="post">
			<s:csrfInput />
			<input type="hidden" name="logoutAllWeb" value="${user.username}" />
		</form>
	</s:authorize>


	<main id="main">
		<!-- ======= Breadcrumbs ======= -->
		<section id="breadcrumbs" class="breadcrumbs">
			<div class="container">

				<div class="row">
					<div class="col-md-6 align-self-center">
						<a>${categoryName}</a>
						<h2>&nbsp;&nbsp; ${viewName}</h2>
					</div>

					<div class="col-md-6 align-self-center">
						<s:authorize access="isAuthenticated()">
							<h6 class="text-right">${user.username}님</h6>
						</s:authorize>
					</div>
				</div>
			</div>
		</section>
		<!-- End Breadcrumbs -->


		<!-- ======= Main content Section ======= -->
		<section id="blog" class="blog">
			<div class="container">
				<div class="row">