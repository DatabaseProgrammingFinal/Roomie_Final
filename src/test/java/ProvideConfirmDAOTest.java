import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.dao.JDBCUtil;
import model.dao.ProvideConfirmDAO;
import model.domain.ProvideConfirm;
import model.domain.RentalProvidePost;

public class ProvideConfirmDAOTest {
    private ProvideConfirmDAO provideConfirmDAO;
    private ProvideConfirm testProvideConfirm;
    private RentalProvidePost testRentalProvidePost;
    private JDBCUtil jdbcUtil;

    @Before
    public void setUp() throws Exception {
        provideConfirmDAO = new ProvideConfirmDAO();
        jdbcUtil = new JDBCUtil();

        // 테이블 초기화 및 시퀀스 리셋
        String resetConfirmSequence = "ALTER SEQUENCE Rental_provide_confirm_seq RESTART START WITH 1";
        String resetPostSequence = "ALTER SEQUENCE Rental_provide_post_seq RESTART START WITH 1";
        String cleanupConfirmSQL = "DELETE FROM Rental_provide_confirm";
        String cleanupMemberSQL = "DELETE FROM Member";
        String cleanupPostSQL = "DELETE FROM Rental_provide_post";

        String insertMember1 = "INSERT INTO Member (id, login_id, password, nickname, dormitory_name, room_number, profile_url, points) "
                + "VALUES (1, 'user01', 'password123', '김철수', '행복관', '101', 'example.jpg', 100)";
        String insertMember2 = "INSERT INTO Member (id, login_id, password, nickname, dormitory_name, room_number, profile_url, points) "
                + "VALUES (2, 'user02', 'password456', '이영희', '자유관', '202', 'example2.jpg', 150)";

        String insertPost = "INSERT INTO Rental_provide_post (id, title, rental_item, content, points, rental_start_date, rental_end_date, rental_location, return_location, status, provider_id, image_url) "
                + "VALUES (Rental_provide_post_seq.NEXTVAL, 'C타입 충전기 내일 빌려드립니다', '충전기', '필요하시면 내일 하루 빌려드립니다.', 15, "
                + "TO_DATE('2024-11-02', 'YYYY-MM-DD'), TO_DATE('2024-11-03', 'YYYY-MM-DD'), '평화관 1층', '행복관 1층', 1, 1, 'charger.jpg')";

        String insertConfirm = "INSERT INTO Rental_provide_confirm (id, actual_return_date, penalty_points, overdue_days, provide_post_id, requester_id) "
                + "VALUES (Rental_provide_confirm_seq.NEXTVAL, TO_DATE('2024-11-02', 'YYYY-MM-DD'), 15, 5, 1, 2)";

        try {
            // 데이터 초기화
            jdbcUtil.setSqlAndParameters(cleanupConfirmSQL, null);
            jdbcUtil.executeUpdate();
            jdbcUtil.setSqlAndParameters(cleanupPostSQL, null);
            jdbcUtil.executeUpdate();
            jdbcUtil.setSqlAndParameters(cleanupMemberSQL, null);
            jdbcUtil.executeUpdate();

            // 시퀀스 리셋
            jdbcUtil.setSqlAndParameters(resetConfirmSequence, null);
            jdbcUtil.executeUpdate();
            jdbcUtil.setSqlAndParameters(resetPostSequence, null);
            jdbcUtil.executeUpdate();

            // 데이터 삽입
            jdbcUtil.setSqlAndParameters(insertMember1, null);
            jdbcUtil.executeUpdate();
            jdbcUtil.setSqlAndParameters(insertMember2, null);
            jdbcUtil.executeUpdate();
            jdbcUtil.setSqlAndParameters(insertPost, null);
            jdbcUtil.executeUpdate();
            jdbcUtil.setSqlAndParameters(insertConfirm, null);
            jdbcUtil.executeUpdate();

            jdbcUtil.commit();
        } catch (Exception e) {
            jdbcUtil.rollback();
            e.printStackTrace();
            fail("테스트 데이터 초기화 중 오류 발생: " + e.getMessage());
        } finally {
            jdbcUtil.close();
        }

        // 테스트에 사용할 데이터 생성
        testProvideConfirm = new ProvideConfirm(0, Date.valueOf("2024-11-02"), 10, 5, 1, 2);
        testRentalProvidePost = new RentalProvidePost(0, "New Title", "New Item", "New Content", 200,
                Date.valueOf("2024-12-10"), Date.valueOf("2024-12-15"), "New Location", "New Return Location", 1, 1,
                "new_image.jpg");
    }

    @After
    public void tearDown() throws Exception {
        // 테스트 데이터 삭제
        String cleanupSQL = "DELETE FROM Rental_provide_confirm";
        String cleanupMemberSQL = "DELETE FROM Member";
        String cleanupPostSQL = "DELETE FROM Rental_provide_post";

        JDBCUtil jdbcUtil = new JDBCUtil();
        try {
            jdbcUtil.setSqlAndParameters(cleanupSQL, null);
            jdbcUtil.executeUpdate();
            jdbcUtil.setSqlAndParameters(cleanupPostSQL, null);
            jdbcUtil.executeUpdate();
            jdbcUtil.setSqlAndParameters(cleanupMemberSQL, null);
            jdbcUtil.executeUpdate();
            jdbcUtil.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail("테스트 데이터 정리 중 오류 발생: " + e.getMessage());
        } finally {
            jdbcUtil.close();
        }
    }

