<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rental Confirmation</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/retntalConfirm/rentalConfirmPopup.css">
</head>
<body>
    <div class="popup-container">
        <div class="popup-content">
            <h1 class="title">매칭 성공!</h1>
            <div class="info">
                <p class="label">대여 요청자</p>
                <p class="dynamic-content" id="requester">${requester}</p> <!-- JSP 동적 데이터 출력 -->
            </div>
            <div class="info">
                <p class="label">대여 제공자</p>
                <p class="dynamic-content" id="provider">${provider}</p> <!-- JSP 동적 데이터 출력 -->
            </div>
            <p class="confirmation-text">대여를 진행하시겠습니까?</p>
            <div class="button-container">
                <button class="yes-btn">예</button>
                <button class="no-btn">아니요</button>
            </div>
        </div>
    </div>

    <!-- JavaScript를 사용하여 동적으로 콘텐츠를 삽입하는 예시 -->
    <script>
        // 필요에 따라 JSP에서 전달된 값을 자바스크립트로 사용할 수 있습니다.
        document.getElementById('requester').textContent = '${requester}';
        document.getElementById('provider').textContent = '${provider}';
    </script>
</body>
</html>
