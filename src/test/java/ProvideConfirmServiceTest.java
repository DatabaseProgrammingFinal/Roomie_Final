import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import model.dao.JDBCUtil;

import model.domain.ProvideConfirm;
import model.service.ProvideConfirmService;

public class ProvideConfirmServiceTest {
    private ProvideConfirmService provideConfirmService;
    private ProvideConfirm testProvideConfirm;

    @Before
    public void setUp() throws Exception {
        provideConfirmService = new ProvideConfirmService();

        // 테스트에 사용할 ProvideConfirm 데이터 생성
        testProvideConfirm = new ProvideConfirm(
            0, // id (자동 생성)
            new Date(System.currentTimeMillis()), // actual_return_date
            11, // penalty_points
            6,  // overdue_days
            1,  // provide_post_id
            1   // requester_id
        );
    }

    @After
    public void tearDown() throws Exception {
        // 테스트 후 생성된 데이터를 삭제
        String deleteSQL = "DELETE FROM Rental_provide_confirm WHERE provide_post_id = 1 AND requester_id = 1";
        JDBCUtil jdbcUtil = new JDBCUtil();
        try {
            jdbcUtil.setSqlAndParameters(deleteSQL, null);
            jdbcUtil.executeUpdate();
            jdbcUtil.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
    }

    @Test
    public void testCreateProvideConfirm() throws SQLException {
        ProvideConfirm createdProvideConfirm = provideConfirmService.createProvideConfirm(testProvideConfirm);

        assertNotNull("ProvideConfirm 객체가 null입니다.", createdProvideConfirm);
        assertTrue("생성된 ID가 유효하지 않습니다.", createdProvideConfirm.getId() > 0);
        assertEquals(testProvideConfirm.getPenalty_points(), createdProvideConfirm.getPenalty_points());
        assertEquals(testProvideConfirm.getOverdue_days(), createdProvideConfirm.getOverdue_days());
    }

    @Test
    public void testGetProvideConfirmById() throws SQLException {
        // ProvideConfirm 생성
        ProvideConfirm createdProvideConfirm = provideConfirmService.createProvideConfirm(testProvideConfirm);

        // 생성된 ProvideConfirm를 ID로 검색
        ProvideConfirm retrievedProvideConfirm = provideConfirmService.getProvideConfirmById(createdProvideConfirm.getId());

        assertNotNull("검색된 ProvideConfirm 객체가 null입니다.", retrievedProvideConfirm);
        assertEquals(createdProvideConfirm.getId(), retrievedProvideConfirm.getId());
        assertEquals(createdProvideConfirm.getPenalty_points(), retrievedProvideConfirm.getPenalty_points());
        assertEquals(createdProvideConfirm.getOverdue_days(), retrievedProvideConfirm.getOverdue_days());
    }
}
