package controller.message;

import model.dao.UserDAO;
import model.domain.Message;
import model.domain.User;
import model.service.MessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;

public class SendMessageController implements Controller {
	
    private MessageService messageService;
    private UserDAO userDAO; // UserDAO 추가

    public SendMessageController() {
        this.messageService = new MessageService(); // MessageService 초기화
        this.userDAO = new UserDAO(); // UserDAO 초기화
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("userId");

            if (userId == null) {
                throw new IllegalArgumentException("로그인된 사용자 ID가 세션에 없습니다.");
            }
            System.out.println("DEBUG: 세션에서 가져온 userId = " + userId);

            int senderId = userId;
            int recipientId = Integer.parseInt(request.getParameter("recipientId"));
            String content = request.getParameter("content");

            User sender = userDAO.findUserById(senderId);
            //User recipient = userDAO.findUserById(recipientId);
            User recipient = userDAO.findUserById(4);
            
            Message message = new Message();
            message.setSender(sender);
            message.setReceiver(recipient);
            message.setContent(content);
            message.setSentDate(new java.util.Date());
            message.setProvidePostId(1); // 기본값 설정

            messageService.createMessage(message);

            // JSON 형식으로 응답 반환
            String jsonResponse = "{ \"senderId\": " + senderId + 
                                  ", \"recipientId\": " + recipientId + 
                                  ", \"content\": \"" + content + "\" }";
            response.getWriter().write(jsonResponse);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{ \"error\": \"메시지 전송 중 오류가 발생했습니다.\" }");
            return null;
        }
    }


}
