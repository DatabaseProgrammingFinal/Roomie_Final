package controller.message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import model.service.MessageService;

public class SendMessageController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(SendMessageController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 요청에서 필요한 데이터를 가져옴
        String recipientIdStr = request.getParameter("recipient_id"); // 수신자 ID
        String content = request.getParameter("content"); // 메시지 내용
        String senderIdStr = request.getParameter("sender_id"); // 발신자 ID
        String requestPostIdStr = request.getParameter("request_post_id"); // 대여 요청 게시글 ID
        String providePostIdStr = request.getParameter("provide_post_id"); // 대여 제공 게시글 ID

        if (recipientIdStr == null || content == null || senderIdStr == null) {
            request.setAttribute("errorMessage", "필수 입력값이 누락되었습니다.");
            return "/message/chat.jsp"; // 입력 폼으로 다시 이동
        }

        // 데이터 형변환
        int recipientId = Integer.parseInt(recipientIdStr);
        int senderId = Integer.parseInt(senderIdStr);
        Integer requestPostId = (requestPostIdStr != null && !requestPostIdStr.isEmpty()) ? Integer.parseInt(requestPostIdStr) : null;
        Integer providePostId = (providePostIdStr != null && !providePostIdStr.isEmpty()) ? Integer.parseInt(providePostIdStr) : null;

        // MessageService를 통해 메시지 생성 요청
        MessageService messageService = new MessageService();
        try {
            messageService.createMessage(recipientId, senderId, content, requestPostId, providePostId);
            log.debug("Message sent successfully from {} to {}", senderId, recipientId);

            return "redirect:/message/chat"; 
        } catch (Exception e) {
            log.error("Failed to send message", e);
            request.setAttribute("errorMessage", "메시지 전송 중 오류가 발생했습니다.");
            return "/message/chat.jsp";
        }
    }
}
