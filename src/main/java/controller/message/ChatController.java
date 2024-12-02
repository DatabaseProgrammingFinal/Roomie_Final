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
        Integer senderId = null;
        Integer recipientId = null;

        try {
            // URL에서 sender와 recipientId 파라미터 읽기
            senderId = Integer.parseInt(request.getParameter("sender"));
            recipientId = Integer.parseInt(request.getParameter("recipientId"));

            System.out.println("ChatController - Sender ID: " + senderId);
            System.out.println("ChatController - Recipient ID: " + recipientId);

            
            if (senderId == null || recipientId == null) {
                throw new IllegalArgumentException("sender 또는 recipient 값이 없습니다.");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw e;
        }

        // 메시지 필터링
        List<Message> messages = messageService.getMessagesWithDetails(senderId, recipientId);

        // JSP로 데이터 전달
        request.setAttribute("messages", messages);
        request.setAttribute("recipientId", recipientId);

        return "/message/chat.jsp";
    }


}
