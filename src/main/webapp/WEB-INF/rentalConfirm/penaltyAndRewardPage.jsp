<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta nickname="viewport"
	content="width=device-width, initial-scale=1.0">
<title>Roomie</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/rentalConfirm/${penaltyExists ? 'yesPenaltyAndRewardPage.css' : 'noPenaltyAndRewardPage.css'}">
</head>
<script>
	function goBack() {
		window.history.back();
	}
</script>
<body>
	<div class="container">
		<!-- 상단 (Header) -->
		<div class="header">
			<a href="javascript:void(0);" class="back-btn" onclick="goBack()">
				<img src="${pageContext.request.contextPath}/images/back.png"
				alt="Back" class="back-img">
			</a>
		</div>

		<div class="content">
			<h2 class="title">대여 정보 확인</h2>
			<!-- Static Info Section -->
			<div class="info">
				<div class="info-row">
					<span class="label">물품명</span> <span class="value"
						id="item-nickname">${itemnickname}</span>
				</div>
				<div class="info-row">
					<span class="label">대여 제공자</span> <span class="value" id="provider">${provider.nickname}
						${provider.dormitory_name} ${provider.room_number}</span>
				</div>
				<div class="info-row">
					<span class="label">대여 요청자</span> <span class="value"
						id="requester">${requester.nickname}
						${requester.dormitory_name} ${requester.room_number}</span>
				</div>
			</div>

			<!-- Non-editable Fields -->
			<div class="form-container">
				<div class="store-info">
					<label for="store">대여 제공 상점</label>
					<div class="select-container non-editable">
						<img src="${pageContext.request.contextPath}/images/roomie.png"
							alt="room_numberie Icon" class="room_numberie-img"> <span
							id="store">${store}</span>
					</div>
				</div>

				<div class="location-group">
					<div class="input-group">
						<label for="rental-place">대여 장소</label>
						<div class="value-box">${rental_place}</div>
					</div>
					<div class="input-group">
						<label for="return-place">반납 장소</label>
						<div class="value-box">${return_place}</div>
					</div>
				</div>

				<div class="date-group">
					<div class="input-group">
						<label for="rental-date">대여 날짜</label>
						<div class="value-box">${rental_date}</div>
					</div>
					<span class="date-separator">~</span>
					<div class="input-group">
						<label for="return-date">반납 날짜</label>
						<div class="value-box">${return_date}</div>
					</div>
				</div>

				<!-- 실제 반납 날짜 -->
				<div class="input-group">
					<label for="actual-return-date">실제 반납 날짜</label>
					<div class="value-box">${actualReturnDate}</div>
				</div>

				<c:choose>
					<%-- overdue_days가 0인 경우 --%>
					<c:when test="${details.overdue_days eq 0}">
						<div class="penalty-status">
							<span class="penalty-message">벌점 없음</span>
						</div>
						<div class="reward-info">
							<span class="reward-message">대여 제공자 제공 상점
								${details.points}루미 부과</span>
						</div>
					</c:when>
					<%-- overdue_days가 0보다 큰 경우 --%>
					<c:otherwise>
						<div class="penalty-status">
							<span class="penalty-message">${details.overdue_days}일 연체
								(50루미 감점/일당)</span>
						</div>
						<div class="reward-info">
							<span class="reward-message">대여 제공자 제공 상점
								${details.points}루미 부과</span> <br> <span class="penalty-message">대여
								요청자 연체 벌점 ${details.penalty_points}루미 부과</span>
						</div>
					</c:otherwise>
				</c:choose>

				<button type="button" class="start-rental-btn"
					onclick="redirectToUpdatePoints()">상벌점 부과</button>

			</div>

			<div class="nav">
				<!-- 하단 (네비게이션바) -->
				<nav class="navbar">
					<a href="${pageContext.request.contextPath}/message"
						class="nav-item"> <img
						src="${pageContext.request.contextPath}/images/message.png"
						alt="Mail">
					</a> <a href="#" class="nav-item"> <img
						src="${pageContext.request.contextPath}/images/home.png"
						alt="Home">
					</a> <a href="#" class="nav-item"> <img
						src="${pageContext.request.contextPath}/images/search.png"
						alt="Search">
					</a>
				</nav>
			</div>
		</div>
	</div>

	<script>
		function redirectToUpdatePoints() {
			const provideConfirmId = '${provideConfirmId}'; // 서버에서 전달된 provideConfirmId
			if (provideConfirmId) {
				window.location.href = '${pageContext.request.contextPath}/confirm/updatePoints?provideConfirmId='
						+ provideConfirmId;
			} else {
				alert('반납 정보가 없습니다. 다시 시도해주세요.');
			}
		}
	</script>

</body>
</html>
