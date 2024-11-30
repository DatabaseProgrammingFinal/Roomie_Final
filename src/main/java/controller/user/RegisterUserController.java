package controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import model.domain.Community;
import model.domain.User;
import model.service.ExistingUserException;
import model.service.UserManager;

public class RegisterUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(RegisterUserController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserManager manager = UserManager.getInstance();

        if (request.getMethod().equals("GET")) {
            // GET request: 회원정보 등록 form 요청
            log.debug("RegisterForm Request");

            List<Community> commList = manager.findCommunityList();  // 커뮤니티 리스트 검색
            request.setAttribute("commList", commList);

            return "/onboarding/registrationForm.jsp"; 
        }

        // POST request (회원정보가 parameter로 전송됨)
        try {
            // 사용자 입력 값 가져오기
        	int id = Integer.parseInt(request.getParameter("id"));
            String loginId = request.getParameter("login_id");
            String password = request.getParameter("password");
            String nickname = request.getParameter("nickname");
            String dormitoryName = request.getParameter("dormitory_name");
            String roomNumber = request.getParameter("room_number");
            String profileUrl = request.getParameter("profileUrl");
            int points = Integer.parseInt(request.getParameter("points"));

            // User 객체 생성
            User user = new User(id, loginId, password, nickname, dormitoryName, roomNumber, profileUrl, points);

            log.debug("Create User : {}", user);

            // 사용자 생성
            manager.create(user);

            return "redirect:/onboarding/loginForm";  // 성공 시 사용자 리스트 화면으로 redirect

        } catch (ExistingUserException e) {
            // 예외 발생 시 회원가입 form으로 forwarding
            request.setAttribute("registerFailed", true);
            request.setAttribute("exception", e);
            return "/onboarding/registrationForm.jsp";
        } catch (Exception e) {
            // 일반적인 예외 처리 (예: 입력값 문제)
            log.error("Error during user registration", e);
            request.setAttribute("registerFailed", true);
            request.setAttribute("exception", new IllegalStateException("회원 가입 중 오류가 발생했습니다."));
            return "/onboarding/registrationForm.jsp";  // 오류가 발생하면 form으로 돌아감
        }
    }
}
