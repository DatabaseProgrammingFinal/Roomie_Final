package controller.message;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.dao.MessageDAO;
import model.domain.Message;
import model.service.MessageService;

public class SearchMessageController implements Controller {
    private MessageService messageService;

    public SearchMessageController() {
        this.messageService = new MessageService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String query = request.getParameter("query"); // 검색어 가져오기
            
            List<Message> messages;
            if (query == null || query.trim().isEmpty()) {
                messages = null; // 검색어가 없으면 빈 메시지 반환
            } else {
                messages = messageService.searchMessages(query.trim()); // 검색 수행
            }
            
            System.out.println("searchController: " + (messages != null ? messages.size() : 0));
            
            request.setAttribute("messages", messages);
            return "/message/message_main.jsp"; // 검색 결과를 보여줄 JSP 페이지로 이동
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "검색 중 오류가 발생했습니다.");
            return "/error.jsp"; // 에러 페이지로 이동
        }
    }
}
