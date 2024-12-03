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
        try {
            // 요청 파라미터 가져오기
        	
        	HttpSession session = request.getSession();
        	Integer userId = (Integer) session.getAttribute("userId");

        	if (userId == null) {
        	    throw new IllegalArgumentException("로그인된 사용자 ID가 세션에 없습니다.");
        	}
        	int senderId = userId;

            String recipientParam = request.getParameter("recipientId");
            String content = request.getParameter("content");
            
            
            System.out.println("DEBUG: senderParam = " + senderId);
            System.out.println("DEBUG: recipientParam = " + recipientParam);
            System.out.println("DEBUG: content = " + content);


            // 파라미터 검증
            
            if (recipientParam == null || recipientParam.isEmpty()) {
                throw new IllegalArgumentException("recipientId는 필수 파라미터입니다.");
            }

            int recipientId = Integer.parseInt(recipientParam);

            // UserDAO를 통해 User 객체 조회
            User sender = userDAO.findUserById(senderId);
            // User recipient = userDAO.findUserById(recipientId);
            User recipient = userDAO.findUserById(5); // 여기!!!
            
            System.out.println("DEBUG: sender = " + sender);
            System.out.println("DEBUG: recipient = " + recipient);

            if (sender == null || recipient == null) {
                throw new IllegalArgumentException("유효하지 않은 sender 또는 recipient ID입니다.");
            }

            // Message 객체 생성 및 설정
            Message message = new Message();
            message.setSender(sender); // 보낸 사람 설정
            message.setReceiver(recipient); // 받는 사람 설정
            message.setContent(content); // 메시지 내용 설정
            message.setSentDate(new java.util.Date()); // 현재 시간 설정
            message.setProvidePostId(1); // 기본값 설정

            // 메시지 저장
            messageService.createMessage(message);

            // 리다이렉트 URL 설정
            String redirectUrl = request.getContextPath() + "/message/chat?sender=" + senderId + "&recipientId=" + recipientId;
            response.sendRedirect(redirectUrl);
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("메시지 전송 중 오류가 발생했습니다.", e);
        }
    }
}
