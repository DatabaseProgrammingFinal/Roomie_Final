package controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.user.*;
import controller.comm.*;
import controller.message.ChatController;
import controller.message.ListMessagesController;
import controller.message.SendMessageController;
import controller.post.CreateProvidePostController;
import controller.post.ListProvidePostController;
import controller.user.LoginController;
import model.service.ProvidePostService;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    
    // 각 요청 uri에 대한 controller 객체를 저장할 HashMap 생성
    private Map<String, Controller> mappings = new HashMap<String, Controller>();

    public void initMapping() {
    	// 각 uri에 대응되는 controller 객체를 생성 및 저장
        mappings.put("/", new ForwardController("/onboarding/logoScreen.jsp"));
        mappings.put("/user/login", new LoginController());
        mappings.put("/user/register", new RegisterUserController());
        mappings.put("/user/checkLoginId", new CheckUserIdController());
        mappings.put("/user/checkNickname", new CheckNicknameController());
       
        mappings.put("/providepost/view", new ForwardController("/providepost/view.jsp")); // 상세 조회 페이지
        
        // chat
        mappings.put("/message", new ListMessagesController()); 
        mappings.put("/message/chat", new ChatController());
        mappings.put("/message/send", new SendMessageController());
        
        
        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String uri) {	
    	// 주어진 uri에 대응되는 controller 객체를 찾아 반환
        return mappings.get(uri);
    }
}