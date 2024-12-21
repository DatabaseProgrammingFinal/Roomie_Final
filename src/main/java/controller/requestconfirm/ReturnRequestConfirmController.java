package controller.requestconfirm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;

public class ReturnRequestConfirmController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String requestConfirmId = request.getParameter("requestConfirmId"); // URL에서 ID 가져오기
        request.setAttribute("requestConfirmId", requestConfirmId); // JSP에 전달
        return "/requestConfirm/returnConfirmPopup.jsp"; // 반납 확인 팝업으로 연결
    }
}