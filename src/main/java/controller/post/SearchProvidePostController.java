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

public class SearchProvidePostController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(SearchProvidePostController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try (Connection connection = JDBCUtil.getConnection()) {
            // ProvidePostService 인스턴스 생성
            ProvidePostService providePostService = new ProvidePostService();

            // 클라이언트에서 전달된 검색어를 가져옴
            String title = request.getParameter("title");

            List<RentalProvidePost> searchResults;
            if (title == null || title.trim().isEmpty()) {
                // 검색어가 없으면 전체 대여글 조회
                searchResults = providePostService.getAllRentalProvidePosts();
                logger.debug("No search title provided. Retrieved all posts.");
            } else {
                // 검색어로 대여글 검색
                searchResults = providePostService.searchRentalProvidePostsByTitle(title);
                logger.debug("Search results for title '{}': {}", title, searchResults);
            }

            // 검색 결과를 request 속성에 저장
            request.setAttribute("providePosts", searchResults);

        } catch (Exception e) {
            // 오류 발생 시 로깅 및 오류 페이지로 이동
            logger.error("Error occurred while searching rental provide posts", e);
            request.setAttribute("exception", e);
            return "/error/errorPage.jsp";
        }

        // 검색 결과 페이지로 이동
        return "/providepost/searchResult.jsp";
    }
}
