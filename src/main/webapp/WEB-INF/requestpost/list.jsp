<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Roomie</title>
<link rel="stylesheet" href="../css/require_list.css">
<script>
	// 뒤로 가기 버튼 클릭 시
	function goBack() {
		window.history.back();
	}

	//로고 클릭하면 새로고침
	function goMain() {
		window.location.href = "${pageContext.request.contextPath}/requestpost/list";
	}

	// 게시글 페이지로 이동
	function goToProvidePost() {
		window.location.href = "${pageContext.request.contextPath}/providepost/list";
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
				</a> <img onclick="goMain()"
					src="${pageContext.request.contextPath}/images/logo.png"
					alt="Profile" class="header-img">
				<!-- 헤더 이미지 -->
				<hr>
			</div>

			<!-- 본문 (Body) -->
			<div class="body">
				<div class="button-container">
					<button class="button-req">물건 필요해요</button>
					<button class="button-prov" onclick="goToProvidePost()">물건
						제공해요</button>
				</div>

				<!-- Search Bar Form -->
				<div class="search-bar">
					<form
						action="${pageContext.request.contextPath}/requestpost/search"
						method="get" id="searchform">
						<input type="text" name="title" placeholder="검색어를 입력해주세요.">
						<button type="submit">
							<img src="${pageContext.request.contextPath}/images/search.png"
								alt="Search">
						</button>
					</form>
				</div>

				<div class="list-container">
				    <!-- requestPosts 리스트를 하나씩 출력 -->
				    <c:forEach var="post" items="${requestPosts}">
				        <c:if test="${post.status == 0}">
				            <a href="${pageContext.request.contextPath}/requestpost/view/${post.id}">
				                <div class="list-item">
				                    <img src="${pageContext.request.contextPath}/images/no-img.png"
				                         alt="item image" class="item-img">
				                    <div class="item-details">
				                        <p class="item-title">${post.title}</p>
				                        <div class="room_numberie-info">
				                            <img src="${pageContext.request.contextPath}/images/roomie.png"
				                                 alt="Point" class="room_numberie-img" width="18px"
				                                 height="18px">
				                            <span class="room_numberie-point">${post.points} 루미</span>
				                        </div>
				                    </div>
				                    <span class="date">${post.rentalStartDate} ~ ${post.rentalEndDate}</span>
				                </div>
				            </a>
				        </c:if>
				    </c:forEach>
				</div>
			</div>
			<div class="floating-button">
				<a href="${pageContext.request.contextPath}/requestpost/create">
					<img src="${pageContext.request.contextPath}/images/write.png"
					alt="Write">
				</a>
			</div>
			<!-- 하단 (네비게이션바) -->
			<div class="nav">
				<nav class="navbar">
					<a href="${pageContext.request.contextPath}/message"
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
</body>
</html>
