import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.domain.User;
import model.service.UserService;

public class UserServiceTest {
    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserService(); // UserService 객체 초기화
    }

    @Test
	public void testCreateUser() {
	    User user = new User("test2024", "password123", "Test Nickname", 
	                         "Dorm A", "101", "http://example.com/profile.jpg", 10);
	    int result = userService.createUser(user);
	    assertEquals(1, result); // 1이 반환되면 성공
	}

    @Test
	public void testUpdateUser() {
	    User user = new User(1, "user03", "password", "SenderUser",
	                         "Dorm A", "101", "http://example.com/profile.jpg", 10);
	    int result = userService.updateUser(user);
	    assertEquals(1, result); // 1이 반환되면 성공
	}

    @Test
    public void testDeleteUser() {
        boolean result = userService.deleteUser("sender01");
        assertTrue(result); // true면 성공
    }

    @Test
    public void testFindUserByLoginId() {
        User user = userService.findUserByLoginId("sender01");
        assertNotNull(user); // user가 null이 아니면 성공
        assertEquals("sender01", user.getLoginId());
    }

    @Test
    public void testFindUserById() {
        User user = userService.findUserById(1);
        assertNotNull(user); // user가 null이 아니면 성공
        assertEquals(1, user.getId());
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = userService.getAllUsers();
        assertNotNull(users); // 리스트가 null이 아니면 성공
        assertTrue(users.size() > 0); // 적어도 한 명 이상의 사용자 존재
    }


    @Test
	public void testIsExistingUser() {
	    boolean exists = userService.isExistingUser("user02");
	    assertTrue(exists); // 사용자가 존재하면 성공
	}
}
