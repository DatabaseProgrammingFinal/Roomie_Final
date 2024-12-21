document.querySelector('.chat-footer form').addEventListener('submit', (event) => {
    event.preventDefault(); // 기본 폼 동작 방지

    const form = event.target; // form의 요소를 가져옴.
    const messageInput = form.querySelector('.input-field');
    const content = messageInput.value;
    const recipientId = form.querySelector('input[name="recipientId"]').value;

    // AJAX 요청
    fetch(form.action, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `recipientId=${recipientId}&content=${encodeURIComponent(content)}`,
    })
        .then((response) => response.text()) // 서버 응답을 텍스트로 처리
        .then((data) => {
            if (data.includes("오류")) { // 오류 처리
                alert(data);
                return;
            }

            // 메시지를 화면에 추가
            const chatBody = document.querySelector('.chat-body');
            const newMessage = document.createElement('div');
            newMessage.className = 'message sent';
            newMessage.innerHTML = `<div class="message-content"><p>${data}</p></div>`;
            chatBody.appendChild(newMessage);

            // 입력창 초기화
            messageInput.value = '';
            chatBody.scrollTop = chatBody.scrollHeight; // 최신 메시지로 스크롤
        })
        .catch((error) => {
            console.error('Error:', error);
            alert('메시지를 보내는 중 문제가 발생했습니다.');
        });
});
