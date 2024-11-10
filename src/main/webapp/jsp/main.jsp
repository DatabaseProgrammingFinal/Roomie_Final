<!-- main.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Roomie App</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
</head>
<body>
    <div class="container">
        <div class="content"> 
            <!-- 상단 (Header) -->
            <div class="header">
                <a href="#" class="back-btn"><img src="${pageContext.request.contextPath}/resources/images/back.png" alt="Back" class="back-img"></a> <!-- 뒤로 가기 버튼 이미지 -->
                <img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="Profile" class="header-img"> <!-- 헤더 이미지 -->
                <hr>
            </div>
            <!-- 본문 (Body) -->
            <div class="body">
                <div class="button-container">
                    <button class="button-req">물건 필요해요</button>
                    <button class="button-prov">물건 제공해요</button>
                </div>
                <div>
                    <div class="search-bar">
                        <input type="text" placeholder="검색어를 입력해주세요.">
                         <button type="submit"><img src="${pageContext.request.contextPath}/resources/images/search.png" alt="Search"></button>
                    </div>
                </div>
                <div class="list-container">
                    <div class="list-req">
                        <!-- 첫 번째 리스트 아이템 -->
                        <div class="list-item">
                            <img src="${pageContext.request.contextPath}/resources/images/no-img.png" alt="item image" class="item-img">
                            <div class="item-details">
                                <p>계산기빌려주세요</p>
                                <div class="roomie-info">
                                    <img src="${pageContext.request.contextPath}/resources/images/roomie.png" alt="Point" class="roomie-img" width="18px" height="18px">
                                    <span class="roomie-point">300 루미</span>
                                </div>
                            </div>
                            <span class="date">0000.00.00 ~ 0000.00.00</span>
                        </div>
                    </div>
                    <!-- 다른 리스트 아이템도 반복 -->
                    <!-- 필요에 따라 추가 -->
                </div>
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
