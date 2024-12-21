<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>대여 성공</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/provideConfirm/success.css">
</head>
<body>
    <div class="container">
        <!-- 상단 (Header) -->
        <div class="header">
            <a href="${pageContext.request.contextPath}/provideConfirm/view?providePostId=${providePostId}" class="back-btn">
                <img src="${pageContext.request.contextPath}/images/back.png" alt="Back" class="back-img">
            </a>
        </div>

        <!-- 성공 메시지 -->
        <div class="content">
            <h2 class="title">대여 정보가 성공적으로 업데이트되었습니다!</h2>
            <p class="message">대여가 성공적으로 시작되었습니다. 대여 상태를 확인하려면 아래 버튼을 클릭하세요.</p>

            <!-- 대여 상태 보기 버튼 -->
            <div class="button-container">
                <a href="${pageContext.request.contextPath}/provideConfirm/view?providePostId=${providePostId}" class="btn">대여 상태 보기</a>
            </div>
        </div>

        <!-- 하단 네비게이션 -->
        <div class="nav">
            <nav class="navbar">
                <a href="#" class="nav-item">
                    <img src="${pageContext.request.contextPath}/images/message.png" alt="Messages">
                </a>
                <a href="#" class="nav-item">
                    <img src="${pageContext.request.contextPath}/images/home.png" alt="Home">
                </a>
                <a href="#" class="nav-item">
                    <img src="${pageContext.request.contextPath}/images/search.png" alt="Search">
                </a>
            </nav>
        </div>
    </div>
</body>
</html>
