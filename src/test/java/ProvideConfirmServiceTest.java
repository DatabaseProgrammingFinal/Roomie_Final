import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;

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

        // 기존 데이터 정리 및 테스트 데이터 준비
        JDBCUtil jdbcUtil = new JDBCUtil();
        try {
            String[] cleanupSQL = {
                "DELETE FROM Rental_provide_confirm",
                "DELETE FROM Rental_provide_post",
                "DELETE FROM Member"
            };

            for (String sql : cleanupSQL) {
                jdbcUtil.setSqlAndParameters(sql, null);
                jdbcUtil.executeUpdate();
            }

            // 시퀀스 초기화
            String[] resetSequences = {
                "ALTER SEQUENCE Rental_provide_confirm_seq RESTART START WITH 1",
                "ALTER SEQUENCE Rental_provide_post_seq RESTART START WITH 1",
                "ALTER SEQUENCE Member_seq RESTART START WITH 1"
            };

            for (String sql : resetSequences) {
                jdbcUtil.setSqlAndParameters(sql, null);
                jdbcUtil.executeUpdate();
            }

            // 테스트 데이터를 삽입
            String insertRequesterSQL = 
                "INSERT INTO Member (id, login_id, password, nickname, dormitory_name, room_number, profile_url, points) " +
                "VALUES (1, 'requester01', 'password123', '홍길동', 'OO관', '101호', 'profile1.jpg', 100)";
            String insertProviderSQL = 
                "INSERT INTO Member (id, login_id, password, nickname, dormitory_name, room_number, profile_url, points) " +
                "VALUES (2, 'provider01', 'password456', '김둘리', 'XX관', '202호', 'profile2.jpg', 200)";
            String insertProvidePostSQL = 
                "INSERT INTO Rental_provide_post (id, title, rental_item, content, points, rental_start_date, rental_end_date, rental_location, status, provider_id, image_url) " +
                "VALUES (1, '노트북 대여', '노트북', '대여 가능', 50, TO_DATE('2024-11-01', 'YYYY-MM-DD'), TO_DATE('2024-11-02', 'YYYY-MM-DD'), 'XX관 1층', 1, 2, 'laptop.jpg')";

            jdbcUtil.setSqlAndParameters(insertRequesterSQL, null);
            jdbcUtil.executeUpdate();

            jdbcUtil.setSqlAndParameters(insertProviderSQL, null);
            jdbcUtil.executeUpdate();

            jdbcUtil.setSqlAndParameters(insertProvidePostSQL, null);
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
            11, // penalty_points
            6,  // overdue_days
            1,  // provide_post_id
            1   // requester_id
        );
    }

    @After
    public void tearDown() throws Exception {
        // 테스트 후 데이터 삭제
        JDBCUtil jdbcUtil = new JDBCUtil();
        try {
            String[] deleteSQL = {
                "DELETE FROM Rental_provide_confirm",
                "DELETE FROM Rental_provide_post",
                "DELETE FROM Member"
            };

            for (String sql : deleteSQL) {
                jdbcUtil.setSqlAndParameters(sql, null);
                jdbcUtil.executeUpdate();
            }

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

    @Test
    public void testGetRequesterAndProviderInfo() throws SQLException {
        // ProvideConfirm 생성
        provideConfirmService.createProvideConfirm(testProvideConfirm);

        // 요청자와 제공자 정보 가져오기
        Map<String, Object> result = provideConfirmService.getRequesterAndProviderInfo(1, 1);

        assertNotNull("요청자와 제공자 정보가 null입니다.", result);

        // 요청자 정보 검증
        Map<String, Object> requester = (Map<String, Object>) result.get("requester");
        assertNotNull("요청자 정보가 null입니다.", requester);
        assertEquals("홍길동", requester.get("nickname"));
        assertEquals("OO관", requester.get("dormitory_name"));
        assertEquals("101호", requester.get("room_number"));

        // 제공자 정보 검증
        Map<String, Object> provider = (Map<String, Object>) result.get("provider");
        assertNotNull("제공자 정보가 null입니다.", provider);
        assertEquals("김둘리", provider.get("nickname"));
        assertEquals("XX관", provider.get("dormitory_name"));
        assertEquals("202호", provider.get("room_number"));
    }
}
