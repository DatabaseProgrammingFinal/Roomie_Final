package controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.user.*;
import controller.comm.*;
import controller.confirm.ConfirmController;
import controller.confirm.CreateConfirmController;
import controller.confirm.StartConfirmController;
import controller.confirm.ReturnConfirmController;
import controller.confirm.ViewConfirmController;
import controller.confirm.OutComeConfirmController;
import controller.confirm.UpdateConfirmController;
import controller.confirm.UpdateMemberPointsController;
import controller.user.LoginController;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    
    // 각 요청 uri에 대한 controller 객체를 저장할 HashMap 생성
    private Map<String, Controller> mappings = new HashMap<String, Controller>();

    public void initMapping() {
    	// 각 uri에 대응되는 controller 객체를 생성 및 저장
        mappings.put("/", new ForwardController("index.jsp"));
        mappings.put("/User/login/form", new ForwardController("/User/loginForm.jsp"));
        mappings.put("/User/login", new LoginController());
        mappings.put("/User/logout", new LogoutController());
        mappings.put("/User/list", new ListUserController());
        mappings.put("/User/view", new ViewUserController());
        
        // 회원 가입 폼 요청과 가입 요청 처리 병합 (폼에 커뮤니티 선택 메뉴 추가를 위함)
//      mappings.put("/User/register/form", new ForwardController("/User/registerForm.jsp"));
//      mappings.put("/User/register", new RegisterUserController());
        mappings.put("/User/register", new RegisterUserController());

        // 사용자 정보 수정 폼 요청과 수정 요청 처리 병합
//      mappings.put("/User/update/form", new UpdateUserFormController());
//      mappings.put("/User/update", new UpdateUserController());        
        mappings.put("/User/update", new UpdateUserController());
        
        mappings.put("/User/delete", new DeleteUserController());
        
        // 커뮤니티 관련 request URI 추가
        mappings.put("/community/list", new ListCommunityController());
        mappings.put("/community/view", new ViewCommunityController());
        mappings.put("/community/create/form", new ForwardController("/community/creationForm.jsp"));
        mappings.put("/community/create", new CreateCommunityController());
        mappings.put("/community/update", new UpdateCommunityController());
        
        
        //대여확정 confrim관련 기능
        mappings.put("/confirm/create", new CreateConfirmController());
        mappings.put("/confirm/start", new StartConfirmController());
        mappings.put("/confirm/return", new ReturnConfirmController());
        mappings.put("/confirm/view", new ViewConfirmController());
        mappings.put("/confirm/outcome", new OutComeConfirmController());
        mappings.put("/confirm/update", new UpdateConfirmController());
        mappings.put("/confirm/updatePoints", new UpdateMemberPointsController());

        mappings.put("/confirm", new ConfirmController());
        
        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String uri) {	
    	// 주어진 uri에 대응되는 controller 객체를 찾아 반환
        return mappings.get(uri);
    }
}