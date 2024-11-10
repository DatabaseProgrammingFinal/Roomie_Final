<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Roomie App</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/provide/write_provide.css">
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
                <div class="button-container">
                    <button class="button-req">물건 필요해요</button>
                    <button class="button-prov">물건 제공해요</button>
                </div>

                <div class="form-container">
                    <form>
                        <!-- Title -->
                        <div class="input-group">
                            <label for="title">제목</label>
                            <input type="text" id="title">
                        </div>
        
                        <!-- Price and Item Name -->
                        <div class="input-group price-item-group">
                            <div class="price-item">
                                <label for="price">가격</label>
                                <div class="select-container">
                                    <img src="${pageContext.request.contextPath}/resources/images/roomie.png" alt="Point" class="roomie-img">
                                    <select id="price">
                                        <option value="100">100 루미</option>
                                        <option value="200">200 루미</option>
                                        <option value="300">300 루미</option>
                                        <option value="400">400 루미</option>
                                        <option value="무관">무관</option>
                                    </select>
                                </div>
                            </div>
                            <div class="item-name">
                                <label for="item-name">물품명</label>
                                <input type="text" id="item-name">
                            </div>
                        </div>

                        <!-- Location Info -->
                        <div class="input-group location-group">
                            <div class="rental-place">
                                <label for="rental-place">대여 장소</label>
                                <input type="text" id="rental-place">
                            </div>

                            <div class="return-place">
                                <label for="return-place">반납 장소</label>
                                <input type="text" id="return-place">
                            </div>
                        </div>
        
                        <!-- Date Info -->
                        <div class="input-group date-group">
                            <div class="rental-date">
                                <label for="rental-date">대여 날짜</label>
                                <input type="date" id="rental-date">
                            </div>

                            <span class="date-separator">~</span>

                            <div class="return-date">
                                <label for="return-date">반납 날짜</label>
                                <input type="date" id="return-date">
                            </div>
                        </div>

                        <!-- Description -->
                        <div class="input-group">
                            <label for="description">설명</label>
                            <textarea id="description" rows="4"></textarea>
                        </div>
        
                        <!-- File Upload -->
                        <div class="input-group">
                            <label for="file-upload">사진</label>
                            <input type="file" id="file-upload">
                        </div>
        
                        <button type="button" class="save-btn">저장하기</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
