package controller.confirm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;

 
public class OutComeConfirmController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "/rentalConfirm/penaltyAndRewardPage.jsp"; // 연체 여부 확인 페이지로 연결
    }
}



