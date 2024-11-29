<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta nickname="viewport" content="width=device-width, initial-scale=1.0">
    <title>Roomie</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/rentalConfirm/${penaltyExists ? 'yesPenaltyAndRewardPage.css' : 'noPenaltyAndRewardPage.css'}">
</head>
<body>
    <div class="container">
        <!-- 상단 (Header) -->
        <div class="header">
            <a href="#" class="back-btn">
                <img src="${pageContext.request.contextPath}/images/back.png" alt="Back" class="back-img">
            </a> <!-- 뒤로 가기 버튼 이미지 -->
        </div>

        <div class="content">
            <h2 class="title">대여 정보 확인</h2>
            <!-- Static Info Section -->
            <div class="info">
                <div class="info-row">
                    <span class="label">물품명</span>
                    <span class="value" id="item-nickname">${itemnickname}</span>
                </div>
                <div class="info-row">
                    <span class="label">대여 제공자</span>
                    <span class="value" id="provider">${provider}</span>
                </div>
                <div class="info-row">
                    <span class="label">대여 요청자</span>
                    <span class="value" id="requester">${requester}</span>
                </div>
            </div>

            <!-- Non-editable Fields -->
            <div class="form-container">
                <div class="store-info">
                    <label for="store">대여 제공 상점</label>
                    <div class="select-container non-editable">
                        <img src="${pageContext.request.contextPath}/images/roomie.png" alt="room_numberie Icon" class="room_numberie-img">
                        <span id="store">${store}</span>
                    </div>
                </div>

                <div class="location-group">
                    <div class="input-group">
                        <label for="rental-place">대여 장소</label>
                        <div class="value-box">${rentalPlace}</div>
                    </div>
                    <div class="input-group">
                        <label for="return-place">반납 장소</label>
                        <div class="value-box">${returnPlace}</div>
                    </div>
                </div>

                <div class="date-group">
                    <div class="input-group">
                        <label for="rental-date">대여 날짜</label>
                        <div class="value-box">${rentalDate}</div>
                    </div>
                    <span class="date-separator">~</span>
                    <div class="input-group">
                        <label for="return-date">반납 날짜</label>
                        <div class="value-box">${returnDate}</div>
                    </div>
                </div>

                <!-- 실제 반납 날짜 -->
                <div class="input-group">
                    <label for="actual-return-date">실제 반납 날짜</label>
                    <div class="value-box">${actualReturnDate}</div>
                </div>

                <!-- 벌점 여부 및 메시지 -->
                <c:choose>
                    <c:when test="${penaltyExists}">
                        <div class="penalty-status">
                            <span class="penalty-message">${penaltyMessage}</span> <!-- 벌점 메시지 -->
                        </div>
                        <div class="reward-info">
                            <span class="reward-message">${rewardMessage}</span> <!-- 보상 메시지 -->
                            <br>
                            <span class="penalty-message">${penaltyDetailMessage}</span>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="penalty-status">
                            <span class="penalty-message">벌점 없음</span> <!-- 기본 메시지 -->
                        </div>
                        <div class="reward-info">
                            <span class="reward-message">${rewardMessage}</span>
                        </div>
                    </c:otherwise>
                </c:choose>

                <button class="start-rental-btn">상벌점 부과</button>
            </div>

            <div class="nav">
                <!-- 하단 (네비게이션바) -->
                <nav class="navbar">
                    <a href="#" class="nav-item">
                        <img src="${pageContext.request.contextPath}/images/message.png" alt="Mail">
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
    </div>
</body>
</html>
