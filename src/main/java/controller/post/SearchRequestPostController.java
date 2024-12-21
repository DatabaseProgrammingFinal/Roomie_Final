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

public class SearchRequestPostController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(SearchRequestPostController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try (Connection connection = JDBCUtil.getConnection()) {
            // RequestPostService 인스턴스 생성
            RequestPostService requestPostService = new RequestPostService();

            // 클라이언트에서 전달된 검색어를 가져옴
            String title = request.getParameter("title");

            List<RentalRequestPost> searchResults;
            if (title == null || title.trim().isEmpty()) {
                // 검색어가 없으면 전체 대여 요청글 조회
                searchResults = requestPostService.getAllRentalRequestPosts();
                logger.debug("No search title provided. Retrieved all posts.");
            } else {
                // 검색어로 대여 요청글 검색
                searchResults = requestPostService.searchRentalRequestPostsByTitle(title);
                logger.debug("Search results for title '{}': {}", title, searchResults);
            }

            // 검색 결과를 request 속성에 저장
            request.setAttribute("requestPosts", searchResults);

        } catch (Exception e) {
            // 오류 발생 시 로깅 및 오류 페이지로 이동
            logger.error("Error occurred while searching rental request posts", e);
            request.setAttribute("exception", e);
            return "/error/errorPage.jsp";
        }

        // 검색 결과 페이지로 이동
        return "/requestpost/searchResult.jsp";
    }
}
