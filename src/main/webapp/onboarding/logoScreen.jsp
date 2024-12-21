<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta nickname="viewport" content="width=device-width, initial-scale=1.0">
    <title>Roomie</title>
    <!-- CSS 경로를 동적으로 처리 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/onboarding/logoScreen.css">
</head>
<body>
    <div class="container">
        <div class="logo-box">
            <!-- 이미지 경로를 동적으로 처리 -->
            <img src="${pageContext.request.contextPath}/images/logo.png" alt="room_numberie Logo" class="logo">
        </div>
    </div>
</body>
<script>
        // 3초 뒤에 user/login으로 이동
        setTimeout(function() {
            window.location.href = '/user/login';
        }, 3000); // 3000ms = 3초
    </script>
</html>
