package controller.post;

import model.domain.RentalProvidePost;
import model.service.ProvidePostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

@WebServlet("/providepost")
public class ProvidePostController extends HttpServlet {

    private ProvidePostService providePostService;

    @Override
    public void init() throws ServletException {
        try {
            // 데이터베이스 연결 초기화
            Connection connection = DriverManager.getConnection(
            		"jdbc:oracle:thin:@//dblab.dongduk.ac.kr:1521/orclpdb", "dbp240210", "21703");
            providePostService = new ProvidePostService(connection);
        } catch (Exception e) {
            throw new ServletException("DB 연결 실패", e);
        }
    }
    
    
    /**
     * GET 요청 처리 (조회 및 검색)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("view".equals(action)) {
                // 특정 게시글 조회
                int id = Integer.parseInt(request.getParameter("id"));
                RentalProvidePost post = providePostService.getRentalProvidePostById(id);
                request.setAttribute("post", post);
                request.getRequestDispatcher("/providepost/view.jsp").forward(request, response);

            } else if ("list".equals(action)) {
                // 전체 게시글 조회
                List<RentalProvidePost> posts = providePostService.getAllRentalProvidePosts();
                request.setAttribute("posts", posts);
                request.getRequestDispatcher("/providepost/list.jsp").forward(request, response);

            } else if ("search".equals(action)) {
                // 제목 검색
                String title = request.getParameter("title");
                List<RentalProvidePost> posts = providePostService.searchRentalProvidePostsByTitle(title);
                request.setAttribute("posts", posts);
                request.getRequestDispatcher("/providepost/search.jsp").forward(request, response);

            } else {
                // 기본 목록 페이지로 이동
                response.sendRedirect("/providepost?action=list");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "오류가 발생했습니다.");
        }
    }

    /**
     * POST 요청 처리 (대여 요청글 등록)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            try {
                // 입력 데이터 수집
                String title = request.getParameter("title");
                String rentalItem = request.getParameter("rentalItem");
                String content = request.getParameter("content");
                int points = Integer.parseInt(request.getParameter("points"));
                String rentalLocation = request.getParameter("rentalLocation");
                String imageUrl = request.getParameter("imageUrl");
                int providerId = Integer.parseInt(request.getParameter("providerId"));
                
                RentalProvidePost post = new RentalProvidePost();
                post.setTitle(title);
                post.setRentalItem(rentalItem);
                post.setContent(content);
                post.setPoints(points);
                post.setRentalLocation(rentalLocation);
                post.setImageUrl(imageUrl);
                post.setProviderId(providerId);
                
                boolean success = providePostService.createRentalProvidePost(post);
                if (success) {
                    response.sendRedirect("/providepost?action=list");
                } else {
                    response.sendRedirect("/providepost?action=create&error=true");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 요청입니다.");
            }
        }
    }
}
