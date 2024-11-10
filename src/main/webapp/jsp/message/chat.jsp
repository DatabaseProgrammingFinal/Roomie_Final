<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chat</title>
    <!-- CSS 파일 경로 설정 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/message/chat.css">
</head>
<body>
    <div class="chat-container">
        <header class="chat-header">
            <a href="#" class="back-btn"><img src="${pageContext.request.contextPath}/resources/images/back.png" alt="Back"></a>
            <div class="profile">
                <img src="${pageContext.request.contextPath}/resources/images/profile.png" alt="Profile" class="profile-img">
                <div class="name">솜솜1</div>
            </div>
        </header>

        <div class="chat-body">
            <div class="message received">
                <img src="${pageContext.request.contextPath}/resources/images/profile.png" alt="Profile" class="avatar">
                <div class="message-content">
                    <p>우산 빌리신다고 하셨었죠?</p>
                </div>
            </div>

            <div class="message sent">
                <div class="message-content">
                    <p>네, 오늘 바로 가능한가요</p>
                </div>
            </div>

            <div class="message received">
                <img src="${pageContext.request.contextPath}/resources/images/profile.png" alt="Profile" class="avatar">
                <div class="message-content">
                    <p>글쎄요 어쩌고 저쩌고 ......</p>
                    <p>어쩌고 저쩌</p>
                </div>
            </div>

            <div class="message sent">
                <div class="message-content">
                    <p>답장답장답장답장답장..</p>
                    <p>답장답장답장답장답장..</p>
                    <p>답장답장답장답장답장..</p>
                </div>
            </div>

            <div class="date-label">화요일, 2024년 10월 01일</div>

            <div class="message received">
                <img src="${pageContext.request.contextPath}/resources/images/profile.png" alt="Profile" class="avatar">
                <div class="message-content">
                    <p>어쩌고 저쩌고 응응 그래</p>
                </div>
            </div>

            <div class="message sent">
                <div class="message-content">
                    <p>응ㅎㅎ</p>
                </div>
            </div>
        </div>

        <footer class="chat-footer">
            <input type="text" class="input-field" placeholder="메시지를 입력하세요">
            <button class="send-btn"><img src="${pageContext.request.contextPath}/resources/images/send.png" alt="Send"></button>
        </footer>
    </div>

</body>
</html>
