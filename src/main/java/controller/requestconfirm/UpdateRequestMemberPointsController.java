package controller.requestconfirm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.service.RequestConfirmService;

public class UpdateRequestMemberPointsController implements Controller {
    private RequestConfirmService requestConfirmService;

    public UpdateRequestMemberPointsController() {
        this.requestConfirmService = new RequestConfirmService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            // requestConfirmId를 요청에서 가져옴
            String requestConfirmIdParam = request.getParameter("requestConfirmId");
            if (requestConfirmIdParam == null || requestConfirmIdParam.isEmpty()) {
                throw new IllegalArgumentException("requestConfirmId ID가 유효하지 않습니다.");
            }

            int requestConfirmId = Integer.parseInt(requestConfirmIdParam);

            // 서비스 호출을 통해 상벌점 업데이트
            requestConfirmService.updateMemberPoints(requestConfirmId);

            // 성공 메시지를 설정하고 성공 페이지로 이동
            request.setAttribute("success", "상벌점 부과가 성공적으로 완료되었습니다.");
            return "redirect:/requestpost/list";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            request.setAttribute("error", "requestConfirmId ID가 유효하지 않습니다.");
            return "/error.jsp"; // 에러 페이지로 이동
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "상벌점 부과 중 오류가 발생했습니다.");
            return "/error.jsp"; // 에러 페이지로 이동
        }
    }
}
