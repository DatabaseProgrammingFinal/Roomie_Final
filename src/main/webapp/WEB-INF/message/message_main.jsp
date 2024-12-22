<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Roomie</title>
<!-- CSS 경로를 동적으로 처리 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/message/default.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/message/message_main.css">
</head>
<body>
	<!-- 
	<form method="GET" action="${pageContext.request.contextPath}/message">
		<input type="hidden" name="userId" value="${sessionScope.userId}">
		<input type="hidden" name="filter" value="all">
		<button type="submit">메시지 보기</button>
	</form>
 -->
	<div class="container">
		<div class="content">
			<!-- 상단 (Header) -->
			<div class="header">
				<a href="javascript:history.back()" class="back-btn"> <img
					src="${pageContext.request.contextPath}/images/back.png" alt="Back"
					class="back-img">
				</a> <img src="${pageContext.request.contextPath}/images/logo.png"
					alt="Profile" class="header-img">
				<hr>
			</div>

			<!-- 본문 (Body) -->
			<div class="body">
				<h1>쪽지함</h1>
				<div class="search-bar">
					<form method="GET"
						action="${pageContext.request.contextPath}/message">
						<input type="hidden" name="userId" value="${sessionScope.userId}">
						<input type="hidden" name="filter" value="all"> <input
							type="text" name="search" placeholder="검색어를 입력해주세요."
							value="${param.search}">
						<button type="submit">
							<img src="${pageContext.request.contextPath}/images/search.png"
								alt="Search">
						</button>
					</form>
				</div>
				<!-- 
            <div class="filter-buttons">
                <form method="GET" action="${pageContext.request.contextPath}/message">
                    <input type="radio" name="filter" id="all" value="all" ${param.filter == null || param.filter == 'all' ? 'checked' : ''}>
                    <label for="all" class="filter-button">All</label>

                    <input type="radio" name="filter" id="sent" value="sent" ${param.filter == 'sent' ? 'checked' : ''}>
                    <label for="sent" class="filter-button">보낸 쪽지</label>

                    <input type="radio" name="filter" id="received" value="received" ${param.filter == 'received' ? 'checked' : ''}>
                    <label for="received" class="filter-button">받은 쪽지</label>
                </form>
            </div>
             -->
				<ul class="message-list" id="messageList">
					<!-- DB에서 가져온 메시지 데이터 출력 -->
					<c:forEach var="message" items="${messages}">
						<li class="message-item"><a
							href="${pageContext.request.contextPath}/message/chat?sender=${message.sender.id}&recipientId=${message.receiver.id}&postId=${message.id}&postType=provide">
								<div class="avatar"></div>
								<div class="message-info">
									<span class="nickname">${message.sender.nickname}</span> <span
										class="message-preview">${message.content}</span>
								</div> <fmt:formatDate value="${message.sentDate}"
									pattern="yyyy-MM-dd'T'HH:mm:ss" var="formattedDate" /> <span
								class="time" data-sent-time="${formattedDate}"></span>
						</a></li>
					</c:forEach>

					<!-- 빈 메시지 처리 -->
					<c:if test="${messages == null || messages.isEmpty()}">
						<div class="empty-message">쪽지가 없습니다.</div>
					</c:if>
				</ul>

				</ul>
				<!-- 빈 메시지 표시 -->
				<c:if test="${messages == null || messages.isEmpty()}">
					<div class="empty-message">쪽지가 없습니다.</div>
				</c:if>
			</div>

			<!-- 하단 (네비게이션바) -->
			<div class="nav">
				<nav class="navbar">
					<a
						href="${pageContext.request.contextPath}/message?userId=${sessionScope.userId}&filter=all"
						class="nav-item"><img
						src="${pageContext.request.contextPath}/images/message.png"
						alt="Mail"></a> <a
						href="${pageContext.request.contextPath}/requestpost/list"
						class="nav-item"><img
						src="${pageContext.request.contextPath}/images/home.png"
						alt="Home"></a> <a href="#" class="nav-item"><img
						src="${pageContext.request.contextPath}/images/search.png"
						alt="Search"></a>
				</nav>
			</div>
		</div>
	</div>
	<script
		src="${pageContext.request.contextPath}/js/message/message_main.js"></script>
</body>
</html>