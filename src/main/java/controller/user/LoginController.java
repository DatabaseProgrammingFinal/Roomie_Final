package controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import model.service.PasswordMismatchException;
import model.service.UserManager;
import model.service.UserNotFoundException;

public class LoginController implements Controller {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String login_id = request.getParameter("login_id");
		String password = request.getParameter("password");

		try {
			System.out.println("로그인 시도: " + login_id);

			// 로그인 처리 및 userId 가져오기
	        UserManager manager = UserManager.getInstance();
	        int userId = manager.login(login_id, password); // userId 반환

	        // 세션에 userId 저장
	        HttpSession session = request.getSession();
	        session.setAttribute("userId", userId);

	        return "redirect:/providepost/list";

		} catch (UserNotFoundException e) {
			// 실패 시 경고 메시지 설정
			request.setAttribute("exception", "아이디/비번이 틀렸습니다. 다시 입력하세요.");
			return "/onboarding/loginForm.jsp"; // 현재 화면 유지
		} catch (PasswordMismatchException e) {
			// 실패 시 경고 메시지 설정
			request.setAttribute("exception", "아이디/비번이 틀렸습니다. 다시 입력하세요.");
			return "/onboarding/loginForm.jsp"; // 현재 화면 유지
		}
	}
}
