package controller.confirm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import controller.Controller;
import model.service.ProvideConfirmService;

public class StartConfirmController implements Controller {
    private ProvideConfirmService provideConfirmService;

    public StartConfirmController() {
        this.provideConfirmService = new ProvideConfirmService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 요청자 ID와 제공 글 ID 파라미터 가져오기
        String requesterIdParam = request.getParameter("requesterId");
        String providePostIdParam = request.getParameter("providePostId");

        if (requesterIdParam == null || providePostIdParam == null) {
            request.setAttribute("error", "요청자 ID 또는 제공 글 ID가 누락되었습니다.");
            return "/error.jsp";
        }

        try {
            int requesterId = Integer.parseInt(requesterIdParam);
            int providePostId = Integer.parseInt(providePostIdParam);

            // 서비스에서 요청자와 제공자 정보를 가져옴
            Map<String, Object> info = provideConfirmService.getRequesterAndProviderInfo(requesterId, providePostId);

            if (info == null) {
                request.setAttribute("error", "요청자 또는 제공자 정보가 없습니다.");
                return "/error.jsp";
            }

            // JSP에서 사용할 데이터 설정
            request.setAttribute("requester", info.get("requester"));
            request.setAttribute("provider", info.get("provider"));

            return "/rentalConfirm/rentalConfirmPopup.jsp"; // JSP로 이동
        } catch (NumberFormatException e) {
            request.setAttribute("error", "요청자 ID 또는 제공 글 ID가 잘못된 형식입니다.");
            return "/error.jsp";
        }
    }
}
