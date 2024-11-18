import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.dao.ProvideConfirmDAO;
import model.domain.ProvideConfirm;

public class ProvideConfirmDAOTest {

    private ProvideConfirmDAO dao;

    @Before
    public void setUp() {
        dao = new ProvideConfirmDAO();
    }

    @After
    public void tearDown() {
        dao = null;
    }

    @Test
    public void testCreate() {
        try {
            ProvideConfirm pc = new ProvideConfirm();
            pc.setActual_return_date(Date.valueOf("2024-11-02"));
            pc.setPenalty_points(10);
            pc.setOverdue_days(5);
            pc.setProvide_post_id(1); // 존재하는 provide_post_id
            pc.setRequester_id(1);   // 존재하는 requester_id

            ProvideConfirm createdPC = dao.create(pc);

            assertNotNull(createdPC);
            assertNotEquals(0, createdPC.getId());
            assertEquals(Date.valueOf("2024-11-02"), createdPC.getActual_return_date());
        } catch (SQLException e) {
            fail("Create method failed: " + e.getMessage());
        }
    }


    @Test
    public void testFindById() {
        try {
            int id = 1; // 테스트용으로 생성된 id 값
            ProvideConfirm pc = dao.findById(id);

            assertNotNull(pc);
            assertEquals(id, pc.getId());
            assertEquals(0, pc.getPenalty_points());
            assertEquals(0, pc.getOverdue_days());
        } catch (SQLException e) {
            fail("FindById method failed: " + e.getMessage());
        }
    }
}
