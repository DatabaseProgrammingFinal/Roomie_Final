//import static org.junit.Assert.*;
//
//import model.service.RequestConfirmService;
//import model.domain.RequestConfirm;
//
//import java.sql.Date;
//import java.sql.SQLException;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//public class RequestConfirmServiceTest {
//    //private RequestConfirmService requestConfirmService;
//    private RequestConfirm testRequestConfirm;
/*
    @Before
    public void setUp() throws Exception {
        requestConfirmService = new RequestConfirmService();

        // 테스트에 사용할 RequestConfirm 데이터 생성
        testRequestConfirm = new RequestConfirm(
            0, // id (자동 생성)
            new Date(System.currentTimeMillis()), // actual_return_date
            15, // penalty_points
            2,  // overdue_days
            1,  // request_post_id
            1   // provider_id
        );
    }

    @After
    public void tearDown() throws Exception {
        // 테스트 데이터 삭제 (필요 시 구현)
        // 테스트 후 필요한 정리 작업이 있으면 추가로 구현
    }

    @Test
    public void testCreateRequestConfirm() throws SQLException {
        RequestConfirm createdRequestConfirm = requestConfirmService.createRequestConfirm(testRequestConfirm);

        assertNotNull("RequestConfirm 객체가 null입니다.", createdRequestConfirm);
        assertTrue("생성된 ID가 유효하지 않습니다.", createdRequestConfirm.getId() > 0);
        assertEquals(testRequestConfirm.getPenalty_points(), createdRequestConfirm.getPenalty_points());
        assertEquals(testRequestConfirm.getOverdue_days(), createdRequestConfirm.getOverdue_days());
    }

    @Test
    public void testGetRequestConfirmById() throws SQLException {
        // RequestConfirm 생성
        RequestConfirm createdRequestConfirm = requestConfirmService.createRequestConfirm(testRequestConfirm);

        // 생성된 ID로 RequestConfirm 조회
        RequestConfirm retrievedRequestConfirm = requestConfirmService.getRequestConfirmById(createdRequestConfirm.getId());

        assertNotNull("검색된 RequestConfirm 객체가 null입니다.", retrievedRequestConfirm);
        assertEquals(createdRequestConfirm.getId(), retrievedRequestConfirm.getId());
        assertEquals(createdRequestConfirm.getPenalty_points(), retrievedRequestConfirm.getPenalty_points());
        assertEquals(createdRequestConfirm.getOverdue_days(), retrievedRequestConfirm.getOverdue_days());
    }
}*/
