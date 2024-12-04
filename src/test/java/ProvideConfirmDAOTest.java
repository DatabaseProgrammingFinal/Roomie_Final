import static org.junit.Assert.*;

import model.dao.ProvideConfirmDAO;
import model.domain.ProvideConfirm;
import model.dao.JDBCUtil;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProvideConfirmDAOTest {
    private ProvideConfirmDAO provideConfirmDAO;
    private ProvideConfirm testProvideConfirm;

    @Before
    public void resetSequences() throws SQLException {
        // 시퀀스 초기화
        String[] resetSequences = {
            "ALTER SEQUENCE Rental_provide_confirm_seq RESTART START WITH 1",
            "ALTER SEQUENCE Rental_provide_post_seq RESTART START WITH 1",
            "ALTER SEQUENCE Member_seq RESTART START WITH 1"
        };

        JDBCUtil jdbcUtil = new JDBCUtil();
        try {
            for (String sql : resetSequences) {
                jdbcUtil.setSqlAndParameters(sql, null);
                jdbcUtil.executeUpdate();
            }
            jdbcUtil.commit();
        } catch (Exception e) {
            jdbcUtil.rollback();
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
    }

    @Before
    public void setUp() throws Exception {
        provideConfirmDAO = new ProvideConfirmDAO();

        // 기존 데이터 정리
        String[] cleanupSQL = {
            "DELETE FROM Rental_provide_confirm",
            "DELETE FROM Rental_provide_post",
            "DELETE FROM Member"
        };

        JDBCUtil jdbcUtil = new JDBCUtil();
        try {
            for (String sql : cleanupSQL) {
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
                "INSERT INTO Rental_provide_post (id, title, rental_item, content, points, rental_start_date, rental_end_date, rental_location,return_location, status, provider_id, image_url) " +
                "VALUES (1, '노트북 대여', '노트북', '대여 가능', 50, TO_DATE('2024-11-01', 'YYYY-MM-DD'), TO_DATE('2024-11-02', 'YYYY-MM-DD'), 'XX관 1층', 'OO관 2층',1, 2, 'laptop.jpg')";
            String insertProvideConfirmSQL = 
                "INSERT INTO Rental_provide_confirm (id, actual_return_date, penalty_points, overdue_days, provide_post_id, requester_id) " +
                "VALUES (1, TO_DATE('2024-11-02', 'YYYY-MM-DD'), 0, 0, 1, 1)";
            
            jdbcUtil.setSqlAndParameters(insertRequesterSQL, null);
            jdbcUtil.executeUpdate();

            jdbcUtil.setSqlAndParameters(insertProviderSQL, null);
            jdbcUtil.executeUpdate();

            jdbcUtil.setSqlAndParameters(insertProvidePostSQL, null);
            jdbcUtil.executeUpdate();

            jdbcUtil.setSqlAndParameters(insertProvideConfirmSQL, null);
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
        String[] deleteSQL = {
            "DELETE FROM Rental_provide_confirm",
            "DELETE FROM Rental_provide_post",
            "DELETE FROM Member"
        };

        JDBCUtil jdbcUtil = new JDBCUtil();
        try {
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

    @Test
    public void testGetRequesterAndProviderInfo() throws SQLException {
        // 요청자와 제공자 정보 가져오기
        Map<String, Object> result = provideConfirmDAO.getRequesterAndProviderInfo(1, 1);

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
    
    @Test
    public void testGetRentalDecisionDetails() throws SQLException {
        // 메서드 실행
        Map<String, Object> rentalDetails = provideConfirmDAO.getRentalDecisionDetails(1);

        // 결과 검증
        assertNotNull("Rental decision details should not be null", rentalDetails);

        // 물품명 확인
        assertEquals("노트북", rentalDetails.get("itemnickname"));

        // 요청자 정보 확인
        Map<String, Object> requester = (Map<String, Object>) rentalDetails.get("requester");
        assertNotNull("Requester information should not be null", requester);
        assertEquals("홍길동", requester.get("nickname"));
        assertEquals("OO관", requester.get("dormitory_name"));
        assertEquals("101호", requester.get("room_number"));

        // 제공자 정보 확인
        Map<String, Object> provider = (Map<String, Object>) rentalDetails.get("provider");
        assertNotNull("Provider information should not be null", provider);
        assertEquals("김둘리", provider.get("nickname"));
        assertEquals("XX관", provider.get("dormitory_name"));
        assertEquals("202호", provider.get("room_number"));

        // 대여 정보 확인
        assertEquals(50, rentalDetails.get("store")); // 제공 금액
        assertEquals("XX관 1층", rentalDetails.get("rental_place"));
        assertEquals("OO관 2층", rentalDetails.get("return_place"));
        assertEquals(java.sql.Date.valueOf("2024-11-01"), rentalDetails.get("rental_date"));
        assertEquals(java.sql.Date.valueOf("2024-11-02"), rentalDetails.get("return_date"));
    }

}