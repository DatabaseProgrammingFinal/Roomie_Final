import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.dao.JDBCUtil;
import model.dao.RequestConfirmDAO;
import model.domain.RequestConfirm;

public class RequestConfirmDAOTest {
    private RequestConfirmDAO requestConfirmDAO;
    private Connection conn;

    @Before
    public void setUp() throws SQLException {
        requestConfirmDAO = new RequestConfirmDAO();

        // Connection 객체를 가져오기
        conn = JDBCUtil.getConnection();
        if (conn == null) {
            throw new SQLException("Failed to establish connection: Connection is null.");
        }

        conn.setAutoCommit(false); // 자동 커밋 비활성화

        try {
            // 중복 데이터 방지 - 기존 데이터 삭제
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Rental_request_confirm WHERE request_post_id = 9999")) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Rental_request_post WHERE id = 9999")) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Member WHERE id = 9999")) {
                ps.executeUpdate();
            }

            // 테스트용 Member 데이터 삽입
            String insertMemberSql = "INSERT INTO Member (id, login_id, password, nickname, dormitory_name, room_number, profile_url, points) "
                    + "VALUES (9999, 'test_user', 'password', 'Tester', 'Test Dorm', '101', 'profile.png', 0)";
            try (PreparedStatement ps = conn.prepareStatement(insertMemberSql)) {
                ps.executeUpdate();
            }

            // 테스트용 Rental_request_post 데이터 삽입
            String insertRequestPostSql = "INSERT INTO Rental_request_post (id, title, rental_item, content, points, rental_start_date, rental_end_date, rental_location, status, requester_id) "
                    + "VALUES (9999, 'Test Title', 'Test Item', 'Test Content', 10, SYSDATE, SYSDATE + 7, 'Test Location', 1, 9999)";
            try (PreparedStatement ps = conn.prepareStatement(insertRequestPostSql)) {
                ps.executeUpdate();
            }

            conn.commit(); // 데이터 삽입 후 커밋
        } catch (SQLException e) {
            conn.rollback(); // 오류 발생 시 롤백
            throw new SQLException("Setup failed: " + e.getMessage(), e);
        }
    }

    @After
    public void tearDown() throws SQLException {
        if (conn != null) {
            try {
                conn.setAutoCommit(true); // 자동 커밋 모드 복원

                try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Rental_request_confirm WHERE request_post_id = 9999")) {
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Rental_request_post WHERE id = 9999")) {
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Member WHERE id = 9999")) {
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                throw new SQLException("Teardown failed: " + e.getMessage(), e);
            } finally {
                conn.close();
            }
        }
    }

    @Test
    public void testCreate() throws SQLException {
        RequestConfirm rc = new RequestConfirm();
        rc.setActual_return_date(new Date(System.currentTimeMillis()));
        rc.setPenalty_points(1);
        rc.setOverdue_days(2);
        rc.setRequest_post_id(9999);
        rc.setProvider_id(9999);

        RequestConfirm createdRc = requestConfirmDAO.create(rc);
        assertNotNull("Created RequestConfirm should not be null", createdRc);
        assertEquals(9999, createdRc.getProvider_id());
    }

    @Test
    public void testRetrieve() throws SQLException {
        RequestConfirm rc = new RequestConfirm();
        rc.setActual_return_date(new Date(System.currentTimeMillis()));
        rc.setPenalty_points(1);
        rc.setOverdue_days(2);
        rc.setRequest_post_id(9999);
        rc.setProvider_id(9999);

        RequestConfirm createdRc = requestConfirmDAO.create(rc);
        assertNotNull("Created RequestConfirm should not be null", createdRc);

        int generatedId = createdRc.getId(); // ID가 올바르게 설정되었는지 확인
        RequestConfirm retrievedRc = requestConfirmDAO.findById(generatedId);
        assertNotNull("Retrieved RequestConfirm should not be null", retrievedRc);
        assertEquals(generatedId, retrievedRc.getId());
    }
}
