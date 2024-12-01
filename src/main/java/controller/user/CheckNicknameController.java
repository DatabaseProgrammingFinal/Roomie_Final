package controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import controller.Controller;
import model.service.UserManager;

public class CheckNicknameController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(CheckNicknameController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserManager manager = UserManager.getInstance();
        String nickname = request.getParameter("nickname");

        boolean isAvailable = manager.isNicknameTaken(nickname); // login_id 중복 체크

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(isAvailable)); // JSON 응답 반환

        return null; // Ajax 요청이므로 JSP 이동 없음
    }
}
