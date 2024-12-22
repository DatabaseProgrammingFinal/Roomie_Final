package controller.message;

import model.domain.Message;
import model.domain.User;
import model.service.MessageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;

import java.util.ArrayList;
import java.util.List;

public class ChatController implements Controller {
    private MessageService messageService;

    public ChatController() {
        this.messageService = new MessageService(); // MessageService 인스턴스 생성
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 현재 로그인한 사용자 정보 가져오기
        Integer senderId = (Integer) request.getSession().getAttribute("userId"); // 세션에서 로그인된 사용자 ID 가져오기
        if (senderId == null) {
            throw new IllegalArgumentException("로그인된 사용자 정보가 없습니다.");
        }

        // 요청 파라미터 읽기
        String postType = request.getParameter("postType"); // type 파라미터
        String recipientIdParam = request.getParameter("recipientId");
        String postIdParam = request.getParameter("postId");

        // 파라미터 유효성 검사
        if (postType == null || (!postType.equals("provide") && !postType.equals("request"))) {
            throw new IllegalArgumentException("유효하지 않은 postType 값입니다. 'provide' 또는 'request'를 전달해주세요.");
        }
        if (recipientIdParam == null || postIdParam == null) {
            throw new IllegalArgumentException("recipientId 및 postId 값이 필요합니다.");
        }

        int recipientId = Integer.parseInt(recipientIdParam);
        int postId = Integer.parseInt(postIdParam);

        System.out.println("DEBUG: Parsed postId = " + postId + ", recipientId = " + recipientId);
        
        Integer providePostId = null;
        Integer requestPostId = null;

        
        if ("provide".equals(postType)) {
        	providePostId = postId;
        } else if ("request".equals(postType)) {
        	requestPostId = postId;
        }
        
     // 올바른 postId 검증
        if (providePostId == null && requestPostId == null) {
            throw new IllegalArgumentException("Invalid postId: neither requestPostId nor providePostId exists.");
        }
        
        // 대화 상대 정보 로드
        User recipient = messageService.getUserById(recipientId);
        if (recipient == null) {
            throw new IllegalArgumentException("수신자 정보를 찾을 수 없습니다.");
        }

        // 메시지 가져오기 (없으면 빈 리스트 반환)
        List<Message> chatMessages = messageService.getChatMessagesBetween(senderId, recipientId, postId);
        if (chatMessages == null) {
            chatMessages = new ArrayList<>(); // 메시지가 없을 경우 빈 리스트 생성
        }
        // JSP로 데이터 전달
        request.setAttribute("messages", chatMessages);
        request.setAttribute("recipient", recipient);
        request.setAttribute("postId", postId);
        request.setAttribute("postType", postType); // type 값을 JSP로 전달
        System.out.println("DEBUG: postId = " + postId + ", postType = " + postType + "reciepient" +recipient);

        return "/message/chat.jsp"; // 채팅 JSP 경로
    }
}
