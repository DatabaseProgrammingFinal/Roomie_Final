package controller.post;

import model.domain.RentalRequestPost;
import model.service.RequestPostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

@WebServlet("/requestpost")
public class RequestPostController extends HttpServlet {

    private RequestPostService requestPostService;

    @Override
    public void init() throws ServletException {
        try {
            // 데이터베이스 연결 초기화
            Connection connection = DriverManager.getConnection(
            		"jdbc:oracle:thin:@//dblab.dongduk.ac.kr:1521/orclpdb", "dbp240210", "21703");
            requestPostService = new RequestPostService(connection);
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
                // ID로 특정 대여 요청글 조회
                int id = Integer.parseInt(request.getParameter("id"));
                RentalRequestPost post = requestPostService.getRentalRequestPostById(id);
                request.setAttribute("post", post);
                request.getRequestDispatcher("/requestpost/view.jsp").forward(request, response);

            } else if ("list".equals(action)) {
                // 전체 대여 요청글 목록 조회
                List<RentalRequestPost> posts = requestPostService.getAllRentalRequestPosts();
                request.setAttribute("posts", posts);
                request.getRequestDispatcher("/requestpost/list.jsp").forward(request, response);

            } else if ("search".equals(action)) {
                // 제목으로 대여 요청글 검색
                String title = request.getParameter("title");
                List<RentalRequestPost> posts = requestPostService.searchRentalRequestPostsByTitle(title);
                request.setAttribute("posts", posts);
                request.getRequestDispatcher("/requestpost/search.jsp").forward(request, response);

            } else {
                // 기본 목록 페이지로 이동
                response.sendRedirect("/requestpost?action=list");
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
                String rentalStartDate = request.getParameter("rentalStartDate");
                String rentalEndDate = request.getParameter("rentalEndDate");
                String rentalLocation = request.getParameter("rentalLocation");
                int requesterId = Integer.parseInt(request.getParameter("requesterId"));

                RentalRequestPost post = new RentalRequestPost();
                post.setTitle(title);
                post.setRentalItem(rentalItem);
                post.setContent(content);
                post.setPoints(points);
                post.setRentalStartDate(java.sql.Date.valueOf(rentalStartDate));
                post.setRentalEndDate(java.sql.Date.valueOf(rentalEndDate));
                post.setRentalLocation(rentalLocation);
                post.setRequesterId(requesterId);

                boolean success = requestPostService.createRentalRequestPost(post);
                if (success) {
                    response.sendRedirect("/requestpost?action=list");
                } else {
                    response.sendRedirect("/requestpost?action=create&error=true");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 요청입니다.");
            }
        }
    }
}
