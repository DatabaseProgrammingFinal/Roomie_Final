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

    		List<Community> commList = manager.findCommunityList();	// 커뮤니티 리스트 검색
			request.setAttribute("commList", commList);	
		
			return "/User/registerForm.jsp";   // 검색한 커뮤니티 리스트를 registerForm으로 전송     	
	    }	

    	// POST request (회원정보가 parameter로 전송됨)
       	User User = new User(
			request.getParameter("login_id"),
			request.getParameter("password"),
			request.getParameter("nickname"),
			request.getParameter("dormitory_name"),
			request.getParameter("room_number"),
			Integer.parseInt(request.getParameter("commId")));
		
        log.debug("Create User : {}", User);

		try {
			manager.create(User);
	        return "redirect:/User/list";	// 성공 시 사용자 리스트 화면으로 redirect
	        
		} catch (ExistingUserException e) {	// 예외 발생 시 회원가입 form으로 forwarding
            request.setAttribute("registerFailed", true);
			request.setAttribute("exception", e);
			request.setAttribute("User", User);
			return "/User/registerForm.jsp";
		}
    }
}

