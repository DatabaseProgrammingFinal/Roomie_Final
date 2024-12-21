package controller.post;

import model.service.RequestPostService;
import model.domain.RentalRequestPost;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.List;

import controller.Controller;
import model.dao.JDBCUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListRequestPostController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(ListRequestPostController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // DB 연결
        try (Connection connection = JDBCUtil.getConnection()) {
            // RequestPostService 인스턴스 생성
            RequestPostService requestPostService = new RequestPostService();
            
            // 모든 요청글 조회
            List<RentalRequestPost> requestPosts = requestPostService.getAllRentalRequestPosts();
            
            // 조회된 요청글 목록을 request 속성에 저장
            request.setAttribute("requestPosts", requestPosts);

            // 콘솔에 요청글 목록 출력 (디버그용)
            logger.debug("Retrieved request posts: {}", requestPosts);

        } catch (Exception e) {
            // 오류 발생 시 스택 트레이스를 출력하고 로깅
            logger.error("Error occurred while retrieving rental request posts", e);
            
            // 오류 페이지로 리다이렉트
            return "/error/errorPage.jsp";
        }
        
        // 요청글 목록을 보여줄 JSP 페이지로 이동
        return "/requestpost/list.jsp";
    }
}
