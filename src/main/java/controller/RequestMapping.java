package controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.user.*;
import controller.comm.*;
import controller.post.ProvidePostController;
import controller.user.LoginController;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    
    // 각 요청 uri에 대한 controller 객체를 저장할 HashMap 생성
    private Map<String, Controller> mappings = new HashMap<String, Controller>();

    public void initMapping() {
    	// 각 uri에 대응되는 controller 객체를 생성 및 저장
        mappings.put("/", new ForwardController("/onboarding/logoScreen.jsp"));
        System.out.println("onboarding");
        mappings.put("/user/login", new LoginController());
        mappings.put("/user/register", new RegisterUserController());
        
//        // message
//        mappings.put("/message", new sendMessageController()); // 메시지 생성
//        mappings.put("/message/{id}", ViewMessagesController()); // 메시지 조회
//        
        
     // ProvidePost 관련 매핑 추가
        mappings.put("/providepost", new ForwardController("/providepost/list.jsp")); // 목록 페이지
        mappings.put("/providepost/view", new ForwardController("/providepost/view.jsp")); // 상세 조회 페이지
        mappings.put("/providepost/create/form", new ForwardController("/providepost/createForm.jsp")); // 작성 페이지
        mappings.put("/providepost/action", new ProvidePostController()); // POST 처리 (생성 및 기타 작업)

        
        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String uri) {	
    	// 주어진 uri에 대응되는 controller 객체를 찾아 반환
        return mappings.get(uri);
    }
}