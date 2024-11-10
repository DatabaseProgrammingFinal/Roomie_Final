<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>대여 정보 결정</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/retntalConfirm/rentalDecisionForm.css">
</head>
<body>
    <div class="container">
        <!-- 상단 (Header) -->
        <div class="header">
            <a href="#" class="back-btn">
                <img src="${pageContext.request.contextPath}/resources/images/back.png" alt="Back" class="back-img"> <!-- 뒤로 가기 버튼 이미지 -->
            </a>
        </div>

        <div class="content">
            <h2 class="title">대여 정보 결정</h2>
            <!-- Static Info Section -->
            <div class="info">
                <div class="info-row">
                    <span class="label">물품명</span>
                    <span class="value" id="item-name">${itemName}</span> <!-- JSP로 전달된 값 출력 -->
                </div>
                <div class="info-row">
                    <span class="label">대여 제공자</span>
                    <span class="value" id="provider">${provider}</span> <!-- JSP로 전달된 값 출력 -->
                </div>
                <div class="info-row">
                    <span class="label">대여 요청자</span>
                    <span class="value" id="requester">${requester}</span> <!-- JSP로 전달된 값 출력 -->
                </div>
            </div>

            <!-- Editable Fields -->
            <div class="form-container">
                <div class="store-info">
                    <div class="input-group">
                        <label for="store">대여 제공 상점</label>
                        <div class="select-container">
                            <img src="${pageContext.request.contextPath}/resources/images/roomie.png" alt="Roomie Icon" class="roomie-img">
                            <select id="store" name="store">
                                <option value="300">300 루미</option>
                                <option value="400">400 루미</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="location-group">
                    <div class="input-group">
                        <label for="rental-place">대여 장소</label>
                        <input type="text" id="rental-place" name="rental-place" placeholder="대여 장소 입력">
                    </div>
                    <div class="input-group">
                        <label for="return-place">반납 장소</label>
                        <input type="text" id="return-place" name="return-place" placeholder="반납 장소 입력">
                    </div>
                </div>

                <div class="date-group">
                    <div class="input-group">
                        <label for="rental-date">대여 날짜</label>
                        <input type="date" id="rental-date" name="rental-date" value="${rentalDate}">
                    </div>
                    <span class="date-separator">~</span>
                    <div class="input-group">
                        <label for="return-date">반납 날짜</label>
                        <input type="date" id="return-date" name="return-date" value="${returnDate}">
                    </div>
                </div>

                <button class="start-rental-btn">대여 시작</button>
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
