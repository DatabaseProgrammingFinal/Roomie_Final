<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Roomie App</title>
    <!-- CSS 파일 경로 수정 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/require_list.css">
</head>
<body>
    <div class="container">
        <div class="content"> 
            <!-- 상단 (Header) -->
            <div class="header">
                <a href="#" class="back-btn">
                    <!-- 뒤로 가기 버튼 이미지 -->
                    <img src="${pageContext.request.contextPath}/resources/images/back.png" alt="Back" class="back-img">
                </a>
                <!-- 헤더 이미지 -->
                <img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="Profile" class="header-img">
                <hr>
            </div>
            <!-- 본문 (Body) -->
            <div class="body">
                <div class="button-container">
                    <button class="button-req">물건 필요해요</button>
                    <button class="button-prov">물건 제공해요</button>
                </div>
                <div>
                    <p class="search_result">계산기로 검색한 결과입니다</p>
                </div>
                <div class="list-container">
                    <!-- 리스트 아이템들 -->
                    <div class="list-req">
                        <div class="list-item">
                            <!-- 이미지 경로 수정 -->
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
                    <div class="list-req">
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
                    <div class="list-req">
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
                    <div class="list-req">
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
                    <div class="list-req">
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
                </div>
            </div>
            <div class="nav"> 
                <!-- 하단 (네비게이션바) -->
                <nav class="navbar">
                    <a href="#" class="nav-item">
                        <img src="${pageContext.request.contextPath}/resources/images/message.png" alt="Mail">
                    </a>
                    <a href="#" class="nav-item">
                        <img src="${pageContext.request.contextPath}/resources/images/home.png" alt="Home">
                    </a>
                    <a href="#" class="nav-item">
                        <img src="${pageContext.request.contextPath}/resources/images/search.png" alt="Search">
                    </a>
                </nav>
            </div>
        </div>
    </div>
   
</body>
</html>
