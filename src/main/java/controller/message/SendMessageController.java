package controller.message;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import model.dao.UserDAO;
import model.domain.Message;
import model.domain.User;
import model.service.MessageService;

public class SendMessageController implements Controller {

    private MessageService messageService;
    private UserDAO userDAO; // UserDAO 추가

    public SendMessageController() {
        this.messageService = new MessageService(); // MessageService 초기화
        this.userDAO = new UserDAO(); // UserDAO 초기화
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("userId");

            String providePostIdParam = request.getParameter("providePostId");
            if (providePostIdParam == null || providePostIdParam.isEmpty()) {
                request.setAttribute("error", "대여 글 ID가 필요합니다.");
                return "/error.jsp";
            }

            int providePostId = Integer.parseInt(providePostIdParam);

            if (userId == null) {
                throw new IllegalArgumentException("로그인된 사용자 ID가 세션에 없습니다.");
            }

            System.out.println("DEBUG: 세션에서 가져온 userId = " + userId);

            // 요청 데이터 파싱
            int senderId = userId;
            int recipientId = Integer.parseInt(request.getParameter("recipientId"));
            String content = request.getParameter("content");

            // 사용자 및 메세지 객체 생성
            User sender = userDAO.findUserById(senderId);
            User recipient = userDAO.findUserById(recipientId);
//            User recipient = userDAO.findUserById(1);

            Message message = new Message();
            message.setSender(sender);
            message.setReceiver(recipient);
            message.setContent(content);
            message.setSentDate(new java.util.Date());
            message.setProvidePostId(providePostId); // 기본값 설정

            messageService.createMessage(message);

            // 메세지 내용만 반환
            PrintWriter out = response.getWriter();
            out.println(content);

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{ \"error\": \"메시지 전송 중 오류가 발생했습니다.\" }");
            return null;
        }
    }

}
