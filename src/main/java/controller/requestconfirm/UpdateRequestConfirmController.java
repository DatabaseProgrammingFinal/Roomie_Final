package controller.requestconfirm;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.service.RequestConfirmService;

public class UpdateRequestConfirmController implements Controller {
    private RequestConfirmService requestConfirmService;

    public UpdateRequestConfirmController() {
        this.requestConfirmService = new RequestConfirmService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            // ProvideConfirm ID와 입력된 값 수신
            int requestConfirmId = Integer.parseInt(request.getParameter("requestConfirmId"));
            String store = request.getParameter("store");
            String rentalPlace = request.getParameter("rentalPlace");
            String returnPlace = request.getParameter("returnPlace");
            Date rentalDate = Date.valueOf(request.getParameter("rentalDate")); // YYYY-MM-DD 형식 필요
            Date returnDate = Date.valueOf(request.getParameter("returnDate")); // YYYY-MM-DD 형식 필요

            System.out.println("Debug - Store Value: " + store);
            // 업데이트 서비스 호출
            requestConfirmService.updateRentalDecisionDetails(requestConfirmId, store, rentalPlace, returnPlace,
                    rentalDate, returnDate);

            response.sendRedirect(
                    request.getContextPath() + "/requestConfirm/view?requestConfirmId=" + requestConfirmId);
            return null; // 리다이렉트 후 JSP 경로 반환 불필요
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "대여 정보 업데이트 중 오류가 발생했습니다.");
            return "/error.jsp"; // 에러 페이지로 이동
        }
    }
}
