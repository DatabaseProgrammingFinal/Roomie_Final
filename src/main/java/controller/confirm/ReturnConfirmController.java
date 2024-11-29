package controller.confirm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;

public class ReturnConfirmController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "/rentalConfirm/returnConfirmPopup.jsp"; // 반납 확인 팝업으로 연결
    }
}

