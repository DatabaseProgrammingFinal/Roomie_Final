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
        	HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                request.setAttribute("error", "로그인이 필요합니다. 다시 로그인하세요.");
                return "/onboarding/loginForm.jsp";
            }

            Integer userId = (Integer) session.getAttribute("userId");
            List<Message> messages = messageService.getLatestMessages(userId);
            request.setAttribute("messages", messages);

            return "/message/message_main.jsp"; // 메세지로 가라. 

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
