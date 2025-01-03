<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Roomie</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/rentalConfirm/rentalConfirmPopup.css">
<script>
    function goBack() {
        const previousPage = '<%= request.getHeader("Referer") %>';
        if (previousPage) {
            window.location.href = previousPage;
        } else {
            alert('이전 페이지가 존재하지 않습니다.');
        }
    }
    function redirectToCreateConfirm(requesterId, providePostId) {
        window.location.href = '${pageContext.request.contextPath}/provideConfirm/create?requesterId=' + requesterId + '&providePostId=' + providePostId;
    }
</script>
</head>
<body>
	<div class="popup-container">
		<div class="popup-content">
			<h1 class="title">매칭 성공!</h1>
			<div class="info">
				<p class="label">대여 요청자</p>
				<div class="dynamic-content" id="requester">
					<p>${requester.nickname}/${requester.dormitory_name}/${requester.room_number}</p>
				</div>
			</div>
			<div class="info">
				<p class="label">대여 제공자</p>
				<div class="dynamic-content" id="provider">
					<p>${provider.nickname}/${provider.dormitory_name}/${provider.room_number}</p>
				</div>
			</div>
			<p class="confirmation-text">대여를 진행하시겠습니까?</p>
			<div class="button-container">
				<button class="yes-btn"
					onclick="redirectToCreateConfirm(${requesterId}, ${providePostId})">예</button>
				<button class="no-btn" onclick="goBack()">아니오</button>
			</div>
		</div>
	</div>
</body>
</html>
