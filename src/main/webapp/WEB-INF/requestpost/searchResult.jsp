<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Roomie</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/require_list.css">
<script>
	function goBack() {
		window.history.back();
	}
</script>

</head>
<body>
	<div class="container">
		<div class="content">
			<!-- 상단 (Header) -->
			<div class="header">
				<a href="javascript:void(0);" class="back-btn" onclick="goBack()">
					<img src="${pageContext.request.contextPath}/images/back.png"
					alt="Back" class="back-img"> <!-- 뒤로 가기 버튼 이미지 -->
				</a> <img src="${pageContext.request.contextPath}/images/logo.png"
					alt="Logo" class="header-img">
				<!-- 헤더 이미지 -->
				<hr>
			</div>

			<!-- 본문 (Body) -->
			<div class="body">
				<div class="button-container">
					<button class="button-req">물건 필요해요</button>
					 <a href="${pageContext.request.contextPath}/providepost/search?title=${param.title}" class="button-prov">물건 제공해요</a>
				</div>

				<div class="search-bar">
					<!-- 검색어 출력 -->
					<p>
						<strong>"${param.title}"</strong>로 검색한 결과입니다.
					</p>
				</div>

				<div class="list-container">
					<!-- requestPosts 리스트를 하나씩 출력 -->
					<c:choose>
						<c:when test="${not empty requestPosts}">
							<c:forEach var="post" items="${requestPosts}">
								<a
									href="${pageContext.request.contextPath}/requestpost/view/${post.id}">
									<div class="list-item">
										<!-- 이미지 (기본 이미지를 사용) -->
										<img
											src="${pageContext.request.contextPath}/images/no-img.png"
											alt="item image" class="item-img">
										<div class="item-details">
											<!-- 제목 출력 -->
											<p class="item-title">${post.title}</p>

											<div class="room_numberie-info">
												<img
													src="${pageContext.request.contextPath}/images/roomie.png"
													alt="Point" class="room_numberie-img" width="18px"
													height="18px">
												<!-- 포인트 출력 -->
												<span class="room_numberie-point">${post.points} 루미</span>
											</div>
										</div>

										<!-- 대여 기간 출력 -->
										<span class="date">${post.rentalStartDate} ~
											${post.rentalEndDate}</span>

									</div>
								</a>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<p class="no-results">검색 결과가 없습니다. 다른 검색어를 입력해 주세요.</p>
						</c:otherwise>
					</c:choose>
				</div>
			</div>

			<!-- 하단 (네비게이션바) -->
			<div class="nav">
				<nav class="navbar">
                    <a href="${pageContext.request.contextPath}/message" class="nav-item"><img src="${pageContext.request.contextPath}/images/message.png" alt="Mail"></a>
                    <a href="${pageContext.request.contextPath}/requestpost/list" class="nav-item"><img src="${pageContext.request.contextPath}/images/home.png" alt="Home"></a>
                    <a href="#" class="nav-item"><img src="${pageContext.request.contextPath}/images/search.png" alt="Search"></a>
                </nav>
			</div>
		</div>
	</div>
</body>
</html>
