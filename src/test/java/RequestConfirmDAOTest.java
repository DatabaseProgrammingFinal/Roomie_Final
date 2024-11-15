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

        // Connection 객체를 가져옵니다.
        conn = JDBCUtil.getConnection();
        if (conn == null) {
            throw new SQLException("Failed to establish connection: Connection is null.");
        }
        
        conn.setAutoCommit(false); // 자동 커밋 비활성화

        // 중복 데이터 방지 - 기존 데이터 삭제
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Member WHERE id = 9999")) {
            ps.executeUpdate();
        }
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Rental_request_post WHERE id = 9999")) {
            ps.executeUpdate();
        }
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Rental_request_confirm WHERE request_post_id = 9999")) {
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

        // 테스트용 Rental_request_confirm 데이터 삽입
        String sql = "INSERT INTO Rental_request_confirm (actual_return_date, penalty_points, overdue_days, request_post_id, provider_id) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(java.time.LocalDate.now()));
            ps.setInt(2, 0);
            ps.setInt(3, 5);
            ps.setInt(4, 9999);
            ps.setInt(5, 9999);
            ps.executeUpdate();
        }
        
        conn.commit(); // 데이터 삽입 후 커밋
        System.out.println("Data inserted successfully in setUp");

    }
    @After
    public void tearDown() throws SQLException {
        if (conn != null) {
            try {
                conn.setAutoCommit(true); // 자동 커밋 모드 복원
                
                String deleteRentalConfirmSql = "DELETE FROM Rental_request_confirm WHERE request_post_id = 9999";
                try (PreparedStatement ps = conn.prepareStatement(deleteRentalConfirmSql)) {
                    ps.executeUpdate();
                }

                String deleteRequestPostSql = "DELETE FROM Rental_request_post WHERE id = 9999";
                try (PreparedStatement ps = conn.prepareStatement(deleteRequestPostSql)) {
                    ps.executeUpdate();
                }

                String deleteMemberSql = "DELETE FROM Member WHERE id = 9999";
                try (PreparedStatement ps = conn.prepareStatement(deleteMemberSql)) {
                    ps.executeUpdate();
                }

                conn.commit(); // 변경 사항을 커밋합니다.
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
        assertNotNull(createdRc);
        assertEquals(9999, createdRc.getProvider_id());
    }

    @Test
    public void testRetrieve() throws SQLException {
        // 새로운 RequestConfirm 객체 생성 및 설정
        RequestConfirm rc = new RequestConfirm();
        rc.setActual_return_date(new Date(System.currentTimeMillis()));
        rc.setPenalty_points(1);
        rc.setOverdue_days(2);
        rc.setRequest_post_id(9999);
        rc.setProvider_id(9999);

        // DAO의 create 메서드를 호출하여 rc를 삽입
        RequestConfirm createdRc = requestConfirmDAO.create(rc);
        assertNotNull("Created RequestConfirm should not be null", createdRc);

        // 생성된 id를 사용하여 findById 호출
        int generatedId = createdRc.getId();
        RequestConfirm retrievedRc = requestConfirmDAO.findById(generatedId);
        assertNotNull("RequestConfirm should not be null", retrievedRc);
        assertEquals(generatedId, retrievedRc.getId());
        System.out.println("Retrieve test passed with id: " + generatedId);
    }


}
