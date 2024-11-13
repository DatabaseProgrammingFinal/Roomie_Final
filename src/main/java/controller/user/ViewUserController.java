package controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.domain.User;
import model.service.UserManager;
import model.service.UserNotFoundException;

public class ViewUserController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {			
    	// 로그인 여부 확인
    	if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/User/login/form";		// login form 요청으로 redirect
        }
    	
		UserManager manager = UserManager.getInstance();
		String login_id = request.getParameter("login_id");
		
    	User User = null;
		try {
			User = manager.findUser(login_id);	// 사용자 정보 검색
		} catch (UserNotFoundException e) {				
	        return "redirect:/User/list";
		}	
		
		request.setAttribute("User", User);		// 사용자 정보 저장				
		return "/User/view.jsp";				// 사용자 보기 화면으로 이동
    }
}
