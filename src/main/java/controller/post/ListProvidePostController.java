package controller.post;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.domain.RentalProvidePost;
import model.service.ProvidePostService;

public class ListProvidePostController implements Controller {
    private ProvidePostService providePostService;

    public ListProvidePostController(ProvidePostService providePostService) {
        this.providePostService = providePostService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            // 서비스에서 모든 대여 제공글 가져오기
            List<RentalProvidePost> postList = providePostService.getAllRentalProvidePosts();

            // 디버깅용 출력
            System.out.println("Retrieved RentalProvidePost list: " + postList);

            // postList 객체를 request에 저장하여 대여 제공글 목록 페이지로 이동(forwarding)
            request.setAttribute("postList", postList);

            return "/providepost/list.jsp"; // 목록 페이지
        } catch (Exception e) {
            // 에러 기록
            System.err.println("Failed to retrieve RentalProvidePost list");
            e.printStackTrace();

            // 오류 시 에러 페이지로 이동
            request.setAttribute("exception", e);
            return "/error.jsp";
        }
    }
}
