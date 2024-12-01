package controller.message;

import model.domain.Message;
import model.service.MessageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;

import java.util.List;

public class ChatController implements Controller {
    private MessageService messageService;

    public ChatController() {
        this.messageService = new MessageService(); // MessageService 인스턴스 생성
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 요청 파라미터 가져오기
        Integer userId = null;
        Integer recipientId = null;

        try {
            String userIdParam = request.getParameter("userId");
            String recipientIdParam = request.getParameter("recipientId");

            if (userIdParam != null && !userIdParam.isEmpty()) {
                userId = Integer.parseInt(userIdParam);
            } else {
                userId = 1; // 기본값 설정
                System.out.println("DEBUG: userId가 null이므로 기본값 1로 설정");
            }

            if (recipientIdParam != null && !recipientIdParam.isEmpty()) {
                recipientId = Integer.parseInt(recipientIdParam);
            } else {
                throw new IllegalArgumentException("recipientId는 필수 파라미터입니다.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR: 파라미터 값이 유효한 숫자가 아닙니다.");
            e.printStackTrace();
            throw e;
        }

        String filter = "all"; // 기본값 설정

        // 메시지 조회
        List<Message> messages = messageService.getMessages(userId, filter);

        // JSP로 데이터 전달
        request.setAttribute("messages", messages);
        request.setAttribute("recipientId", recipientId);

        return "/message/chat.jsp"; // 채팅 화면으로 이동
    }

}
