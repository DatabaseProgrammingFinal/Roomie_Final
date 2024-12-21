package controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.confirm.CreateProvideConfirmController;
import controller.confirm.OutComeProvideConfirmController;
import controller.confirm.ProvideConfirmController;
import controller.confirm.ReturnProvideConfirmController;
import controller.confirm.StartProvideConfirmController;
import controller.confirm.UpdateProvideConfirmController;
import controller.confirm.UpdateProvideMemberPointsController;
import controller.confirm.ViewProvideConfirmController;
import controller.message.ChatController;
import controller.message.ListMessagesController;
import controller.message.SendMessageController;
import controller.post.CreateProvidePostController;
import controller.post.CreateRequestPostController;
import controller.post.ListProvidePostController;
import controller.post.ListRequestPostController;
import controller.post.SearchProvidePostController;
import controller.post.SearchRequestPostController;
import controller.post.ViewProvidePostController;
import controller.post.ViewRequestPostController;
import controller.user.CheckNicknameController;
import controller.user.CheckUserIdController;
import controller.user.LoginController;
import controller.user.RegisterUserController;

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

        // chat
        mappings.put("/message", new ListMessagesController());
        mappings.put("/message/chat", new ChatController());
        mappings.put("/message/send", new SendMessageController());

        // ProvidePost 관련 매핑 추가
        mappings.put("/providepost/list", new ListProvidePostController()); // 전체 대여글 조회
        mappings.put("/providepost/view", new ViewProvidePostController()); // 특정 대여글 조회
        mappings.put("/providepost/create", new CreateProvidePostController()); // 대여글 등록
        mappings.put("/providepost/search", new SearchProvidePostController()); // 제목으로 대여글 검색

        // mappings.put("/message/search", new SearchMessageController());

        mappings.put("/requestpost/list", new ListRequestPostController()); // 전체 대여글 조회
        mappings.put("/requestpost/view", new ViewRequestPostController()); // 특정 대여글 조회
        mappings.put("/requestpost/create", new CreateRequestPostController()); // 대여글 등록
        mappings.put("/requestpost/search", new SearchRequestPostController()); // 제목으로 대여글 검색

        // 대여확정 confrim관련 기능
        mappings.put("/provideConfirm/create", new CreateProvideConfirmController());
        mappings.put("/provideConfirm/start", new StartProvideConfirmController());
        mappings.put("/provideConfirm/return", new ReturnProvideConfirmController());
        mappings.put("/provideConfirm/view", new ViewProvideConfirmController());
        mappings.put("/provideConfirm/outcome", new OutComeProvideConfirmController());
        mappings.put("/provideConfirm/update", new UpdateProvideConfirmController());
        mappings.put("/provideConfirm/updatePoints", new UpdateProvideMemberPointsController());

        mappings.put("/provideconfirm", new ProvideConfirmController());

        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String uri) {
        if (uri.matches("^/providepost/view/\\d+$")) {
            return mappings.get("/providepost/view");
        }

        else if (uri.matches("^/requestpost/view/\\d+$")) {
            return mappings.get("/requestpost/view");
        }
        return mappings.get(uri);
    }

}