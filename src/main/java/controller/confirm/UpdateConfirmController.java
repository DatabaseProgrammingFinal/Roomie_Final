//package controller.confirm;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import controller.Controller;
//import model.domain.RentalProvidePost;
//import model.service.ProvideConfirmService;
//
//public class UpdateConfirmController implements Controller {
//    private ProvideConfirmService provideConfirmService;
//
//    public UpdateConfirmController() {
//        this.provideConfirmService = new ProvideConfirmService();
//    }
//
//   
//    @Override
//    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        try {
//            
//            
//            // 디버그 로그 추가
//            System.out.println("DEBUG: Received Parameters:");
//            System.out.println("provideConfirmId: " + request.getParameter("provideConfirmId"));
//            System.out.println("providePostId: " + request.getParameter("providePostId"));
//
//            // 필수 파라미터 확인
//            String provideConfirmIdStr = request.getParameter("provideConfirmId");
//            String providePostIdStr = request.getParameter("providePostId");
//
//            if (provideConfirmIdStr == null || provideConfirmIdStr.isEmpty() ||
//                providePostIdStr == null || providePostIdStr.isEmpty()) {
//                throw new IllegalArgumentException("필수 파라미터가 누락되었습니다.");
//            }
//
//            int provideConfirmId = Integer.parseInt(provideConfirmIdStr);
//            int providePostId = Integer.parseInt(providePostIdStr);
//            
//            int store;
//            try {
//                // `store` 값이 유효한 정수인지 확인
//                store = Integer.parseInt(request.getParameter("store"));
//            } catch (NumberFormatException e) {
//                throw new IllegalArgumentException("대여 제공 상점(store)의 값이 유효하지 않습니다: " + request.getParameter("store"));
//            }
//            String rentalPlace = request.getParameter("rentalPlace");
//            String returnPlace = request.getParameter("returnPlace");
//            java.sql.Date rentalDate = java.sql.Date.valueOf(request.getParameter("rentalDate"));
//            java.sql.Date returnDate = java.sql.Date.valueOf(request.getParameter("returnDate"));
//
//            // RentalProvidePost 객체 업데이트
//            RentalProvidePost rentalProvidePost = provideConfirmService.getRentalProvidePostById(providePostId);
//            rentalProvidePost.setPoints(store);
//            rentalProvidePost.setRentalLocation(rentalPlace);
//            rentalProvidePost.setReturnLocation(returnPlace);
//            rentalProvidePost.setRentalStartDate(rentalDate);
//            rentalProvidePost.setRentalEndDate(returnDate);
//
//            // 업데이트 수행
//            provideConfirmService.updateRentalDecisionDetails(rentalProvidePost);
//
//            // View 페이지로 이동
//            return "/rentalConfirm/success.jsp";
//        } catch (Exception e) {
//            e.printStackTrace();
//            request.setAttribute("error", "대여 정보를 업데이트하는 중 오류가 발생했습니다.");
//            return "/error.jsp";
//        }
//    }
//
//}
package controller.confirm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.service.ProvideConfirmService;

public class UpdateConfirmController implements Controller {
    private ProvideConfirmService provideConfirmService;

    public UpdateConfirmController() {
        this.provideConfirmService = new ProvideConfirmService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            // ProvideConfirm ID를 받아 처리
            int provideConfirmId = Integer.parseInt(request.getParameter("provideConfirmId"));
            
            // 대여 결정 세부 정보 업데이트 메서드 호출
            provideConfirmService.updateRentalDecisionDetails(provideConfirmId);
            
            return "/success.jsp"; // 성공 페이지로 리다이렉트
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "대여 결정 정보 업데이트 중 오류가 발생했습니다.");
            return "/error.jsp"; // 에러 페이지로 이동
        }
    }
}
