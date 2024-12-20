package controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import model.domain.User;
import model.service.ExistingUserException;
import model.service.UserManager;

public class RegisterUserController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(RegisterUserController.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    UserManager manager = UserManager.getInstance();

	    if (request.getMethod().equals("POST")) {
	    	StringBuilder errorMsg = new StringBuilder();
	        try {
	            // 폼 데이터 가져오기
	            String loginId = request.getParameter("login_id");
	            String password = request.getParameter("password");
	            String nickname = request.getParameter("nickname");
	            String dormitoryName = request.getParameter("dormitory_name");
	            String roomNumber = request.getParameter("room_number");
	            String profileUrl = "default.jpg"; // 기본 값 설정
	            int points = 0; // 기본 값 설정
	            
	            // **1. 유효성 검사: 필수 값 체크**
	            if (loginId == null || loginId.trim().isEmpty() ||
	                password == null || password.trim().isEmpty() ||
	                nickname == null || nickname.trim().isEmpty() ||
	                dormitoryName == null || dormitoryName.trim().isEmpty() ||
	                roomNumber == null || roomNumber.trim().isEmpty()) {

	            	errorMsg.append("모든 내용을 입력해주세요. ");
	            }

	            // **2. 비밀번호 유효성 검사**
	            if (!password.matches("^(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]{4,16}$")) {
	                errorMsg.append("비밀번호는 4~16자 이내로 작성하고 숫자와 특수문자를 포함해야 합니다. ");
	            }

	            // **3. 아이디 중복 확인**
	            if (manager.isIdTaken(loginId)) {
	            	errorMsg.append("이미 사용 중인 아이디입니다. ");
	            }

	            // **4. 닉네임 중복 확인**
	            if (manager.isNicknameTaken(nickname)) {
	            	errorMsg.append("이미 사용 중인 닉네임입니다. ");
	            }

	            if (errorMsg.length() > 0) {
	                request.setAttribute("registerFailed", true);
	                request.setAttribute("errorMsg", errorMsg.toString().trim());
	                return "/onboarding/registrationForm.jsp";
	            }
	            
	            // **5. 유효성 통과 후 회원 가입 진행**
	            User user = new User(loginId, password, nickname, dormitoryName, roomNumber, profileUrl, points);
	            manager.create(user);

	            // 성공 시 로그인 페이지로 리다이렉트
	            return "redirect:/user/login";

	        } catch (ExistingUserException e) {
	            request.setAttribute("registerFailed", true);
	            request.setAttribute("exception", e.getMessage());
	            return "/onboarding/registrationForm.jsp";
	        }
	    }
	    return "/onboarding/registrationForm.jsp";
	}

}
