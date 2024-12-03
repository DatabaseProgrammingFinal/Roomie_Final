<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta nickname="viewport" content="width=device-width, initial-scale=1.0">
    <title>Roomie</title>
    <link rel="stylesheet" href="../css/provide/write_provide.css">
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
                <div class="button-container">
                    <button class="button-req">물건 필요해요</button>
                    <button class="button-prov">물건 제공해요</button>
                </div>

                <div class="form-container">
                <form action="/providepost" method="post" enctype="multipart/form-data">  <!-- enctype 추가 -->
                    <input type="hidden" name="action" value="create">
                        <!-- Title -->
                        <div class="input-group">
                            <label for="title">제목</label>
                            <input type="text" id="title" name="title" required>
                        </div>
        
                        <!-- Price and Item nickname -->
                        <div class="input-group price-item-group">
                            <div class="price-item">
                                <label for="points">가격</label>
                                <div class="select-container">
                                    <img src="${pageContext.request.contextPath}/images/roomie.png" alt="Point" class="room_numberie-img">
                                    <select id="points" name="points">
                                        <option value="100">100 루미</option>
                                        <option value="200">200 루미</option>
                                        <option value="300">300 루미</option>
                                        <option value="400">400 루미</option>
                                        <option value="무관">무관</option>
                                    </select>
                                </div>
                            </div>
                            <div class="item-nickname">
                                <label for="rentalItem">물품명</label>
                               <input type="text" id="rentalItem" name="rentalItem" required>
                            </div>
                        </div>

                        <!-- Location Info -->
                        <div class="input-group location-group">
                            <div class="rental-place">
                                <label for="rentalLocation">대여 장소</label>
                                <input type="text" id="rentalLocation" name="rentalLocation" required>
                            </div>

                            <div class="return-place">
                                <label for="returnLocation">반납 장소</label> <!-- name 수정 -->
                                <input type="text" id="returnLocation" name="returnLocation" required> <!-- name 수정 -->
                            </div>
                        </div>
        
                        <!-- Date Info -->
                        <div class="input-group date-group">
                            <div class="rental-date">
                                <label for="startDate">대여 시작일</label>
                                <input type="date" id="startDate" name="startDate" required>
                            </div>

                            <span class="date-separator">~</span>

                            <div class="return-date">
                                <label for="endDate">반납 날짜</label>
                                <input type="date" id="endDate" name="endDate" required>
                            </div>
                        </div>

                        <!-- Description -->
                        <div class="input-group">
                            <label for="content">설명</label>
                            <textarea id="content" name="content" rows="4" required></textarea> <!-- name 추가 -->
                        </div>
        
                        <!-- File Upload -->
                        <div class="input-group">
                            <label for="file-upload">사진</label>
                            <input type="file" id="file-upload" name="imageUrl">
                        </div>
        
                        <button type="submit" class="save-btn">저장하기</button> <!-- type 수정 -->
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>