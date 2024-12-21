package controller.confirm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;

public class ReturnConfirmController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String provideConfirmId = request.getParameter("provideConfirmId"); // URL에서 ID 가져오기
        request.setAttribute("provideConfirmId", provideConfirmId); // JSP에 전달
        return "/rentalConfirm/returnConfirmPopup.jsp"; // 반납 확인 팝업으로 연결
    }
}