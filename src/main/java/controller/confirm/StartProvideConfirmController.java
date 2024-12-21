package controller.confirm;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import model.service.ProvideConfirmService;

public class StartProvideConfirmController implements Controller {
    private ProvideConfirmService provideConfirmService;

    public StartProvideConfirmController() {
        this.provideConfirmService = new ProvideConfirmService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {

            HttpSession session = request.getSession(false); // 이미 세션이 있으면 가져오고 없으면 null을 반환
            if (session == null || session.getAttribute("userId") == null) {
                // 세션이 없거나 userId가 없으면 로그인되지 않은 상태이므로 로그인 페이지로 리다이렉트
                request.setAttribute("error", "로그인이 필요합니다. 다시 로그인하세요.");
                return "/onboarding/loginForm.jsp"; // 로그인 폼 페이지로 이동
            }
            Integer userId = (Integer) session.getAttribute("userId");

            String providePostIdParam = request.getParameter("providePostId");
            if (providePostIdParam == null || providePostIdParam.isEmpty()) {
                request.setAttribute("error", "대여 글 ID가 필요합니다.");
                return "/error.jsp";
            }
            int providePostId2 = Integer.parseInt(providePostIdParam);
            System.out.println("넘어온 postID확인 Controller: Provide Post ID = " + providePostId2);

            int requesterId = userId; // 요청자 ID (테스트용)
            int providePostId = 1; // 제공 글 ID (테스트용)

            System.out.println("Controller: Requester ID = " + requesterId);
            System.out.println("Controller: Provide Post ID = " + providePostId);

            Map<String, Object> info = provideConfirmService.getRequesterAndProviderInfo(requesterId, providePostId);

            if (info == null) {
                request.setAttribute("error", "요청자 또는 제공자 정보가 없습니다.");
                return "/error.jsp";
            }

            // JSP에서 사용할 데이터 설정
            request.setAttribute("requester", info.get("requester"));
            request.setAttribute("provider", info.get("provider"));
            request.setAttribute("requesterId", requesterId); // 이 부분 확인
            request.setAttribute("providePostId", providePostId); // 이 부분 확인

            return "/provideConfirm/rentalConfirmPopup.jsp"; // JSP로 이동
        } catch (Exception e) {
            request.setAttribute("error", "대여 정보를 가져오는 중 오류가 발생했습니다.");
            return "/error.jsp";
        }
    }

}
