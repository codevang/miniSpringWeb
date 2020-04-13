<%@page import="hs.spring.hsweb.mapper.vo.board.BasicBoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String boardName = (String) request.getAttribute("boardViewName");
	pageContext.setAttribute("categoryName", "게시판");
	pageContext.setAttribute("viewName", boardName);
%>
<%@ include file="/WEB-INF/views/layout/top.jsp"%>

<div class="col-lg-9">

	<!-- list -->
	<c:set var="list" value="${boardList.mainBoardList}" />

	<!-- 페이지가 정상 범위 밖의 페이지일 경우 1페이지로 리다이렉트-->
	<c:choose>
		<c:when test="${boardPaging == null}">
			<script>
				$(document).ready(function() {
					alert("잘못된 페이지입니다.");
					location.href = "/board/basicBoardList/${boardName}/1";
				});
			</script>
		</c:when>

		<c:otherwise>
			<s:authorize access="isAuthenticated()">
				<!-- 글쓰기 버튼 -->
				<a href="/board/basicBoardWriter/${boardName}/0" class="btn btn-info btn-md active mb-3"
					role="button" aria-pressed="true"
				>글쓰기</a>
			</s:authorize>

			<table class="table table-hover">
				<!-- 구분 -->
				<thead>
					<tr>
						<th scope="col" class="text-center align-middle">날짜</th>
						<th scope="col" class="text-center align-middle">제목</th>
						<th scope="col" class="text-center align-middle">글쓴이</th>
						<th scope="col" class="text-center align-middle">조회</th>
						<th scope="col" class="text-center align-middle">공감</th>
						<th scope="col" class="text-center align-middle">비공감</th>
					</tr>
				</thead>


				<!-- 게시물 -->
				<tbody>
					<c:forEach var="i" items="${list}">
						<tr>
							<td style="width: 13%" class="text-center align-middle">${i.bDate}</td>
							<td style="width: 47%" class="text-left align-middle"><a
									href="/board/basicBoardViewer/${boardName}/${i.bId}"
								>
									<c:forEach begin="1" end="${i.bIndent}" var="j">
										&nbsp;&nbsp;&nbsp;				
									</c:forEach>
									<c:if test="${i.bIndent > 0}">└ </c:if>
									${i.bTitle}
								</a></td>
							<td style="width: 10%" class="text-center align-middle">${i.bUsername}(${i.bUserId})</td>
							<td style="width: 10%" class="text-center align-middle">${i.bHit}</td>
							<td style="width: 10%" class="text-center align-middle">${i.bGood}</td>
							<td style="width: 10%" class="text-center align-middle">${i.bHate}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="row mt-5">
				<div class="col"></div>
				<div class="col-lg-auto">
					<ul class="pagination">
						<c:forEach begin="${boardPaging[0]}" end="${boardPaging[1]}" var="i">
							<li class="page-item"><a class="page-link"
									href="/board/basicBoardList/${boardName}/${i}"
								>${i}</a></li>
						</c:forEach>
					</ul>
				</div>
				<div class="col"></div>
			</div>
		</c:otherwise>
	</c:choose>
</div>

<!-- Right Sidebar -->
<div class="col-lg-3 border-left">

	<!-- 조회순 게시물 5개 -->
	<c:set var="bHitList" value="${boardList.bHitBoardList}" />
	<c:if test="${bHitList != null}">
		<div class="row">
			<table class="table table-striped ml-2">
				<thead class="thead-dark">
					<tr>
						<th scope="col" class="text-left align-middle">조회순</th>
						<th scope="col" class="text-center align-middle"></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="j" items="${bHitList}">
						<tr>
							<td style="width: 90%" class="text-left align-middle"><a
									href="/board/basicBoardViewer/${boardName}/${j.bId}"
								>
									<c:forEach begin="1" end="${j.bIndent}" var="w">
										re:				
									</c:forEach>
									${j.bTitle}
								</a></td>
							<td style="width: 10%" class="text-center align-middle">${j.bHit}</td>
						<tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>

	<!-- 공감순 게시물 5개 -->
	<c:set var="bGoodList" value="${boardList.bGoodBoardList}" />
	<c:if test="${bGoodList != null}">
		<div class="row mt-4">
			<table class="table table-striped ml-2">
				<thead class="thead-dark">
					<tr>
						<th scope="col" class="text-left align-middle">공감순</th>
						<th scope="col" class="text-center align-middle"></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="j" items="${bGoodList}">
						<tr>
							<td style="width: 90%" class="text-left align-middle"><a
									href="/board/basicBoardViewer/${boardName}/${j.bId}"
								>
									<c:forEach begin="1" end="${j.bIndent}" var="w">
										re:				
									</c:forEach>
									${j.bTitle}
								</a></td>
							<td style="width: 10%" class="text-center align-middle">${j.bGood}</td>
						<tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
</div>


<%@ include file="/WEB-INF/views/layout/bottom.jsp"%>