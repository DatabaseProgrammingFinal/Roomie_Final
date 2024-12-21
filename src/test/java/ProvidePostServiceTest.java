import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import model.domain.RentalProvidePost;
import model.service.ProvidePostService;

public class ProvidePostServiceTest {
    private static Connection connection;
    private static ProvidePostService providePostService;
    private static RentalProvidePost testPost;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // 데이터베이스 연결 설정
        connection = DriverManager.getConnection("jdbc:oracle:thin:@//dblab.dongduk.ac.kr:1521/orclpdb", "dbp240210", "21703");
        connection.setAutoCommit(false); // 트랜잭션 수동 설정
        providePostService = new ProvidePostService();

        // 테스트에 사용할 데이터 생성

        providePostService = new ProvidePostService(connection);

        // 테스트에 사용할 데이터 생성 (단 한번만)
        testPost = new RentalProvidePost(
            0, "Test Title", "Test Item", "Test Content", 50,
            new Date(System.currentTimeMillis()),
            new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 7)),
            "Test Location", "Test return Location", 0, 1, "http://example.com/image.jpg"
        );

        providePostService.createRentalProvidePost(testPost);
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
    public void testCreateRentalProvidePost() throws Exception {
        // 데이터베이스에서 삽입된 데이터 확인
        String query = "SELECT title FROM rental_provide_post WHERE title = ?";
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
    public void testGetRentalProvidePostById() throws Exception {
        // 삽입된 데이터의 ID 조회 및 검증
        List<RentalProvidePost> posts = providePostService.searchRentalProvidePostsByTitle("Test Title");
        assertFalse("검색 결과가 비어 있습니다.", posts.isEmpty());
        RentalProvidePost createdPost = posts.get(0);

        RentalProvidePost retrievedPost = providePostService.getRentalProvidePostById(createdPost.getId());
        assertNotNull("조회된 데이터가 null입니다.", retrievedPost);
        assertEquals("Test Title", retrievedPost.getTitle());
    }

    @Test
    public void testSearchRentalProvidePostsByTitle() throws Exception {
        // 제목 검색 테스트
        List<RentalProvidePost> posts = providePostService.searchRentalProvidePostsByTitle("Test Title");
        assertFalse("검색된 데이터가 없습니다.", posts.isEmpty());

        RentalProvidePost post = posts.get(0);
        assertEquals("Test Title", post.getTitle());
        assertEquals("Test Item", post.getRentalItem());
    }

    @Test
    public void testGetAllRentalProvidePosts() throws Exception {
        // 모든 데이터 조회 테스트
        List<RentalProvidePost> posts = providePostService.getAllRentalProvidePosts();
        assertFalse("조회된 데이터가 없습니다.", posts.isEmpty());
    }
}

