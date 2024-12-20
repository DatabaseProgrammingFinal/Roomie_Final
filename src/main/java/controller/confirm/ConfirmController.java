package controller.confirm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;

public class ConfirmController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 특정 요청 파라미터에 따라 적절한 JSP로 연결
        String action = request.getParameter("action");
        
        if ("create".equals(action)) {
            return "/rentalConfirm/rentalDecisionForm.jsp";
        } else if ("start".equals(action)) {
            return "/rentalConfirm/rentalConfirmPopup.jsp";
        } else if ("return".equals(action)) {
            return "/rentalConfirm/returnConfirmPopup.jsp";
        } else if ("outcome".equals(action)) {
            return "/rentalConfirm/penaltyAndRewardPage.jsp";
        }
          else if ("view".equals(action)) { 
            return "/rentalConfirm/rentalStatusView.jsp";
        } else if ("update".equals(action)) {
            // 업데이트 로직을 처리할 JSP 또는 컨트롤러 연결
            return "/rentalConfirm/rentalStatusView.jsp"; // 성공 페이지
        } else if ("updatePoints".equals(action)) {
            return "/success.jsp"; 
        }
          else {
            return "/rentalConfirm/penaltyAndRewardPage.jsp";
        }
    }
}