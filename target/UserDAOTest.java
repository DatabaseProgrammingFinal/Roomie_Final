import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.sql.SQLException;
import java.util.List;
import model.dao.UserDAO;
import model.domain.User;

public class UserDAOTest {

    private UserDAO userDAO;

    @Before
    public void setUp() throws SQLException {
        userDAO = new UserDAO();  // UserDAO 객체 생성

        // 테스트용 사용자 "testuser"가 데이터베이스에 없으면 생성
        if (!userDAO.existingUser("testuser")) {
            User newUser = new User(1, "testuser", "password123", "Test User", 
                                    "Test Dorm", "101", "http://profile.url", 100);
            int result = userDAO.create(newUser);
            assertEquals(1, result);  // create가 성공적으로 실행되었는지 확인
        }
    }

    @After
    public void tearDown() throws SQLException {
        // "testuser" 삭제 후 테스트 환경 정리
        User userToRemove = userDAO.findUser("testuser");
        if (userToRemove != null) {
            int result = userDAO.remove("testuser");
            assertEquals(1, result);  // remove가 성공적으로 실행되었는지 확인
        }
    }

    @Test
    public void testCreate() throws SQLException {
        // 새로운 User 객체 생성
        User newUser = new User(2, "newuser", "password123", "New User", 
                                "New Dorm", "102", "http://newprofile.url", 200);

        // create 메서드 실행
        int result = userDAO.create(newUser);

        // 결과 확인: 1이면 성공
        assertEquals(1, result);

        // 생성된 정보 확인
        User createdUser = userDAO.findUser("newuser");
        assertNotNull(createdUser);
        assertEquals("newuser", createdUser.getLoginId());
    }

    @Test
    public void testUpdate() throws SQLException {
        // 기존 사용자
        User existingUser = userDAO.findUser("testuser");
        assertNotNull(existingUser);

        existingUser.setNickname("Updated User");
        existingUser.setPoints(200);

        // update 메서드 실행
        int result = userDAO.update(existingUser);

        // 결과 확인: 1은 성공
        assertEquals(1, result);

        // 수정된 정보 확인
        User updatedUser = userDAO.findUser("testuser");
        assertEquals("Updated User", updatedUser.getNickname());
        assertEquals(200, updatedUser.getPoints());
    }

    @Test
    public void testRemove() throws SQLException {
        // 기존 사용자 정보 가져오기
        User userToRemove = userDAO.findUser("testuser");
        assertNotNull(userToRemove);

        // remove 메서드 실행
        int result = userDAO.remove("testuser");

        // 결과 확인: 1은 성공
        assertEquals(1, result);

        // 삭제된 사용자 정보 확인
        User removedUser = userDAO.findUser("testuser");
        assertNull(removedUser);
    }

    @Test
    public void testFindUser() throws SQLException {
        // 기존 사용자 정보 검색
        User foundUser = userDAO.findUser("testuser");
        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getLoginId());
    }

    @Test
    public void testFindUserList() throws SQLException {
        // 전체 사용자 목록 검색
        List<User> userList = userDAO.findUserList();

        // 사용자 목록에 데이터가 있는지 확인
        assertNotNull(userList);
        assertTrue(userList.size() > 0);
    }

    @Test
    public void testExistingUser() throws SQLException {
        // 존재하는 사용자 확인
        boolean exists = userDAO.existingUser("testuser");
        assertTrue(exists);

        // 존재하지 않는 사용자 확인
        boolean notExists = userDAO.existingUser("nonexistentuser");
        assertFalse(notExists);
    }
}
