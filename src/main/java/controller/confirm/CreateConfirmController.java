package controller.confirm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import controller.Controller;
import model.domain.ProvideConfirm;
import model.service.ProvideConfirmService;

public class CreateConfirmController implements Controller {
    private ProvideConfirmService provideConfirmService;

    public CreateConfirmController() {
        this.provideConfirmService = new ProvideConfirmService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int requesterId = Integer.parseInt(request.getParameter("requesterId")); // 전달받은 요청자 ID
        int providePostId = Integer.parseInt(request.getParameter("providePostId")); // 전달받은 제공 글 ID

        try {
            // DB에 새로운 ProvideConfirm 생성
            ProvideConfirm provideConfirm = new ProvideConfirm();
            provideConfirm.setRequester_id(requesterId);
            provideConfirm.setProvide_post_id(providePostId);
            provideConfirm.setActual_return_date(new java.sql.Date(System.currentTimeMillis())); // 기본값으로 현재 날짜 설정
            provideConfirm.setPenalty_points(0); // 기본값 0
            provideConfirm.setOverdue_days(0); // 기본값 0

            provideConfirmService.createProvideConfirm(provideConfirm); // DB에 삽입

            // 대여 정보를 가져옴
            Map<String, Object> rentalDetails = provideConfirmService.getRentalDecisionDetails(provideConfirm.getId());

            if (rentalDetails == null) {
                request.setAttribute("error", "대여 정보를 가져올 수 없습니다.");
                return "/error.jsp";
            }

            // JSP로 전달할 데이터를 request attribute에 설정
            request.setAttribute("itemnickname", rentalDetails.get("itemnickname"));

            Map<String, Object> requester = (Map<String, Object>) rentalDetails.get("requester");
            request.setAttribute("requester", requester);

            Map<String, Object> provider = (Map<String, Object>) rentalDetails.get("provider");
            request.setAttribute("provider", provider);

            request.setAttribute("store", rentalDetails.get("store"));
            request.setAttribute("rental_place", rentalDetails.get("rental_place"));
            request.setAttribute("return_place", rentalDetails.get("return_place"));
            request.setAttribute("rental_date", rentalDetails.get("rental_date"));
            request.setAttribute("return_date", rentalDetails.get("return_date"));

            return "/rentalConfirm/rentalDecisionForm.jsp"; // JSP로 이동
        } catch (Exception e) {
            request.setAttribute("error", "대여 정보를 가져오는 중 오류가 발생했습니다.");
            return "/error.jsp";
        }
    }
}
