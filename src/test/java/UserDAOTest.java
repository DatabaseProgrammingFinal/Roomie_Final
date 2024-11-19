import static org.junit.Assert.*;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.dao.UserDAO;
import model.domain.User;

public class UserDAOTest {
    private UserDAO userDAO;
    private User testUser;

    @Before
    public void setUp() throws Exception {
        userDAO = new UserDAO();

        // 테스트에 사용할 사용자 데이터 생성
        testUser = new User(
            1,                       // id
            "test_user",             // loginId
            "password123",           // password
            "TestNickname",          // nickname
            "TestDormitory",         // dormitoryName
            "A101",                  // roomNumber
            "http://example.com",    // profileUrl
            100                      // points
        );

        // 테스트 전에 동일한 사용자 제거 (중복 방지)
        userDAO.remove(testUser.getLoginId());
    }

    @After
    public void tearDown() throws Exception {
        // 테스트 후 데이터 정리
        userDAO.remove(testUser.getLoginId());
    }

    @Test
    public void testCreate() throws SQLException {
        // 새로운 사용자 생성
        int result = userDAO.create(testUser);
        assertEquals(1, result); // 삽입 성공 시 1 반환

        // 생성된 사용자 조회
        User retrievedUser = userDAO.findUser(testUser.getLoginId());
        assertNotNull(retrievedUser);
        assertEquals(testUser.getLoginId(), retrievedUser.getLoginId());
        assertEquals(testUser.getNickname(), retrievedUser.getNickname());
        assertEquals(testUser.getPoints(), retrievedUser.getPoints());
    }

    @Test
    public void testFindUser() throws SQLException {
        // 테스트 데이터를 삽입
        userDAO.create(testUser);

        // 사용자 조회
        User retrievedUser = userDAO.findUser(testUser.getLoginId());
        assertNotNull(retrievedUser);
        assertEquals(testUser.getLoginId(), retrievedUser.getLoginId());
    }

    @Test
    public void testUpdate() throws SQLException {
        // 사용자 삽입
        userDAO.create(testUser);

        // 사용자 정보 수정
        testUser.setPassword("new_password123");
        testUser.setNickname("UpdatedNickname");
        testUser.setPoints(200);
        int result = userDAO.update(testUser);
        assertEquals(1, result); // 수정 성공 시 1 반환

        // 수정된 정보 확인
        User updatedUser = userDAO.findUser(testUser.getLoginId());
        assertNotNull(updatedUser);
        assertEquals("new_password123", updatedUser.getPassword());
        assertEquals("UpdatedNickname", updatedUser.getNickname());
        assertEquals(200, updatedUser.getPoints());
    }

    @Test
    public void testRemove() throws SQLException {
        // 사용자 삽입
        userDAO.create(testUser);

        // 사용자 삭제
        int result = userDAO.remove(testUser.getLoginId());
        assertEquals(1, result); // 삭제 성공 시 1 반환

        // 삭제된 사용자 확인
        User deletedUser = userDAO.findUser(testUser.getLoginId());
        assertNull(deletedUser);
    }

    @Test
    public void testFindUserList() throws SQLException {
        // 사용자 삽입
        userDAO.create(testUser);

        // 전체 사용자 조회
        List<User> userList = userDAO.findUserList();
        assertNotNull(userList);
        assertTrue(userList.stream().anyMatch(user -> user.getLoginId().equals(testUser.getLoginId())));
    }
}
