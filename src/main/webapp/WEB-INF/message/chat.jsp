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
			<a href="javascript:history.back()" class="back-btn"><img
				src="${pageContext.request.contextPath}/images/back.png" alt="Back"></a>
			<div class="profile">
				<img src="${pageContext.request.contextPath}/images/profile.png"
					alt="Profile" class="profile-img">
				<div class="nickname">${receiverNickName}</div>
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
				method="POST">
				<!--  
				<input type="hidden" name="postType"
					value="${postType != null ? postType : 'provide'}">

				<c:choose>
					<c:when test="${postType == 'provide'}">
						<input type="hidden" name="postId"
							value="${providePost.id != null ? providePost.id : ''}">
						<input type="hidden" name="recipientId"
							value="${providePost.providerId != null ? providePost.providerId : ''}">
					</c:when>
					<c:when test="${postType == 'request'}">
						<input type="hidden" name="postId"
							value="${requestPost.id != null ? requestPost.id : ''}">
						<input type="hidden" name="recipientId"
							value="${requestPost.requesterId != null ? requestPost.requesterId : ''}">
					</c:when>
					<c:otherwise>
						<p>유효하지 않은 postType 값입니다.</p>
					</c:otherwise>
				</c:choose>

				<input type="text" name="content" class="input-field"
					placeholder="메시지를 입력하세요">
				<button type="submit" class="send-btn">
					<img src="${pageContext.request.contextPath}/images/send.png"
						alt="Send">
				</button>
				-->
				<input type="hidden" name="postType" value="provide"> <input
					type="hidden" name="postId" value="1">
				<!-- 하드코딩된 postId -->
				<input type="hidden" name="recipientId" value="2">
				<!-- 하드코딩된 recipientId -->
				<!-- 메시지 내용 입력 -->
				<input type="text" name="content" class="input-field"
					placeholder="메시지를 입력하세요">
				<button type="submit" class="send-btn">
					<img src="${pageContext.request.contextPath}/images/send.png"
						alt="Send">
				</button>
			</form>
		</footer>
	</div>
	<script src="${pageContext.request.contextPath}/js/message/chat.js"></script>
</body>
</html>