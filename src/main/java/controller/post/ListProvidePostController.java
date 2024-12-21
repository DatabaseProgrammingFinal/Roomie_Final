package controller.post;

import model.service.ProvidePostService;
import model.domain.RentalProvidePost;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.List;

import controller.Controller;
import model.dao.JDBCUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListProvidePostController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(ListProvidePostController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // DB 연결
        try (Connection connection = JDBCUtil.getConnection()) {
            // ProvidePostService 인스턴스 생성
            ProvidePostService providePostService = new ProvidePostService();
            
            // 모든 대여글 조회
            List<RentalProvidePost> providePosts = providePostService.getAllRentalProvidePosts();
            
            // 조회된 대여글 목록을 request 속성에 저장
            request.setAttribute("providePosts", providePosts);

            // 콘솔에 대여글 목록 출력 (디버그용)
            logger.debug("Retrieved provide posts: {}", providePosts);

        } catch (Exception e) {
            // 오류 발생 시 스택 트레이스를 출력하고 로깅
            logger.error("Error occurred while retrieving rental provide posts", e);
            
            // 오류 페이지로 리다이렉트
            return "/error/errorPage.jsp";
        }
        
        // 대여글 목록을 보여줄 JSP 페이지로 이동
        return "/providepost/list.jsp";
    }
}
