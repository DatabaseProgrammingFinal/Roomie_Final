package controller.requestconfirm;

import java.sql.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.domain.RequestConfirm;
import model.service.RequestConfirmService;

public class CreateRequestConfirmController implements Controller {
    private RequestConfirmService requestConfirmService;

    public CreateRequestConfirmController() {
        this.requestConfirmService = new RequestConfirmService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int providerId = Integer.parseInt(request.getParameter("providerId")); // 전달받은 요청자 ID
        int requestPostId = Integer.parseInt(request.getParameter("requestPostId")); // 전달받은 제공 글 ID

        try {
            // DB에 새로운 ProvideConfirm 생성
            RequestConfirm requestConfirm = new RequestConfirm();
            requestConfirm.setProvider_id(providerId);
            requestConfirm.setRequest_post_id(requestPostId);
            requestConfirm.setActual_return_date(Date.valueOf("2024-11-27"));
            requestConfirm.setPenalty_points(0); // 기본값 0
            requestConfirm.setOverdue_days(0); // 기본값 0

            requestConfirmService.createRequestConfirm(requestConfirm); // DB에 삽입

            // 대여 정보를 가져옴
            Map<String, Object> rentalDetails = requestConfirmService.getRentalDecisionDetails(requestConfirm.getId());

            if (rentalDetails == null) {
                request.setAttribute("error", "대여 정보를 가져올 수 없습니다.");
                return "/error.jsp";
            }

            // JSP로 전달할 데이터를 request attribute에 설정
            request.setAttribute("requestConfirmId", requestConfirm.getId());
            request.setAttribute("requestPostId", requestPostId);
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

            return "/requestConfirm/rentalDecisionForm.jsp"; // JSP로 이동
        } catch (Exception e) {
            request.setAttribute("error", "대여 정보를 가져오는 중 오류가 발생했습니다.");
            return "/error.jsp";
        }
    }
}
