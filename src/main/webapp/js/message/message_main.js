/**
 * message js입니다.
 */
function calculateRelativeTime(sentTime) {
    const now = new Date(); // 현재 시간
    const sentDate = new Date(sentTime); // 메시지 작성 시간
    const diff = now - sentDate; // 시간 차이 (밀리초)

    if (isNaN(sentDate.getTime())) {
        return "잘못된 날짜";
    }

    // 분, 시간, 일 계산
    const minutes = Math.floor(diff / (1000 * 60));
    if (minutes < 1) return "방금 전";
    if (minutes < 60) return `${minutes}분 전`;

    const hours = Math.floor(diff / (1000 * 60 * 60));
    if (hours < 24) return `${hours}시간 전`;

    const days = Math.floor(diff / (1000 * 60 * 60 * 24));
    return `${days}일 전`;
}

// HTML 요소 업데이트 함수
function updateRelativeTimes() {
    document.querySelectorAll(".time").forEach((timeElement) => {
        const sentTime = timeElement.getAttribute("data-sent-time"); // data-sent-time 속성 값
        const relativeTime = calculateRelativeTime(sentTime); // 상대 시간 계산
        timeElement.textContent = relativeTime; // 계산된 시간 표시
    });
}

// 페이지 로드 시 실행
window.onload = updateRelativeTimes;
