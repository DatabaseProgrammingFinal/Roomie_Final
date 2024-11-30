package controller.post;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import model.domain.RentalProvidePost;
import model.service.ProvidePostService;

public class CreateProvidePostController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(CreateProvidePostController.class);
    private ProvidePostService providePostService;

    public CreateProvidePostController(ProvidePostService providePostService) {
        this.providePostService = providePostService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 입력값을 기반으로 RentalProvidePost 객체 생성
        RentalProvidePost post = new RentalProvidePost(
            Integer.parseInt(request.getParameter("id")), // ID
            request.getParameter("title"),               // 제목
            request.getParameter("rentalItem"),          // 대여 물품
            request.getParameter("content"),             // 내용
            Integer.parseInt(request.getParameter("points")), // 포인트
            java.sql.Date.valueOf(request.getParameter("startDate")), // 대여 시작일
            java.sql.Date.valueOf(request.getParameter("endDate")),   // 대여 종료일
            request.getParameter("location"),            // 위치
            Integer.parseInt(request.getParameter("status")),         // 상태
            Integer.parseInt(request.getParameter("providerId")),     // 제공자 ID
            request.getParameter("imageUrl")             // 이미지 URL
        );

        try {
            // 서비스 호출하여 대여글 등록
            providePostService.createRentalProvidePost(post);

            // 성공 로그 기록
            log.debug("RentalProvidePost created successfully: {}", post);

            // 성공 시 대여글 목록 페이지로 리디렉트
            return "redirect:/providepost/list";
        } catch (Exception e) {
            // 실패 시 입력 폼으로 포워딩하며, 에러 정보를 전달
            log.error("Failed to create RentalProvidePost", e);

            request.setAttribute("creationFailed", true);
            request.setAttribute("exception", e);
            request.setAttribute("post", post); // 사용자가 입력한 정보 유지

            return "/providepost/createForm.jsp"; // 입력 폼으로 포워딩
        }
    }
}
