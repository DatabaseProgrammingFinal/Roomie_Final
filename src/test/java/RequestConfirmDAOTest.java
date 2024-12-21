import static org.junit.Assert.*;

import model.dao.RequestConfirmDAO;
import model.domain.RequestConfirm;
import model.dao.JDBCUtil;

import java.sql.Date;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RequestConfirmDAOTest {
    private RequestConfirmDAO requestConfirmDAO;
    private RequestConfirm testRequestConfirm;

    @Before
    public void setUp() throws Exception {
        requestConfirmDAO = new RequestConfirmDAO();

        // 외래키 제약 조건을 충족하기 위한 데이터 삽입
        String insertTestDataSQL = 
            "INSERT INTO Rental_request_post (id, title, rental_item, content, points, rental_start_date, rental_end_date, rental_location, status, requester_id) " +
            "VALUES (Rental_request_post_seq.NEXTVAL, 'Test Rental', 'Test Item', 'Test Content', 10, TO_DATE('2024-11-01', 'YYYY-MM-DD'), TO_DATE('2024-11-02', 'YYYY-MM-DD'), 'Test Location', 1, 1)";
        JDBCUtil jdbcUtil = new JDBCUtil();
        try {
            jdbcUtil.setSqlAndParameters(insertTestDataSQL, null);
            jdbcUtil.executeUpdate();
            jdbcUtil.commit();
            System.out.println("테스트 데이터를 Rental_request_post 테이블에 삽입했습니다.");
        } catch (Exception e) {
            jdbcUtil.rollback();
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }

        // 테스트에 사용할 RequestConfirm 데이터 생성
        testRequestConfirm = new RequestConfirm(
            0, // id (자동 생성)
            new Date(System.currentTimeMillis()), // actual_return_date
            10, // penalty_points
            5,  // overdue_days
            1,  // request_post_id
            1   // provider_id
        );
    }

    @After
    public void tearDown() throws Exception {
        // 테스트 데이터 삭제
        String deleteTestDataSQL = "DELETE FROM Rental_request_confirm WHERE request_post_id = 1 AND provider_id = 1";
        JDBCUtil jdbcUtil = new JDBCUtil();
        try {
            jdbcUtil.setSqlAndParameters(deleteTestDataSQL, null);
            jdbcUtil.executeUpdate();
            jdbcUtil.commit();
            System.out.println("테스트 데이터를 Rental_request_confirm 테이블에서 삭제했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
    }

    @Test
    public void testCreate() throws SQLException {
        RequestConfirm createdRequestConfirm = requestConfirmDAO.create(testRequestConfirm);

        assertNotNull("RequestConfirm 객체가 null입니다.", createdRequestConfirm);
        assertTrue("생성된 ID가 유효하지 않습니다.", createdRequestConfirm.getId() > 0);
        assertEquals(testRequestConfirm.getPenalty_points(), createdRequestConfirm.getPenalty_points());
        assertEquals(testRequestConfirm.getOverdue_days(), createdRequestConfirm.getOverdue_days());
    }

    @Test
    public void testFindById() throws SQLException {
        RequestConfirm createdRequestConfirm = requestConfirmDAO.create(testRequestConfirm);

        RequestConfirm retrievedRequestConfirm = requestConfirmDAO.findById(createdRequestConfirm.getId());
        assertNotNull("검색된 RequestConfirm 객체가 null입니다.", retrievedRequestConfirm);
        assertEquals(createdRequestConfirm.getId(), retrievedRequestConfirm.getId());
        assertEquals(createdRequestConfirm.getPenalty_points(), retrievedRequestConfirm.getPenalty_points());
    }
}
