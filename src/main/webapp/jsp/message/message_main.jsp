<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Roomie App</title>
    <!-- CSS 경로를 동적으로 처리 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/message/default.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/message/message_main.css">
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
                <h1>쪽지함</h1>

                <div class="search-bar">
                    <input type="text" placeholder="검색어를 입력해주세요.">
                    <button type="submit"><img src="${pageContext.request.contextPath}/resources/images/search.png" alt="Search"></button>
                </div>

                <div class="filter-buttons">
                    <input type="radio" name="filter" id="all" checked>
                    <label for="all" class="filter-button">All</label>
                    
                    <input type="radio" name="filter" id="sent">
                    <label for="sent" class="filter-button">보낸 쪽지</label>

                    <input type="radio" name="filter" id="received">
                    <label for="received" class="filter-button">받은 쪽지</label>
                </div>

                <button class="new-message-btn">새로운 쪽지 보내기</button>

                <!-- 쪽지 리스트 -->
                <ul class="message-list" id="messageList">
                    <!-- 자바스크립트로 동적으로 8개의 리스트 생성 -->
                </ul>

                <!-- 빈 메시지 표시 -->
                <div class="empty-message" id="emptySent" style="display: none;">보낸 쪽지가 없습니다.</div>
                <div class="empty-message" id="emptyReceived" style="display: none;">받은 쪽지가 없습니다.</div>
            </div>

            <!-- 하단 (네비게이션바) -->
            <div class="nav">
                <nav class="navbar">
                    <a href="#" class="nav-item"><img src="${pageContext.request.contextPath}/resources/images/message.png" alt="Mail"></a>
                    <a href="#" class="nav-item"><img src="${pageContext.request.contextPath}/resources/images/home.png" alt="Home"></a>
                    <a href="#" class="nav-item"><img src="${pageContext.request.contextPath}/resources/images/search.png" alt="Search"></a>
                </nav>
            </div>
        </div>
    </div>

    <script>
        const allButton = document.getElementById('all');
        const sentButton = document.getElementById('sent');
        const receivedButton = document.getElementById('received');
        const messageList = document.getElementById('messageList');
        const emptySent = document.getElementById('emptySent');
        const emptyReceived = document.getElementById('emptyReceived');

        // 8개의 동일한 쪽지 항목을 생성하는 함수
        function generateMessages() {
            for (let i = 0; i < 8; i++) {
                const li = document.createElement('li');
                li.innerHTML = ` 
                    <div class="message-item">
                        <div class="avatar"></div>
                        <div class="message-info">
                            <span class="name">솜솜${i + 1}</span>
                            <span class="message-preview">어쩌고 저쩌고</span>
                        </div>
                        <span class="time">1시간 전</span>
                    </div>
                `;
                messageList.appendChild(li);
            }
        }

        // 함수 정의
        function showAllMessages() {
            messageList.style.display = 'block';
            emptySent.style.display = 'none';
            emptyReceived.style.display = 'none';
        }

        function showSentMessages() {
            messageList.style.display = 'none';
            emptySent.style.display = 'block';
            emptyReceived.style.display = 'none';
        }

        function showReceivedMessages() {
            messageList.style.display = 'none';
            emptySent.style.display = 'none';
            emptyReceived.style.display = 'block';
        }

        // 이벤트 리스너 등록
        allButton.addEventListener('change', showAllMessages);
        sentButton.addEventListener('change', showSentMessages);
        receivedButton.addEventListener('change', showReceivedMessages);

        // 페이지 로드 시 8개의 메시지 생성
        generateMessages();
    </script>
</body>
</html>
