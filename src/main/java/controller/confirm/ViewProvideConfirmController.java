package controller.confirm;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.service.ProvideConfirmService;

public class ViewProvideConfirmController implements Controller {
    private ProvideConfirmService provideConfirmService;

    public ViewProvideConfirmController() {
        this.provideConfirmService = new ProvideConfirmService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int provideConfirmId = Integer.parseInt(request.getParameter("provideConfirmId")); // ProvideConfirm ID로 조회

        try {
            // 디버깅: ProvideConfirm ID 확인
            System.out.println("Debug - ProvideConfirm ID: " + provideConfirmId);

            // ProvideConfirm ID로 대여 정보 조회
            Map<String, Object> rentalDetails = provideConfirmService.getRentalDecisionDetails(provideConfirmId);

            if (rentalDetails == null) {
                System.out.println("Debug - Rental details are null.");
                request.setAttribute("error", "대여 정보를 가져올 수 없습니다.");
                return "/error.jsp";
            }

            // 디버깅: 조회된 데이터 출력
            System.out.println("Debug - Item Nickname: " + rentalDetails.get("itemnickname"));
            System.out.println("Debug - Store: " + rentalDetails.get("store"));
            System.out.println("Debug - Rental Place: " + rentalDetails.get("rental_place"));
            System.out.println("Debug - Return Place: " + rentalDetails.get("return_place"));
            System.out.println("Debug - Rental Date: " + rentalDetails.get("rental_date"));
            System.out.println("Debug - Return Date: " + rentalDetails.get("return_date"));

            Map<String, Object> requester = (Map<String, Object>) rentalDetails.get("requester");
            Map<String, Object> provider = (Map<String, Object>) rentalDetails.get("provider");

            System.out.println("Debug - Requester: " + requester);
            System.out.println("Debug - Provider: " + provider);

            // JSP에 전달할 데이터 설정
            request.setAttribute("itemnickname", rentalDetails.get("itemnickname"));
            request.setAttribute("requester", requester);
            request.setAttribute("provider", provider);
            request.setAttribute("store", rentalDetails.get("store"));
            request.setAttribute("rental_place", rentalDetails.get("rental_place"));
            request.setAttribute("return_place", rentalDetails.get("return_place"));
            request.setAttribute("rental_date", rentalDetails.get("rental_date"));
            request.setAttribute("return_date", rentalDetails.get("return_date"));

            request.setAttribute("provideConfirmId", provideConfirmId);
            return "/provideConfirm/rentalStatusView.jsp"; // JSP로 이동
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "대여 정보를 가져오는 중 오류가 발생했습니다.");
            return "/error.jsp";
        }
    }
}
