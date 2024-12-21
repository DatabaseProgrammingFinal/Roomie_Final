<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Roomie</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/rentalConfirm/rentalConfirmPopup.css">
</head>
<body>
    <div class="popup-container">
        <div class="popup-content">
            <h1 class="title">매칭 성공!</h1>
            <div class="info">
                <p class="label">대여 요청자</p>
                <div class="dynamic-content" id="requester">
                    <p>이름: ${requester.nickname}</p>
                    <p>기숙사: ${requester.dormitory_name}</p>
                    <p>호실: ${requester.room_number}</p>
                </div>
            </div>
            <div class="info">
                <p class="label">대여 제공자</p>
                <div class="dynamic-content" id="provider">
                    <p>이름: ${provider.nickname}</p>
                    <p>기숙사: ${provider.dormitory_name}</p>
                    <p>호실: ${provider.room_number}</p>
                </div>
            </div>
            <p class="confirmation-text">대여를 진행하시겠습니까?</p>
            <div class="button-container">
               <button class="yes-btn" 
        onclick="redirectToCreateConfirm(${providerId}, ${requestPostId})">예</button>
<button class="no-btn" onclick="history.back();">아니요</button>
            </div>
        </div>
    </div>

    <script>
    function redirectToCreateConfirm(providerId, requestPostId) {
        window.location.href = '${pageContext.request.contextPath}/requestConfirm/create?providerId=' + providerId + '&requestPostId=' + requestPostId;
    }
</script>
</body>
</html>
