package controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import model.domain.Community;
import model.domain.User;
import model.service.UserManager;

public class UpdateUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserManager manager = UserManager.getInstance();

        if (request.getMethod().equals("GET")) {
            // GET request: 회원정보 수정 form 요청
            String updateId = request.getParameter("login_id");

            log.debug("UpdateForm Request : {}", updateId);

            User user = manager.findUser(updateId);  // 수정하려는 사용자 정보 검색
            request.setAttribute("User", user);

            HttpSession session = request.getSession();
            if (UserSessionUtils.isLoginUser(updateId, session) ||
                UserSessionUtils.isLoginUser("admin", session)) {
                // 현재 로그인한 사용자가 수정 대상 사용자이거나 관리자인 경우 -> 수정 가능

                List<Community> commList = manager.findCommunityList();  // 커뮤니티 리스트 검색
                request.setAttribute("commList", commList);

                return "/User/updateForm.jsp";  // 검색한 사용자 정보 및 커뮤니티 리스트를 updateForm으로 전송     
            }

            // else (수정 불가능한 경우) 사용자 보기 화면으로 오류 메세지를 전달
            request.setAttribute("updateFailed", true);
            request.setAttribute("exception", 
                new IllegalStateException("타인의 정보는 수정할 수 없습니다."));
            return "/User/view.jsp";  // 사용자 보기 화면으로 이동 (forwarding)
        }

        // POST request (회원정보가 parameter로 전송됨)
        try {
            // 필수 정보 가져오기
            String loginId = request.getParameter("login_id");
            String password = request.getParameter("password");  // 비밀번호
            String nickname = request.getParameter("nickname");
            String dormitoryName = request.getParameter("dormitory_name");
            String roomNumber = request.getParameter("room_number");
            String profileUrl = request.getParameter("profile_url");
            
            // points는 정수형으로 변환
            int points = Integer.parseInt(request.getParameter("points"));
            
            // User 객체 생성
            int id = Integer.parseInt(request.getParameter("id"));  // id는 수정하지 않으므로 그대로 전달

            User updateUser = new User(
                id,
                loginId,
                password,  // 비밀번호
                nickname,
                dormitoryName,
                roomNumber,
                profileUrl,
                points
            );

            log.debug("Update User : {}", updateUser);

            manager.update(updateUser);  // 사용자 정보 업데이트

            return "redirect:/User/list";  // 성공 후 사용자 리스트로 리다이렉트
        } catch (Exception e) {
            log.error("Error updating user", e);
            request.setAttribute("updateFailed", true);
            request.setAttribute("exception", new IllegalStateException("회원 정보 수정 중 오류가 발생했습니다."));
            return "/User/updateForm.jsp";  // 오류 발생 시 수정 폼으로 돌아가기
        }
    }
}
