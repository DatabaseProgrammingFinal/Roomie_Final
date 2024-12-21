<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta nickname="viewport"
	content="width=device-width, initial-scale=1.0">
<title>Roomie</title>
<!-- CSS 파일 경로 설정 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/message/chat.css">
</head>
<body>
	<div class="chat-container">
		<header class="chat-header">
			<!-- back button 
			<a href="#" class="back-btn"><img
				src="${pageContext.request.contextPath}/images/back.png" alt="Back"></a>
			-->
			<a
				href="${pageContext.request.contextPath}/message?userId=${sessionScope.userId}&filter=all"
				class="back-btn"> <img
				src="${pageContext.request.contextPath}/images/back.png" alt="Back"
				class="back-img">
			</a>

			<div class="profile">
				<img src="${pageContext.request.contextPath}/images/profile.png"
					alt="Profile" class="profile-img">
				<div class="nickname">솜솜1</div>
				<p>현재 세션 User ID: ${sessionScope.userId}</p>
				<c:set var="providePostId" value="${param.providePostId}" />


			</div>
		</header>

		<div class="chat-body">
			<c:forEach var="message" items="${messages}">
				<div
					class="message ${message.sender.id == sessionScope.userId ? 'sent' : 'received'}">
					<c:if test="${message.sender.id != sessionScope.userId}">
						<img src="${pageContext.request.contextPath}/images/profile.png"
							alt="Profile" class="avatar">
					</c:if>
					<div class="message-content">
						<p>${message.content}</p>

					</div>
				</div>
			</c:forEach>
		</div>

		<footer class="chat-footer">
			<form action="${pageContext.request.contextPath}/message/send"
				method="post">
				<input type="hidden" name="recipientId" value="${recipientId}">
				<input type="text" name="content" class="input-field"
					placeholder="메시지를 입력하세요">
				<button type="submit" class="send-btn">
					<img src="${pageContext.request.contextPath}/images/send.png"
						alt="Send">
				</button>
			</form>
		</footer>
	</div>

</body>
</html>