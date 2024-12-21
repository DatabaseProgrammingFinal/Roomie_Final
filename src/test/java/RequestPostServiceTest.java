import static org.junit.Assert.*;

import java.sql.*;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import model.domain.RentalRequestPost;
import model.service.RequestPostService;

public class RequestPostServiceTest {
    private static Connection connection;
    private static RequestPostService requestPostService;
    private static RentalRequestPost testPost;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // 데이터베이스 연결 설정
        connection = DriverManager.getConnection("jdbc:oracle:thin:@//dblab.dongduk.ac.kr:1521/orclpdb", "dbp240210", "21703");
        connection.setAutoCommit(false); // 트랜잭션 수동 설정

        requestPostService = new RequestPostService();

        // 테스트에 사용할 데이터 생성
        testPost = new RentalRequestPost(
            0, "Test Title", "Test Item", "Test Content", 50,
            new Date(System.currentTimeMillis()),
            new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 7)),
            "Test Location", "Test Return Location", 0, 1
        );

        // 데이터베이스에 테스트 데이터 삽입
        requestPostService.createRentalRequestPost(testPost);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // 테스트 후 트랜잭션 롤백 및 연결 종료
        if (connection != null) {
            connection.rollback(); // 테스트 데이터 저장 방지
            connection.close();
        }
    }

    @Test
    public void testCreateRentalRequestPost() throws Exception {
        // 데이터베이스에서 삽입된 데이터 확인
        String query = "SELECT title FROM rental_request_post WHERE title = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, "Test Title");
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    assertEquals("Test Title", rs.getString("title"));
                } else {
                    fail("데이터베이스에 삽입된 데이터가 없습니다.");
                }
            }
        }
    }

    @Test
    public void testGetRentalRequestPostById() throws Exception {
        // 삽입된 데이터의 ID 조회 및 검증
        RentalRequestPost post = requestPostService.getRentalRequestPostById(testPost.getId());
        assertNotNull(post);
        assertEquals(testPost.getTitle(), post.getTitle());
    }

    @Test
    public void testSearchRentalRequestPostsByTitle() throws Exception {
        // 제목으로 검색하여 결과 검증
        List<RentalRequestPost> posts = requestPostService.searchRentalRequestPostsByTitle("Test Title");
        assertFalse(posts.isEmpty());
        assertEquals("Test Title", posts.get(0).getTitle());
    }

    @Test
    public void testGetAllRentalRequestPosts() throws Exception {
        // 모든 대여 요청글 조회 및 검증
        List<RentalRequestPost> posts = requestPostService.getAllRentalRequestPosts();
        assertFalse(posts.isEmpty());
        assertTrue(posts.stream().anyMatch(post -> post.getTitle().equals("Test Title")));
    }
}
