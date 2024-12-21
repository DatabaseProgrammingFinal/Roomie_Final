package controller.post;

import model.domain.RentalRequestPost;
import model.service.RequestPostService;
import model.dao.JDBCUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateRequestPostController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(CreateRequestPostController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getMethod().equals("GET")) {
            // GET 요청인 경우, 폼을 보여줍니다.
            return "/requestpost/createForm.jsp";
        }

        try (Connection connection = JDBCUtil.getConnection()) {
            RequestPostService requestPostService = new RequestPostService();
            StringBuilder errorMsg = new StringBuilder();
            request.setCharacterEncoding("UTF-8");

            if (request.getMethod().equals("POST")) {
                try {
                    // 폼 데이터 가져오기
                    String title = request.getParameter("title");
                    String rentalItem = request.getParameter("rentalItem");
                    String content = request.getParameter("content");
                    String pointsStr = request.getParameter("points");
                    String startDateStr = request.getParameter("startDate");
                    String endDateStr = request.getParameter("endDate");
                    String rentalLocation = request.getParameter("rentalLocation");
                    String returnLocation = request.getParameter("returnLocation");

                    // 디버깅 로그
                    logger.debug("Title: {}", title);
                    logger.debug("RentalItem: {}", rentalItem);
                    logger.debug("Points: {}", pointsStr);
                    logger.debug("StartDate: {}", startDateStr);
                    logger.debug("EndDate: {}", endDateStr);
                    logger.debug("RentalLocation: {}", rentalLocation);
                    logger.debug("ReturnLocation: {}", returnLocation);
                    logger.debug("Content: {}", content);

                    // 유효성 검사
                    if (isInvalidInput(title)) errorMsg.append("제목을 입력해주세요. ");
                    if (isInvalidInput(rentalItem)) errorMsg.append("물품명을 입력해주세요. ");
                    if (isInvalidInput(pointsStr)) errorMsg.append("가격을 선택해주세요. ");
                    if (isInvalidInput(rentalLocation)) errorMsg.append("대여 장소를 입력해주세요. ");
                    if (isInvalidInput(returnLocation)) errorMsg.append("반납 장소를 입력해주세요. ");
                    if (isInvalidInput(startDateStr)) errorMsg.append("대여 시작일을 입력해주세요. ");
                    if (isInvalidInput(endDateStr)) errorMsg.append("반납 날짜를 입력해주세요. ");
                    if (isInvalidInput(content)) errorMsg.append("설명을 입력해주세요. ");

                    int points = 0;
                    try {
                        points = Integer.parseInt(pointsStr);
                    } catch (NumberFormatException e) {
                        errorMsg.append("가격은 숫자 형식이어야 합니다. ");
                    }

                    try {
                        java.sql.Date.valueOf(startDateStr);
                        java.sql.Date.valueOf(endDateStr);
                    } catch (IllegalArgumentException e) {
                        errorMsg.append("날짜 형식이 잘못되었습니다. (예: YYYY-MM-DD) ");
                    }

                    if (errorMsg.length() > 0) {
                        request.setAttribute("creationFailed", true);
                        request.setAttribute("errorMsg", errorMsg.toString().trim());
                        return "/requestpost/createForm.jsp";
                    }

                    // RentalRequestPost 객체 설정
                    RentalRequestPost post = new RentalRequestPost();
                    post.setTitle(title);
                    post.setRentalItem(rentalItem);
                    post.setPoints(points);
                    post.setRentalLocation(rentalLocation);
                    post.setReturnLocation(returnLocation);
                    post.setRentalStartDate(java.sql.Date.valueOf(startDateStr));
                    post.setRentalEndDate(java.sql.Date.valueOf(endDateStr));
                    post.setContent(content);
                    post.setStatus(1);
                    post.setRequesterId(1);

                    // 데이터베이스에 저장
                    int isCreated = requestPostService.createRentalRequestPost(post);

                    if (isCreated > 0) {
                        logger.debug("Rental Request Post created: {}", post);
                        return "redirect:/requestpost/list";
                    } else {
                        request.setAttribute("creationFailed", true);
                        request.setAttribute("errorMessage", "물건 요청 게시글 생성 중 오류가 발생했습니다.");
                        return "/requestpost/createForm.jsp";
                    }
                } catch (Exception e) {
                    logger.error("Error occurred while creating Rental Request Post", e);
                    request.setAttribute("errorMessage", "게시글 생성 중 오류가 발생했습니다.");
                    return "/error/errorPage.jsp";
                }
            }
        }

        // 기본적으로 폼 페이지를 반환
        return "/requestpost/createForm.jsp";
    }

    // 유효성 검사 메서드
    private boolean isInvalidInput(String input) {
        return input == null || input.trim().isEmpty();
    }
}
