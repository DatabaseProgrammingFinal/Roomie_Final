package controller.confirm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;

public class StartConfirmController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "/rentalConfirm/rentalConfirmPopup.jsp"; // 대여 매칭 성공 팝업으로 연결
    }
}
