<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>대여 정보 확인</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/retntalConfirm/yesPenaltyAndRewardPage.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <a href="#" class="back-btn">
                <img src="${pageContext.request.contextPath}/resources/images/back.png" alt="Back" class="back-img">
            </a> <!-- 뒤로 가기 버튼 이미지 -->
        </div>

        <div class="content">
            <h2 class="title">대여 정보 확인</h2>

            <!-- Static Info Section -->
            <div class="info">
                <div class="info-row">
                    <span class="label">물품명</span>
                    <span class="value" id="item-name">계산기</span>
                </div>
                <div class="info-row">
                    <span class="label">대여 제공자</span>
                    <span class="value" id="provider">○○관○○호김둘리</span>
                </div>
                <div class="info-row">
                    <span class="label">대여 요청자</span>
                    <span class="value" id="requester">○○관○○호홍길동</span>
                </div>
            </div>

            <!-- Non-editable Fields -->
            <div class="form-container">
                <div class="store-info">
                    <label for="store">대여 제공 상점</label>
                    <div class="select-container non-editable">
                        <img src="${pageContext.request.contextPath}/resources/images/roomie.png" alt="Roomie Icon" class="roomie-img">
                        <span id="store">300 루미</span>
                    </div>
                </div>

                <div class="location-group">
                    <div class="input-group">
                        <label for="rental-place">대여 장소</label>
                        <div class="value-box">1층 로비</div>
                    </div>
                    <div class="input-group">
                        <label for="return-place">반납 장소</label>
                        <div class="value-box">1층 식당</div>
                    </div>
                </div>

                <div class="date-group">
                    <div class="input-group">
                        <label for="rental-date">대여 날짜</label>
                        <div class="value-box">2024-09-10</div>
                    </div>
                    <span class="date-separator">~</span>
                    <div class="input-group">
                        <label for="return-date">반납 날짜</label>
                        <div class="value-box">2024-09-24</div>
                    </div>
                </div>

                <!-- 실제 반납 날짜 추가 -->
                <div class="input-group">
                    <label for="actual-return-date">실제 반납 날짜</label>
                    <div class="value-box">2024-09-26</div>
                </div>

                <!-- 연체 여부 메시지 -->
                <div class="penalty-status">
                    <span class="penalty-message">2일 연체 (50루미 감점/일당)</span>
                </div>

                <!-- 대여 제공자, 대여 요청자 벌점 정보 -->
                <div class="reward-info">
                    <span class="reward-message">대여 제공자 제공 상점 300루미 부과</span><br>
                    <span class="penalty-message">대여 요청자 연체 벌점 100루미 부과</span>
                </div>

                <button class="start-rental-btn">상벌점 부과</button>
            </div>
            <div class="nav">
                <!-- 하단 (네비게이션바) -->
                <nav class="navbar">
                    <a href="#" class="nav-item"><img src="${pageContext.request.contextPath}/resources/images/message.png" alt="Mail"></a>
                    <a href="#" class="nav-item"><img src="${pageContext.request.contextPath}/resources/images/home.png" alt="Home"></a>
                    <a href="#" class="nav-item"><img src="${pageContext.request.contextPath}/resources/images/search.png" alt="Search"></a>
                </nav>
            </div>
        </div>
    </div>
</body>
</html>
