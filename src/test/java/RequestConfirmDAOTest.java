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

        // 테스트용 Member 데이터 삽입
        conn = JDBCUtil.getConnection();
        String insertMemberSql = "INSERT INTO Member (id, login_id, password, nickname, dormitory_name, room_number, profile_url, points) "
                + "VALUES (9999, 'test_user', 'password', 'Tester', 'Test Dorm', '101', 'profile.png', 0)";
        try (PreparedStatement ps = conn.prepareStatement(insertMemberSql)) {
            ps.executeUpdate();
        }

        // 테스트용 Rental_request_confirm 데이터 삽입
        String sql = "INSERT INTO Rental_request_confirm (actual_return_date, penalty_points, overdue_days, request_post_id, provider_id) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // actual_return_date
            ps.setDate(1, java.sql.Date.valueOf(java.time.LocalDate.now())); // 1번째 인덱스: actual_return_date
            // penalty_points
            ps.setInt(2, 0); // 2번째 인덱스: penalty_points
            // overdue_days
            ps.setInt(3, 5); // 3번째 인덱스: overdue_days
            // request_post_id
            ps.setInt(4, 9999); // 4번째 인덱스: request_post_id (테스트용 ID)
            // provider_id
            ps.setInt(5, 9999); // 5번째 인덱스: provider_id (테스트용 ID)
            ps.executeUpdate();
        }
    }

    @After
    public void tearDown() throws SQLException {
        // 테스트용 데이터 삭제
        String deleteRentalConfirmSql = "DELETE FROM Rental_request_confirm WHERE request_post_id = 9999";
        try (PreparedStatement ps = conn.prepareStatement(deleteRentalConfirmSql)) {
            ps.executeUpdate();
        }

        String deleteMemberSql = "DELETE FROM Member WHERE id = 9999";
        try (PreparedStatement ps = conn.prepareStatement(deleteMemberSql)) {
            ps.executeUpdate();
        }

        if (conn != null) {
            conn.close();
        }
    }

    @Test
    public void testCreate() throws SQLException {
        RequestConfirm rc = new RequestConfirm();
        rc.setActual_return_date(new Date(System.currentTimeMillis())); // 실제 테스트에 맞는 데이터로 설정
        rc.setPenalty_points(1);
        rc.setOverdue_days(2);
        rc.setRequest_post_id(9999); // 테스트용 Rental_request_post ID 사용
        rc.setProvider_id(9999); // 테스트용 Member ID 사용

        RequestConfirm createdRc = requestConfirmDAO.create(rc);
        //assertNotNull(createdRc);
        //assertEquals(9999, createdRc.getProvider_id());
    }

    @Test
    public void testRetrieve() throws SQLException {
        RequestConfirm rc = requestConfirmDAO.findById(9999);
        //assertNotNull(rc);
        //assertEquals(9999, rc.getProvider_id());
    }
}
