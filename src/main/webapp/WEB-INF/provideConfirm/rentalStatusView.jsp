<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta nickname="viewport"
	content="width=device-width, initial-scale=1.0">
<title>Roomie</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/rentalConfirm/rentalDecisionForm.css">
<style>
/* Popup container 기존 css */
.popup-container {
	width: 390px;
	height: 279px;
	border-radius: 14px;
	background-color: var(--gray-0, #FFF);
	display: flex;
	justify-content: center;
	align-items: center;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

/* Popup content */
.popup-content {
	text-align: center;
	width: 100%;
	padding: 20px;
	box-sizing: border-box;
}

/* Title styling */
.title {
	font-size: 24px;
	font-weight: bold;
	margin-bottom: 20px;
}

/* Confirmation text */
.confirmation-text {
	font-size: 16px;
	margin-bottom: 30px;
}

/* Button container */
.button-container {
	display: flex;
	justify-content: space-between;
	align-items: center;
	gap: 20px;
}

/* Yes button */
.yes-btn {
	width: 130px;
	height: 44px;
	background-color: #6997E5;
	color: #FFF;
	border: none;
	border-radius: 14px;
	font-size: 16px;
	cursor: pointer;
	transition: background-color 0.3s;
}

.yes-btn:hover {
	background-color: #4272bb;
}

/* No button */
.no-btn {
	width: 130px;
	height: 44px;
	background-color: transparent;
	border: 2px solid #888;
	border-radius: 14px;
	font-size: 16px;
	color: #888;
	cursor: pointer;
	transition: background-color 0.3s, color 0.3s;
}

.no-btn:hover {
	background-color: #f1f1f1;
	color: #555;
}

/* Info text */
.info-text {
	font-size: 12px;
	color: red;
	margin-top: 20px;
}

.popup-container.hidden {
            display: none;
        }

</style>
</head>
<body>
	<div class="container">
		<!-- 상단 (Header) -->
		<div class="header">
			<a href="#" class="back-btn"><img
				src="${pageContext.request.contextPath}/images/back.png" alt="Back"
				class="back-img"></a>
		</div>

		<div class="content">
			<h2 class="title">대여 정보 확인</h2>

			<!-- Static Info Section -->
			<div class="info">
				<div class="info-row">
					<span class="label">물품명</span> <span class="value"
						id="item-nickname">${itemnickname}</span>
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
							<img src="${pageContext.request.contextPath}/images/roomie.png"
								alt="roomie Icon" class="roomie-img"> <select id="store">
								<option value="${store}"
									${store != 300 && store != 400 ? "selected" : ""}>현재
									상점: ${store} (기본값)</option>
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

				<button class="start-rental-btn" onclick="openPopup()">반납
					진행</button>
			</div>

			<div class="nav">
				<nav class="navbar">
					<a href="${pageContext.request.contextPath}/message"
						class="nav-item"><img
						src="${pageContext.request.contextPath}/images/message.png"
						alt="Mail"></a> <a href="#" class="nav-item"><img
						src="${pageContext.request.contextPath}/images/home.png"
						alt="Home"></a> <a href="#" class="nav-item"><img
						src="${pageContext.request.contextPath}/images/search.png"
						alt="Search"></a>
				</nav>
			</div>
		</div>
	</div>

	<!-- 반납 확인 팝업 -->
	<div id="return-popup" class="popup-container hidden">
		<div class="popup-content">
			<h1 class="title">반납 진행</h1>
			<p class="confirmation-text">반납이 완료되었습니까?</p>
			<div class="button-container">
				<button type="button" class="yes-btn" onclick="confirmReturn()">예</button>
				<button type="button" class="no-btn" onclick="closePopup()">아니오</button>
			</div>
			<p class="info-text">예 버튼을 누르면 실제 반납이 자동 등록됩니다.</p>
		</div>
	</div>

	<script>
    function openPopup() {
        document.getElementById('return-popup').classList.remove('hidden');
    }

    function closePopup() {
        document.getElementById('return-popup').classList.add('hidden');
    }

    function confirmReturn() {
        const provideConfirmId = '${provideConfirmId}';
        if (provideConfirmId) {
            closePopup();
            window.location.href = '${pageContext.request.contextPath}/provideConfirm/outcome?provideConfirmId=' + provideConfirmId;
        } else {
            alert('반납 정보가 없습니다. 다시 시도해주세요.');
        }
    }
    </script>
</body>
</html>
