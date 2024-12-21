<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>오류 페이지</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>
    <div class="error-container">
        <h1>오류가 발생했습니다.</h1>
        <p>${error}</p> <!-- 오류 메시지를 출력 -->
        <div class="button-container">
            <button onclick="location.href='${pageContext.request.contextPath}/home'">홈으로 이동</button>
            <button onclick="history.back();">이전 페이지로 이동</button>
        </div>
    </div>
</body>
</html>