//package controller.confirm;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import controller.Controller;
//
// 
//public class OutComeConfirmController implements Controller {
//
//    @Override
//    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        return "/provideConfirm/penaltyAndRewardPage.jsp"; // 연체 여부 확인 페이지로 연결
//    }
//}

package controller.requestconfirm;

import java.sql.Date;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.service.RequestConfirmService;

public class OutComeRequestConfirmController implements Controller {
    private RequestConfirmService requestConfirmService;

    public OutComeRequestConfirmController() {
        this.requestConfirmService = new RequestConfirmService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // ProvideConfirm ID 가져오기
        int requestConfirmId = Integer.parseInt(request.getParameter("requestConfirmId"));

        // 현재 날짜 가져오기
        Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());

        try {
            // Service를 통해 실제 반납 날짜 업데이트
            requestConfirmService.updateActualReturnDate(requestConfirmId, currentDate);

            // 연체일 계산
            requestConfirmService.updateOverdueDays(requestConfirmId);

            Map<String, Object> rentalDetails = requestConfirmService.getRentalDecisionDetails(requestConfirmId);

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

            Date actualReturnDate = requestConfirmService.getActualReturnDate(requestConfirmId);
            request.setAttribute("actualReturnDate", actualReturnDate);

            Map<String, Integer> details = requestConfirmService.getOverdueAndPoints(requestConfirmId);

            // JSP에 전달
            request.setAttribute("details", details);
            request.setAttribute("requestConfirmId", requestConfirmId);
            // 성공 시 결과 페이지로 리다이렉트
            return "/requestConfirm/penaltyAndRewardPage.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            // 실패 시 에러 페이지로 이동
            request.setAttribute("error", "반납 처리 중 오류가 발생했습니다.");
            return "/error.jsp";
        }
    }
}
