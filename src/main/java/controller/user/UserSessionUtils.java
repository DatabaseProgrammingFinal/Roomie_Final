package controller.User;

import javax.servlet.http.HttpSession;

public class UserSessionUtils {
    public static final String User_SESSION_KEY = "login_id";

    /* 현재 로그인한 사용자의 ID를 구함 */
    public static String getLoginlogin_id(HttpSession session) {
        String login_id = (String)session.getAttribute(User_SESSION_KEY);
        return login_id;
    }

    /* 로그인한 상태인지를 검사 */
    public static boolean hasLogined(HttpSession session) {
        if (getLoginlogin_id(session) != null) {
            return true;
        }
        return false;
    }

    /* 현재 로그인한 사용자의 ID가 login_id인지 검사 */
    public static boolean isLoginUser(String login_id, HttpSession session) {
        String loginUser = getLoginlogin_id(session);
        if (loginUser == null) {
            return false;
        }
        return loginUser.equals(login_id);
    }
}
