<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Roomie</title>
    <link rel="stylesheet" href="../css/request/write_request.css">
    <script>
        // 뒤로 가기 버튼 클릭 시
        function goBack() {
            window.history.back();
        }

        // 제공 게시글 작성 페이지로 이동
        function goToProvidePostCreate() {
            window.location.href = "${pageContext.request.contextPath}/providepost/create";
        }
    </script>
</head>
<body>
    <div class="container">
        <div class="content"> 
            <!-- 상단 (Header) -->
            <div class="header">
                 <a href="javascript:void(0);" class="back-btn" onclick="goBack()"><img src="${pageContext.request.contextPath}/images/back.png" alt="Back" class="back-img"></a> <!-- 뒤로 가기 버튼 이미지 -->
            </div>

            <!-- 본문 (Body) -->
            <div class="body">
                <div class="button-container">
                    <button class="button-req">물건 필요해요</button>
                    <button class="button-prov" onclick="goToProvidePostCreate()">물건 제공해요</button>
                </div>

                <div class="form-container">
                   <form action="${pageContext.request.contextPath}/requestpost/create" method="post">

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
                                    <img src="${pageContext.request.contextPath}/images/roomie.png" alt="Point" class="roomie-img" width="24px">
                                    <select id="points" name="points" required>
                                        <option value="100">100 루미</option>
                                        <option value="200">200 루미</option>
                                        <option value="300">300 루미</option>
                                        <option value="400">400 루미</option>
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
                                <label for="returnLocation">반납 장소</label>
                                <input type="text" id="returnLocation" name="returnLocation" required>
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
                            <textarea id="content" name="content" rows="4" required></textarea>
                        </div>
        
        
                        <button type="submit" class="save-btn">저장하기</button>
                    </form>
                    <c:if test="${creationFailed}">
    <div class="error-message">${errorMsg}</div>
</c:if>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
