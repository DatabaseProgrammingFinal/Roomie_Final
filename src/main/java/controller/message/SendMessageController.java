package controller.message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.dao.UserDAO;
import model.domain.Message;
import model.domain.User;
import model.service.MessageService;

public class SendMessageController implements Controller {

	private MessageService messageService;
	private UserDAO userDAO;

	public SendMessageController() {
		this.messageService = new MessageService(); // MessageService 초기화
		this.userDAO = new UserDAO(); // UserDAO 초기화
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try {
			// 세션에서 userId 가져오기
			Integer userId = (Integer) request.getSession().getAttribute("userId");
			if (userId == null) {
				throw new IllegalArgumentException("로그인된 사용자 정보가 없습니다. 다시 로그인해주세요.");
			}

			// 요청 데이터 읽기
			String type = request.getParameter("postType"); // 요청 파라미터에서 type 가져오기
			String postIdParam = request.getParameter("postId");
			String recipientIdParam = request.getParameter("recipientId");
			String content = request.getParameter("content");

			// 필수 파라미터 검증
			if (type == null || (!type.equals("provide") && !type.equals("request"))) {
				throw new IllegalArgumentException("잘못된 type 값입니다. 'provide' 또는 'request'를 전달해주세요.");
			}
			if (postIdParam == null || postIdParam.isEmpty()) {
				throw new IllegalArgumentException("postId 값이 누락되었습니다.");
			}
			if (recipientIdParam == null || recipientIdParam.isEmpty()) {
				throw new IllegalArgumentException("recipientId 값이 누락되었습니다.");
			}
			if (content == null || content.trim().isEmpty()) {
				throw new IllegalArgumentException("메시지 내용(content)을 입력해주세요.");
			}

			// 파라미터 파싱
			int postId = Integer.parseInt(postIdParam);
			int recipientId = Integer.parseInt(recipientIdParam);

			// type에 따라 postId 설정
			Integer providePostId = null;
			Integer requestPostId = null;
			
			if ("provide".equals(type)) {
				providePostId = postId;
			} else if ("request".equals(type)) {
				requestPostId = postId;
			}

			// Sender와 Receiver 정보 가져오기
			User sender = userDAO.findUserById(userId);
			User recipient = userDAO.findUserById(recipientId);

			if (sender == null || recipient == null) {
				throw new IllegalArgumentException("발신자 또는 수신자 정보를 찾을 수 없습니다.");
			}

			// Message 객체 생성 및 설정
			Message message = new Message();
			message.setSender(sender);
			message.setReceiver(recipient);
			message.setContent(content);
			message.setSentDate(new java.util.Date());
			message.setProvidePostId(providePostId);
			message.setRequestPostId(requestPostId);

			// Message 저장
			messageService.createMessage(message);

			System.out.println("DEBUG: postType = " + type);
			System.out.println("DEBUG: postId = " + postIdParam);
			System.out.println("DEBUG: recipientId = " + recipientIdParam);
			System.out.println("DEBUG: content = " + content);

			
			// 성공 응답 반환
			String jsonResponse = String.format("{\"message\": \"메시지가 성공적으로 전송되었습니다.\", \"content\": \"%s\"}", content);
			response.getWriter().write(jsonResponse);
			return null;
			
		

		} catch (IllegalArgumentException e) {
			// 클라이언트 오류 처리
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
			return null;
		} catch (Exception e) {
			// 서버 오류 처리
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"error\": \"메시지 전송 중 서버 오류가 발생했습니다.\"}");
			return null;
		}

	}
	
}
