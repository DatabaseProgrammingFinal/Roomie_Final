package controller.message;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import model.dao.UserDAO;
import model.domain.Message;
import model.service.MessageService;

public class ListMessagesController implements Controller {
    private MessageService messageService;

    public ListMessagesController() {
        this.messageService = new MessageService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            // 세션에서 login_id 가져오기
            HttpSession session = request.getSession();
            String loginId = (String) session.getAttribute("userId");
            if (loginId == null) {
                throw new IllegalStateException("로그인된 사용자가 아닙니다.");
            }
            System.out.println("ListMessages: 세션에서 가져온 userId = " + loginId);

            // login_id로 member 테이블에서 id 가져오기
            UserDAO userDAO = new UserDAO();
            int userId = userDAO.findUserIdByLoginId(loginId);

            // 필터 처리
            String filter = request.getParameter("filter"); // "all", "sent", "received" 값 중 하나
            List<Message> messages = messageService.getMessages(userId, filter);

            // JSP로 메시지 전달
            request.setAttribute("messages", messages);

            return "/message/message_main.jsp"; // 메시지 목록 JSP로 이동
        } catch (IllegalStateException e) {
            System.out.println("ListMessages: 세션로그인안돼");
        	request.setAttribute("error", "로그인이 필요합니다. 다시 로그인하세요.");
            return "/onboarding/loginForm.jsp"; // 로그인 페이지로 이동
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "메시지를 불러오는 도중 오류가 발생했습니다.");
            return "/error.jsp"; // 에러 페이지로 이동
        }
    }

}
