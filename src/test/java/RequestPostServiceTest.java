import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import model.dao.RequestPostDAO;
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
        connection.setAutoCommit(false);  // 트랜잭션 수동 설정

        requestPostService = new RequestPostService(connection);

        // 테스트에 사용할 데이터 생성 (단 한번만)
        testPost = new RentalRequestPost(
            0, "Test Request Title", "Test Request Item", "Test Request Content", 50,
            new Date(System.currentTimeMillis()),
            new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 7)),
            "Test Request Location", 0, 1
        );

        // 데이터베이스에 한 번만 데이터를 삽입
        requestPostService.createRentalRequestPost(testPost);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // 테스트 후 트랜잭션 커밋 및 연결 종료
        if (connection != null) {
            connection.commit();
            connection.close();
        }
    }

    @Test
    public void testCreateRentalRequestPost() throws SQLException {
        // 데이터베이스에서 실제로 값이 들어갔는지 조회
        String query = "SELECT title FROM rental_request_post WHERE title = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, "Test Request Title");
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // 조회된 결과를 콘솔에 출력
                    System.out.println("데이터베이스에서 조회된 값: " + rs.getString("title"));
                    assertEquals("Test Request Title", rs.getString("title"));
                } else {
                    System.out.println("데이터베이스에 값이 없습니다.");
                    fail("데이터베이스에 삽입된 값이 없습니다.");
                }
            }
        }
    }

    @Test
    public void testGetRentalRequestPostById() throws SQLException {
        // 생성된 데이터의 ID를 조회
        RentalRequestPost createdPost = requestPostService.searchRentalRequestPostsByTitle("Test Request Title").get(0);

        // ID로 데이터 조회
        RentalRequestPost retrievedPost = requestPostService.getRentalRequestPostById(createdPost.getId());
        assertNotNull("조회된 RentalRequestPost 객체가 null입니다.", retrievedPost);
        assertEquals(createdPost.getId(), retrievedPost.getId());
    }

    @Test
    public void testSearchRentalRequestPostsByTitle() throws SQLException {
        // 제목으로 검색
        List<RentalRequestPost> posts = requestPostService.searchRentalRequestPostsByTitle("Test Request Title");
        assertFalse("검색된 RentalRequestPost 목록이 비어 있습니다.", posts.isEmpty());

        // 검색된 데이터의 제목을 확인
        RentalRequestPost post = posts.get(0);
        assertEquals("Test Request Title", post.getTitle());
        assertEquals("Test Request Item", post.getRentalItem());
    }

    @Test
    public void testGetAllRentalRequestPosts() throws SQLException {
        // 모든 데이터를 조회
        List<RentalRequestPost> posts = requestPostService.getAllRentalRequestPosts();
        assertFalse("조회된 RentalRequestPost 목록이 비어 있습니다.", posts.isEmpty());
    }

}
