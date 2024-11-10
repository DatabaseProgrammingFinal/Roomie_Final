<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Form</title>
    <!-- CSS 경로를 동적으로 처리 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/onboarding/loginForm.css">
</head>
<body>
    <div class="container">
        <div class="content">
            <!-- 로고 -->
            <div class="logo-box">
                <img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="Roomie Logo" class="logo">
            </div>

            <!-- 환영 메시지 -->
            <p class="welcome-msg">환영합니다<br>계정을 입력해주세요</p>

            <!-- 로그인 폼 -->
            <form action="${pageContext.request.contextPath}/login_process.jsp" method="POST" class="login-form">
                <div class="input-group">
                    <label for="username">아이디</label>
                    <input type="text" id="username" name="username" placeholder="아이디 입력">
                </div>
                <div class="input-group">
                    <label for="password">비밀번호</label>
                    <input type="password" id="password" name="password" placeholder="비밀번호 입력">
                </div>
                <button type="submit" class="login-btn">로그인</button>
            </form>

            <!-- 회원가입 링크 -->
            <div class="signup-link">
                계정이 없나요? <a href="${pageContext.request.contextPath}/signup.jsp">회원가입</a>
            </div>
        </div>
    </div>
</body>
</html>
