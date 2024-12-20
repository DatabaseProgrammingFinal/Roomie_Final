package controller.confirm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import controller.Controller;
import model.service.ProvideConfirmService;

public class ViewConfirmController implements Controller {
    private ProvideConfirmService provideConfirmService;

    public ViewConfirmController() {
        this.provideConfirmService = new ProvideConfirmService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int providePostId = Integer.parseInt(request.getParameter("providePostId"));

        try {
            // 대여 정보를 조회
            Map<String, Object> rentalDetails = provideConfirmService.getRentalDecisionDetails(providePostId);

            if (rentalDetails == null) {
                request.setAttribute("error", "대여 정보를 가져올 수 없습니다.");
                return "/error.jsp";
            }

            // JSP에 전달할 데이터 설정
            request.setAttribute("itemnickname", rentalDetails.get("itemnickname"));
            request.setAttribute("requester", rentalDetails.get("requester"));
            request.setAttribute("provider", rentalDetails.get("provider"));
            request.setAttribute("store", rentalDetails.get("store"));
            request.setAttribute("rental_place", rentalDetails.get("rental_place"));
            request.setAttribute("return_place", rentalDetails.get("return_place"));
            request.setAttribute("rental_date", rentalDetails.get("rental_date"));
            request.setAttribute("return_date", rentalDetails.get("return_date"));

            return "/rentalConfirm/rentalStatusView.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "대여 정보를 가져오는 중 오류가 발생했습니다.");
            return "/error.jsp";
        }
    }
}
