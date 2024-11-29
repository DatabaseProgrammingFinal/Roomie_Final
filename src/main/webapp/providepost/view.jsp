<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta nickname="viewport" content="width=device-width, initial-scale=1.0">
    <title>Roomie</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/provide/view_provide.css">
</head>
<body>
    <div class="container">
        <div class="content"> 
            <!-- 상단 (Header) -->
            <div class="header">
                <a href="#" class="back-btn"><img src="${pageContext.request.contextPath}/images/back.png" alt="Back" class="back-img"></a> <!-- 뒤로 가기 버튼 이미지 -->
            </div>

            <!-- 본문 (Body) -->
            <div class="body">
                <div class="title-container">
                    <p class="title">${post.title}</p>
                    <img src="${image_url}/images/menu.png" alt="Menu" class="menu-img" width="24px" height="24px">
                </div>
                <div class="profile">
                    <img src="${pageContext.request.contextPath}/images/user.png" alt="User Profile" class="profile-img" width="44px" height="44px">
                    <div class="profile-info">
                        <h3 class="nicknickname">${post.provider_id}</h3>
                        <p class="room_number">${post.rental_location}</p>
                    </div>
                </div>
                <div class="img-container">
                    <img src="${pageContext.request.contextPath}/images/sample.png" width="328px" height="218px"/>
                </div>
                <p class="description">${post.content}</p>

                <!-- 물품 정보 -->
                <div class="item-info">
                    <div class="info-row">
                        <span class="label">물품명</span>
                        <span class="value">${post.rental_item}</span>
                    </div>
                    <div class="info-row">
                        <span class="label">대여 장소</span>
                        <span class="value">${post.rental_location}</span>
                    </div>
                    <div class="info-row">
                        <span class="label">반납 장소</span>
                        <span class="value">${post.rental_location}</span>
                    </div>
                    <div class="info-row">
                        <span class="label">대여 날짜</span>
                        <span class="value">${post.rental_start_date} ~ ${post.rental_end_date}</span>
                    </div>
                </div>
            </div>
            
            <!-- 하단 버튼 -->
            <div class="footer">
                <img src="${pageContext.request.contextPath}/images/roomie.png" alt="Point" class="room_numberie-img" width="24px" height="24px">
                <span class="points">${post.points} 루미</span>
                <button class="send-msg-btn">쪽지 보내기</button>
            </div>
        </div>
    </div>
</body>
</html>
