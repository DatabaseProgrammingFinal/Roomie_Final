package controller.post;

import model.domain.RentalProvidePost;
import model.service.ProvidePostService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.Controller;

public class ViewProvidePostController implements Controller {
    private ProvidePostService providePostService;

    public ViewProvidePostController(ProvidePostService providePostService) {
        this.providePostService = providePostService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            // URL 파라미터에서 id를 가져옴
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                // id가 없는 경우 오류 페이지로 이동
                request.setAttribute("errorMessage", "게시글 ID가 없습니다.");
                return "/error.jsp";
            }

            // id를 정수로 파싱
            int id = Integer.parseInt(idParam);

            // 해당 게시글 조회
            RentalProvidePost post = providePostService.getRentalProvidePostById(id);
            if (post == null) {
                // 게시글이 없는 경우 오류 페이지로 이동
                request.setAttribute("errorMessage", "해당 게시글을 찾을 수 없습니다.");
                return "/error.jsp";
            }

            // 조회한 게시글을 request에 저장
            request.setAttribute("post", post);

            // 상세 조회 페이지로 이동
            return "/providepost/view.jsp";
        } catch (NumberFormatException e) {
            // id 파싱 오류 처리
            e.printStackTrace();
            request.setAttribute("errorMessage", "잘못된 게시글 ID 형식입니다.");
            return "/error.jsp";
        } catch (Exception e) {
            // 다른 예외 처리
            e.printStackTrace();
            request.setAttribute("exception", e);
            return "/error.jsp";
        }
    }
}
