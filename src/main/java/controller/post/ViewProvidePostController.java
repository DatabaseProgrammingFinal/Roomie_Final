package controller.post;

import model.domain.RentalProvidePost;
import model.service.ProvidePostService;
import model.dao.JDBCUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

import controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewProvidePostController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(ViewProvidePostController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // URL에서 ID 추출
        String uri = request.getRequestURI();
        String[] segments = uri.split("/");
        String idParam = segments[segments.length - 1];

        try (Connection connection = JDBCUtil.getConnection()) {
            ProvidePostService providePostService = new ProvidePostService();

            if (idParam == null || idParam.isEmpty()) {
                request.setAttribute("errorMessage", "게시글 ID가 없습니다.");
                logger.error("No post ID provided in request");
                return "/error/errorPage.jsp";
            }

            int id = Integer.parseInt(idParam);

            RentalProvidePost post = providePostService.getRentalProvidePostById(id);
            if (post == null) {
                request.setAttribute("errorMessage", "해당 게시글을 찾을 수 없습니다.");
                logger.error("Post with ID {} not found", id);
                return "/error/errorPage.jsp";
            }

            request.setAttribute("post", post);
            logger.debug("Retrieved post: {}", post);

        } catch (NumberFormatException e) {
            logger.error("Invalid post ID format", e);
            request.setAttribute("errorMessage", "잘못된 게시글 ID 형식입니다.");
            return "/error/errorPage.jsp";
        } catch (Exception e) {
            logger.error("Error occurred while retrieving the post", e);
            request.setAttribute("errorMessage", "게시글 조회 중 오류가 발생했습니다.");
            return "/error/errorPage.jsp";
        }

        return "/providepost/view.jsp";
    }
}
