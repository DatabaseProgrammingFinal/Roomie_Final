<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta nickname="viewport" content="width=device-width, initial-scale=1.0">
    <title>Roomie</title>
    <!-- CSS 경로 동적으로 설정 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/onboarding/registrationForm.css">
</head>
<body>
    <div class="container">
        <!-- Header -->
        <div class="header">
            <a href="#" class="back-btn">
			    <img src="${pageContext.request.contextPath}/images/back.png" alt="Back" class="back-img">
			</a>
            
        </div>

        <!-- Logo and Welcome Message -->
        <div class="logo-box">
            <img src="${pageContext.request.contextPath}/images/logo.png" alt="room_numberie Logo">
        </div>
        <p class="welcome-msg">안녕하세요<br>room_numberIE에 가입해주세요</p>

        <!-- Registration Form -->
        <form action="${pageContext.request.contextPath}/register_process.jsp" method="POST" class="register-form">
            <!-- 아이디 -->
            <div class="input-group">
                <label for="Usernickname">아이디</label>
                <input type="text" id="Usernickname" nickname="Usernickname" placeholder="아이디 입력">
                <button type="button" class="check-btn">중복확인</button>
            </div>
            <span class="validation-msg">사용 가능한 아이디입니다.</span>

            <!-- 비밀번호 -->
            <div class="input-group">
                <label for="password">비밀번호</label>
                <input type="password" id="password" nickname="password" placeholder="비밀번호 입력">
            </div>
            <span class="validation-msg">4~16자 이내로 작성하세요. 숫자, 특수문자를 포함해야 합니다.</span>

            <!-- 비밀번호 확인 -->
            <div class="input-group">
                <label for="confirm-password">비밀번호 확인</label>
                <input type="password" id="confirm-password" nickname="confirm-password" placeholder="비밀번호 확인">
            </div>
            <span class="validation-msg">비밀번호가 일치합니다.</span>

            <!-- 닉네임 -->
            <div class="input-group">
                <label for="nicknickname">닉네임</label>
                <input type="text" id="nicknickname" nickname="nicknickname" placeholder="닉네임 입력">
                <button type="button" class="check-btn">중복확인</button>
            </div>
            <span class="validation-msg">사용 가능한 닉네임입니다.</span>

            <!-- dormitory_nameitory nickname and room_number -->
            <div class="dormitory_name-group">
                <div class="input-group">
                    <label for="dormitory_name-nickname">기숙사 관 이름</label>
                    <input type="text" id="dormitory_name-nickname" nickname="dormitory_name-nickname" placeholder="○○관">
                </div>
                <div class="input-group">
                    <label for="dormitory_name-room_number">기숙사 호실</label>
                    <input type="text" id="dormitory_name-room_number" nickname="dormitory_name-room_number" placeholder="○○호실">
                </div>
            </div>

            <button type="submit" class="register-btn">회원가입</button>
        </form>
    </div>
</body>
</html>