    @Test(timeout = 5000) // 타임아웃 5초 설정
    public void testCreate() throws SQLException {
        System.out.println("===== testCreate 시작 =====");
        ProvideConfirm createdProvideConfirm = provideConfirmDAO.create(testProvideConfirm);

        assertNotNull("ProvideConfirm 객체가 null입니다.", createdProvideConfirm);
        assertTrue("생성된 ID가 유효하지 않습니다.", createdProvideConfirm.getId() > 0);
        assertEquals(testProvideConfirm.getPenalty_points(), createdProvideConfirm.getPenalty_points());
        assertEquals(testProvideConfirm.getOverdue_days(), createdProvideConfirm.getOverdue_days());

        System.out.println("testCreate 성공: ID = " + createdProvideConfirm.getId());
        System.out.println("===== testCreate 종료 =====");
    }

    @Test(timeout = 5000)
    public void testFindById() throws SQLException {
        System.out.println("===== testFindById 시작 =====");
        ProvideConfirm createdProvideConfirm = provideConfirmDAO.create(testProvideConfirm);

        ProvideConfirm retrievedProvideConfirm = provideConfirmDAO.findById(createdProvideConfirm.getId());
        assertNotNull("검색된 ProvideConfirm 객체가 null입니다.", retrievedProvideConfirm);
        assertEquals(createdProvideConfirm.getId(), retrievedProvideConfirm.getId());
        assertEquals(createdProvideConfirm.getPenalty_points(), retrievedProvideConfirm.getPenalty_points());

        System.out.println("testFindById 성공: ID = " + retrievedProvideConfirm.getId());
        System.out.println("===== testFindById 종료 =====");
    }

    @Test(timeout = 5000)
    public void testUpdate() throws SQLException {
        System.out.println("===== testUpdate 시작 =====");
        ProvideConfirm createdProvideConfirm = provideConfirmDAO.create(testProvideConfirm);

        // 데이터 수정
        createdProvideConfirm.setPenalty_points(2000);
        createdProvideConfirm.setOverdue_days(10);
        provideConfirmDAO.update(createdProvideConfirm);

        // 수정된 데이터 확인
        ProvideConfirm updatedProvideConfirm = provideConfirmDAO.findById(createdProvideConfirm.getId());
        assertEquals(20, updatedProvideConfirm.getPenalty_points());
        assertEquals(10, updatedProvideConfirm.getOverdue_days());

        System.out.println("testUpdate 성공: ID = " + updatedProvideConfirm.getId());
        System.out.println("===== testUpdate 종료 =====");
    }

    @Test(timeout = 5000)
    public void testDelete() throws SQLException {
        System.out.println("===== testDelete 시작 =====");
        ProvideConfirm createdProvideConfirm = provideConfirmDAO.create(testProvideConfirm);

        // 데이터 삭제
        provideConfirmDAO.delete(createdProvideConfirm.getId());

        // 삭제된 데이터 확인
        ProvideConfirm deletedProvideConfirm = provideConfirmDAO.findById(createdProvideConfirm.getId());
        assertNull("삭제된 ProvideConfirm 객체가 여전히 존재합니다.", deletedProvideConfirm);

        System.out.println("testDelete 성공: ID = " + createdProvideConfirm.getId());
        System.out.println("===== testDelete 종료 =====");
    }

    @Test
    public void testGetRequesterAndProviderInfo() throws SQLException {
        // 요청자와 제공자 정보 가져오기
        Map<String, Object> result = provideConfirmDAO.getRequesterAndProviderInfo(2, 1);

        assertNotNull("요청자와 제공자 정보가 null입니다.", result);

        // 요청자 정보 검증
        Map<String, Object> requester = (Map<String, Object>) result.get("requester");
        assertNotNull("요청자 정보가 null입니다.", requester);
        assertEquals("이영희", requester.get("nickname"));
        assertEquals("자유관", requester.get("dormitory_name"));
        assertEquals("202", requester.get("room_number"));

        // 제공자 정보 검증
        Map<String, Object> provider = (Map<String, Object>) result.get("provider");
        assertNotNull("제공자 정보가 null입니다.", provider);
        assertEquals("김철수", provider.get("nickname"));
        assertEquals("행복관", provider.get("dormitory_name"));
        assertEquals("101", provider.get("room_number"));
    }

    @Test
    public void testGetRentalDecisionDetails() throws SQLException {
        // 메서드 실행
        Map<String, Object> rentalDetails = provideConfirmDAO.getRentalDecisionDetails(2);

        // 결과 검증
        assertNotNull("Rental decision details should not be null", rentalDetails);

        // 물품명 확인
        assertEquals("충전기", rentalDetails.get("itemnickname"));

        // 요청자 정보 확인
        Map<String, Object> requester = (Map<String, Object>) rentalDetails.get("requester");
        assertNotNull("Requester information should not be null", requester);
        assertEquals("이영희", requester.get("nickname"));
        assertEquals("자유관", requester.get("dormitory_name"));
        assertEquals("202", requester.get("room_number"));

        // 제공자 정보 확인
        Map<String, Object> provider = (Map<String, Object>) rentalDetails.get("provider");
        assertNotNull("Provider information should not be null", provider);
        assertEquals("김철수", provider.get("nickname"));
        assertEquals("행복관", provider.get("dormitory_name"));
        assertEquals("101", provider.get("room_number"));

        // 대여 정보 확인
        assertEquals(15, rentalDetails.get("store")); // 제공 금액
        assertEquals("평화관 1층", rentalDetails.get("rental_place"));
        assertEquals("행복관 1층", rentalDetails.get("return_place"));
        assertEquals(java.sql.Date.valueOf("2024-11-02"), rentalDetails.get("rental_date"));
        assertEquals(java.sql.Date.valueOf("2024-11-03"), rentalDetails.get("return_date"));
    }

}
