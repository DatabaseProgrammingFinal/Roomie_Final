<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Roomie App</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/request/view_request.css">
</head>
<body>
    <div class="container">
        <div class="content"> 
            <!-- 상단 (Header) -->
            <div class="header">
                <a href="#" class="back-btn"><img src="${pageContext.request.contextPath}/resources/images/back.png" alt="Back" class="back-img"></a> <!-- 뒤로 가기 버튼 이미지 -->
            </div>

            <!-- 본문 (Body) -->
            <div class="body">
                <div class="title-container">
                    <p class="title">계산기 빌려주세요제발</p>
                    <img src="${pageContext.request.contextPath}/resources/images/menu.png" alt="Menu" class="menu-img" width="24px" height="24px">
                </div>
                <div class="profile">
                    <img src="${pageContext.request.contextPath}/resources/images/user.png" alt="User Profile" class="profile-img" width="44px" height="44px">
                    <div class="profile-info">
                        <h3 class="nickname">닉네임~~</h3>
                        <p class="room">○○기숙사 301호실</p>
                    </div>
                </div>

                <p class="description">계산기 빌려주세요제발</p>

                <!-- 물품 정보 -->
                <div class="item-info">
                    <div class="info-row">
                        <span class="label">물품명</span>
                        <span class="value">계산기</span>
                    </div>
                    <div class="info-row">
                        <span class="label">대여 장소</span>
                        <span class="value">○○관 ○○</span>
                    </div>
                    <div class="info-row">
                        <span class="label">반납 장소</span>
                        <span class="value">○○관 ○○</span>
                    </div>
                    <div class="info-row">
                        <span class="label">대여 날짜</span>
                        <span class="value">0000.00.00 ~ 0000.00.00</span>
                    </div>
                </div>
            </div>
            
            <!-- 하단 버튼 -->
            <div class="footer">
                <img src="${pageContext.request.contextPath}/resources/images/roomie.png" alt="Point" class="roomie-img" width="24px" height="24px">
                <span class="points">300 루미</span>
                <button class="send-msg-btn">쪽지 보내기</button>
            </div>
        </div>
    </div>
</body>
</html>
