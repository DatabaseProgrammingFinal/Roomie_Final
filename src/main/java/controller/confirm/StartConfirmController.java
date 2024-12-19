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
        // 고정된 providerId와 requesterId를 사용 (임시 테스트용)
        int requesterId = 2; // 요청자 ID (테스트용)
        int providePostId = 1; // 제공 글 ID (테스트용)

        try {
            // 서비스에서 요청자와 제공자 정보를 가져옴
            Map<String, Object> info = provideConfirmService.getRequesterAndProviderInfo(requesterId, providePostId);

            if (info == null) {
                request.setAttribute("error", "요청자 또는 제공자 정보가 없습니다.");
                return "/error.jsp";
            }

            // JSP에서 사용할 데이터 설정
            request.setAttribute("requester", info.get("requester"));
            request.setAttribute("provider", info.get("provider"));
            request.setAttribute("requesterId", requesterId);
            request.setAttribute("providePostId", providePostId);

            return "/rentalConfirm/rentalConfirmPopup.jsp"; // JSP로 이동
        } catch (Exception e) {
            request.setAttribute("error", "대여 정보를 가져오는 중 오류가 발생했습니다.");
            return "/error.jsp";
        }
    }
}
