package controller.post;

import model.domain.RentalProvidePost;
import model.service.ProvidePostService;

import controller.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class SearchProvidePostController implements Controller {
    private ProvidePostService providePostService;

    // 기본 생성자
    public SearchProvidePostController() {
    }

    // 매개변수 생성자
    public SearchProvidePostController(ProvidePostService providePostService) {
        this.providePostService = providePostService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            // 클라이언트에서 전달된 검색어를 가져옴
            String title = request.getParameter("title");

            if (title == null || title.trim().isEmpty()) {
                // 검색어가 없거나 빈 문자열인 경우, 전체 게시글 목록 반환
                request.setAttribute("posts", providePostService.getAllRentalProvidePosts());
            } else {
                // 검색어로 게시글 검색
                List<RentalProvidePost> searchResults = providePostService.searchRentalProvidePostsByTitle(title);

                // 검색 결과를 request에 저장
                request.setAttribute("posts", searchResults);
            }

            // 검색 결과 페이지로 이동
            return "/providepost/searchResult.jsp";
        } catch (Exception e) {
            // 예외 발생 시 에러 페이지로 이동
            e.printStackTrace();

            // 예외 정보를 request에 저장
            request.setAttribute("exception", e);
            return "/error.jsp";
        }
    }
}
