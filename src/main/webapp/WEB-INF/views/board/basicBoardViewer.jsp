<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String boardName = (String) request.getAttribute("boardViewName");
	pageContext.setAttribute("categoryName", "게시판 / " + boardName);
	pageContext.setAttribute("viewName", "게시물 보기");
%>

<%@ include file="/WEB-INF/views/layout/top.jsp"%>

<!-- 게시물 VO -->
<c:set var="vo" value="${boardVO}" />

<!-- 게시물이 없을 경우 리스트로 리다이렉트 -->
<c:choose>
	<c:when test="${vo == null}">
		<script type="text/javascript">
			$(document).ready(function() {
				alert("존재하지 않는 게시물입니다.");
				location.href = "/board/basicBoardList/${boardName}/1";
			});
		</script>
	</c:when>

	<c:otherwise>
		<div class="col-lg-9">
			<div class="row">
				<div class="col">
					<s:authorize access="isAuthenticated()">
						<!-- 공감/비공감 Param 정보 -->
						<form name="boardInfo">
							<s:csrfInput />
							<input type="hidden" name="boardName" value="${boardName}">
							<input type="hidden" name="bId" value="${vo.bId}">
						</form>
						<!-- 답글쓰기 버튼 -->
						<a href="/board/basicBoardWriter/${boardName}/${vo.bId}"
							class="btn btn-info btn-md active mb-3" role="button" aria-pressed="true"
						>답글쓰기</a>

						<!-- 공감 버튼 -->
						<a id="bGoodBtn" class="btn btn-success btn-md active mb-3">공감</a>
						<script>
							$('#bGoodBtn').on(
									'click',
									function() {
										var form = $("form[name=boardInfo]")
												.serialize();
										$.ajax({
											url : "/board/basicBoardBGood",
											type : "POST",
											dataType : "json",
											data : form,
											success : function(data) {
												var txt = data.bGoodCount;
												$('#bGoodResult').text(txt);
											},
											error : function() {
												alert("이미 공감한 게시물입니다.");
											}
										});
									});
						</script>

						<!-- 비공감 버튼 -->
						<a id="bHateBtn" class="btn btn-success btn-md active mb-3">비공감</a>
						<script>
							$('#bHateBtn').on(
									'click',
									function() {
										var form = $("form[name=boardInfo]")
												.serialize();
										$.ajax({
											url : "/board/basicBoardBHate",
											type : "POST",
											dataType : "json",
											data : form,
											success : function(data) {
												var txt = data.bGoodCount;
												$('#bHateResult').text(txt);
											},
											error : function() {
												alert("이미 비공감한 게시물입니다.");
											}
										});
									});
						</script>
					</s:authorize>
				</div>

				<!-- 삭제버튼 (작성자에게만 보여주도록 함) -->
				<s:authorize access="isAuthenticated()">
					<div class="col-md-auto">
						<c:if test="${user.username eq vo.bUserId}">
							<!-- Button trigger modal -->
							<button type="button" class="btn btn-secondary" data-toggle="modal"
								data-target="#exampleModal"
							>삭제</button>

							<!-- Modal -->
							<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
								aria-labelledby="exampleModalLabel" aria-hidden="true"
							>
								<div class="modal-dialog" role="document">
									<div class="modal-content">
										<div class="modal-header">
											<h5 class="modal-title" id="exampleModalLabel">게시물 삭제</h5>
											<button type="button" class="close" data-dismiss="modal" aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
										</div>
										<div class="modal-body">게시물을 삭제하시겠습니까?</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-primary" onclick="clickDel(boardInfo)">삭제하기</button>
											<button type="button" class="btn btn-secondary" data-dismiss="modal">취소하기</button>
										</div>
									</div>
								</div>
							</div>
							<script>
								function clickDel(formName) {
									formName.action = "/board/contentDelAsk";
									formName.method = "post";
									formName.submit();
								}
							</script>
						</c:if>
					</div>
				</s:authorize>
			</div>

			<!-- 게시물 내용 -->
			<table class="table">
				<thead>
					<tr>
						<th scope="col" style="width: 70%" class="align-middle"><h3>${vo.bTitle}</h3>
							${vo.bUsername}(${vo.bUserId})</th>
						<th scope="col" style="width: 30%" class="text-right align-middle">조회수 ${vo.bHit}<br>
							공감 <a id="bGoodResult">${vo.bGood}</a> / 비공감 <a id="bHateResult">${vo.bHate}</a><br>${vo.bDate}
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><pre class="h5">${vo.bContent}</pre></td>
					</tr>
				</tbody>
			</table>
		</div>

		<!-- Right Sidebar -->
		<div class="col-lg-3 border-left">라이트~</div>
	</c:otherwise>

</c:choose>

<%@ include file="/WEB-INF/views/layout/bottom.jsp"%>