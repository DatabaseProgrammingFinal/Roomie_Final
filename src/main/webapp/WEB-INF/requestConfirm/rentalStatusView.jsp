<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta nickname="viewport" content="width=device-width, initial-scale=1.0">
    <title>Roomie</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/rentalConfirm/rentalDecisionForm.css">
</head>
<body>
    <div class="container">
        <!-- 상단 (Header) -->
        <div class="header">
            <a href="#" class="back-btn"><img src="${pageContext.request.contextPath}/images/back.png" alt="Back" class="back-img"></a> <!-- 뒤로 가기 버튼 이미지 -->
        </div>

        <div class="content">
            <h2 class="title">대여 정보 확인</h2>
            
            <!-- Static Info Section -->
            <div class="info">
                <div class="info-row">
                    <span class="label">물품명</span>
                    <span class="value" id="item-nickname">${itemnickname}</span> <!-- 서버에서 전달된 물품명 -->
                </div>
                <div class="info-row">
					<span class="label">대여 제공자</span> <span class="value" id="provider">${provider.nickname}
						${provider.dormitory_name} ${provider.room_number}</span>
				</div>
                <div class="info-row">
					<span class="label">대여 요청자</span> <span class="value"
						id="requester">${requester.nickname}
						${requester.dormitory_name} ${requester.room_number}</span>
				</div>
            </div>

            <!-- Editable Fields -->
            <div class="form-container">
                <div class="store-info">
                    <div class="input-group">
                        <label for="store">대여 제공 상점</label>
                        <div class="select-container">
                            <img src="${pageContext.request.contextPath}/images/roomie.png" alt="room_numberie Icon" class="room_numberie-img">
                            <select id="store">
                              <option value="${store}"
										${store != 300 && store != 400 ? "selected" : ""}>현재
										상점: ${store} (기본값)</option>
									<!-- 선택 가능한 값 -->
									<option value="300" ${store == 300 ? "selected" : ""}>300
										루미</option>
									<option value="400" ${store == 400 ? "selected" : ""}>400
										루미</option>
                            </select>
                        </div>
                    </div>
                </div>
                
                <div class="location-group">
						<div class="input-group">
							<label for="rental-place">대여 장소</label> <input type="text"
								id="rental-place" name="rentalPlace" value="${rental_place}"
								placeholder="대여 장소 입력">
						</div>
						<div class="input-group">
							<label for="return-place">반납 장소</label> <input type="text"
								id="return-place" name="returnPlace" value="${return_place}"
								placeholder="반납 장소 입력">
						</div>
					</div>


                <div class="date-group">
						<div class="input-group">
							<label for="rental-date">대여 날짜</label> <input type="date"
								id="rental-date" name="rentalDate" value="${rental_date}">
						</div>
						<span class="date-separator">~</span>
						<div class="input-group">
							<label for="return-date">반납 날짜</label> <input type="date"
								id="return-date" name="returnDate" value="${return_date}">
						</div>
					</div>


                <button class="start-rental-btn" onclick="redirectToReturnConfirm()">반납 진행</button>
            </div>
            
            <div class="nav">
                <!-- 하단 (네비게이션바) -->
                <nav class="navbar">
					<a href="${pageContext.request.contextPath}/message"
						class="nav-item"><img
						src="${pageContext.request.contextPath}/images/message.png"
						alt="Mail"></a> <a
						href="${pageContext.request.contextPath}/requestpost/list"
						class="nav-item"><img
						src="${pageContext.request.contextPath}/images/home.png"
						alt="Home"></a> <a href="#" class="nav-item"><img
						src="${pageContext.request.contextPath}/images/search.png"
						alt="Search"></a>
				</nav>
            </div>
        </div>
    </div>
    <script>
    function redirectToReturnConfirm() {
        const requestConfirmId = '${requestConfirmId}'; // ViewConfirmController에서 전달된 requestConfirmId
        if (requestConfirmId) {
            window.location.href = '${pageContext.request.contextPath}/requestConfirm/return?requestConfirmId=' + requestConfirmId;
        } else {
            alert('반납 정보가 없습니다. 다시 시도해주세요.');
        }
    }
</script>
</body>
</html>
