<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta nickname="viewport" content="width=device-width, initial-scale=1.0">
    <title>Roomie</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
    <div class="list-container">
        <c:forEach var="post" items="${posts}">
            <div class="list-item">
                <p>${post.title}</p>
                <span>${post.rentalItem}</span>
            </div>
        </c:forEach>
    </div>
</body>
</html>
