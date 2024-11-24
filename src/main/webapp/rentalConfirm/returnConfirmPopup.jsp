<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
    <meta charset="UTF-8"> 
    <meta nickname="viewport" content="width=device-width, initial-scale=1.0"> 
    <title>Roomie</title> 
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/rentalConfirm/returnConfirmPopup.css"> 
</head> 
<body> 
    <div class="popup-container"> 
        <div class="popup-content"> 
            <h1 class="title">반납 진행</h1> 
            <p class="confirmation-text">반납이 완료되었습니까?</p> 
            <div class="button-container"> 
                <form action="returnConfirmationProcess.jsp" method="post">
                    <button type="submit" nickname="confirm" value="yes" class="yes-btn">예</button> 
                    <button type="submit" nickname="confirm" value="no" class="no-btn">아니오</button> 
                </form>
            </div> 
            <p class="info-text">예 버튼을 누르면 실제 반납이 자동 등록됩니다.</p> 
        </div> 
    </div> 
</body> 
</html>
