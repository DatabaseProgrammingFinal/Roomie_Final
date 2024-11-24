import static org.junit.Assert.*;

import model.dao.ProvideConfirmDAO;
import model.domain.ProvideConfirm;
import model.dao.JDBCUtil;

import java.sql.Date;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProvideConfirmDAOTest {
    private ProvideConfirmDAO provideConfirmDAO;
    private ProvideConfirm testProvideConfirm;

    @Before
    public void setUp() throws Exception {
        provideConfirmDAO = new ProvideConfirmDAO();

        // 외래키 제약 조건을 충족하기 위한 데이터 삽입
        String insertTestDataSQL = 
            "INSERT INTO Rental_provide_confirm (id, actual_return_date, penalty_points, overdue_days, provide_post_id, requester_id) " +
            "VALUES (Rental_request_confirm_seq.NEXTVAL, TO_DATE('2024-11-02', 'YYYY-MM-DD'), 0, 0, 1, 1)";
        JDBCUtil jdbcUtil = new JDBCUtil();
        try {
            jdbcUtil.setSqlAndParameters(insertTestDataSQL, null);
            jdbcUtil.executeUpdate();
            jdbcUtil.commit();
        } catch (Exception e) {
            jdbcUtil.rollback();
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }

        // 테스트에 사용할 ProvideConfirm 데이터 생성
        testProvideConfirm = new ProvideConfirm(
            0, // id (자동 생성)
            new Date(System.currentTimeMillis()), // actual_return_date
            10, // penalty_points
            5,  // overdue_days
            1,  // provide_post_id
            1   // requester_id
        );
    }

    @After
    public void tearDown() throws Exception {
        // 테스트 데이터 삭제
        String deleteTestDataSQL = "DELETE FROM Rental_provide_confirm WHERE provide_post_id = 1 AND requester_id = 1";
        JDBCUtil jdbcUtil = new JDBCUtil();
        try {
            jdbcUtil.setSqlAndParameters(deleteTestDataSQL, null);
            jdbcUtil.executeUpdate();
            jdbcUtil.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
    }

    @Test
    public void testCreate() throws SQLException {
        ProvideConfirm createdProvideConfirm = provideConfirmDAO.create(testProvideConfirm);

        assertNotNull("ProvideConfirm 객체가 null입니다.", createdProvideConfirm);
        assertTrue("생성된 ID가 유효하지 않습니다.", createdProvideConfirm.getId() > 0);
        assertEquals(testProvideConfirm.getPenalty_points(), createdProvideConfirm.getPenalty_points());
        assertEquals(testProvideConfirm.getOverdue_days(), createdProvideConfirm.getOverdue_days());
    }

    @Test
    public void testFindById() throws SQLException {
        ProvideConfirm createdProvideConfirm = provideConfirmDAO.create(testProvideConfirm);

        ProvideConfirm retrievedProvideConfirm = provideConfirmDAO.findById(createdProvideConfirm.getId());
        assertNotNull("검색된 ProvideConfirm 객체가 null입니다.", retrievedProvideConfirm);
        assertEquals(createdProvideConfirm.getId(), retrievedProvideConfirm.getId());
        assertEquals(createdProvideConfirm.getPenalty_points(), retrievedProvideConfirm.getPenalty_points());
    }
}
