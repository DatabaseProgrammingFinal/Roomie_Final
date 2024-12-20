const form = document.querySelector('.chat-footer form');
const messageInput = form.querySelector('.input-field');

form.addEventListener('submit', (event) => {
    event.preventDefault(); // 기본 동작 차단
    const content = messageInput.value;
    const recipientId = form.querySelector('input[name="recipientId"]').value;

    fetch(`${form.action}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `recipientId=${recipientId}&content=${encodeURIComponent(content)}`,
    })
    .then(response => response.json())
    .then(data => {
        if (data.error) {
            alert(data.error);
            return;
        }
        
        // 메시지 화면에 추가
        const chatBody = document.querySelector('.chat-body');
        const newMessage = document.createElement('div');
        newMessage.className = 'message sent';
        newMessage.innerHTML = `<div class="message-content"><p>${data.content}</p></div>`;
        chatBody.appendChild(newMessage);
        
        // 입력창 초기화
        messageInput.value = '';
        chatBody.scrollTop = chatBody.scrollHeight;
    })
    .catch(error => console.error('Error:', error));
});
