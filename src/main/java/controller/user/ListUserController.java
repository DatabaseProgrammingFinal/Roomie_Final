package controller.User;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.Controller;
import model.domain.User;
import model.service.UserManager;

public class ListUserController implements Controller {
	// private static final int countPerPage = 100;	// 한 화면에 출력할 사용자 수

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)	throws Exception {
		// 로그인 여부 확인
    	if (!UserSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/User/login/form";		// login form 요청으로 redirect
        }
    	
    	/*
    	String currentPageStr = request.getParameter("currentPage");	
		int currentPage = 1;
		if (currentPageStr != null && !currentPageStr.equals("")) {
			currentPage = Integer.parseInt(currentPageStr);
		}		
    	*/
    	
		UserManager manager = UserManager.getInstance();
		List<User> UserList = manager.findUserList();
		// List<User> UserList = manager.findUserList(currentPage, countPerPage);

		// UserList 객체와 현재 로그인한 사용자 ID를 request에 저장하여 전달
		request.setAttribute("UserList", UserList);				
		request.setAttribute("curlogin_id", 
				UserSessionUtils.getLoginlogin_id(request.getSession()));		

		// 사용자 리스트 화면으로 이동(forwarding)
		return "/User/list.jsp";        
    }
}
