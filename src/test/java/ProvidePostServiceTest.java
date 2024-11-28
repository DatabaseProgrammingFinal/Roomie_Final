import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.dao.ProvidePostDAO;
import model.domain.RentalProvidePost;
import model.service.ProvidePostService;

public class ProvidePostServiceTest {
    private Connection connection;
    private ProvidePostService providePostService;
    private RentalProvidePost testPost;

    @Before
    public void setUp() throws Exception {
        // 데이터베이스 연결 설정
        connection = DriverManager.getConnection("jdbc:oracle:thin:@//dblab.dongduk.ac.kr:1521/orclpdb", "dbp240210", "21703");
        providePostService = new ProvidePostService(connection);

        // 테스트에 사용할 RentalProvidePost 데이터 생성
        testPost = new RentalProvidePost(
            0, // id (자동 생성)
            "Test Title", // title
            "Test Item",  // rentalItem
            "Test Content", // content
            50, // points
            new Date(System.currentTimeMillis()), // rentalStartDate
            new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 7)), // rentalEndDate (7일 후)
            "Test Location", // rentalLocation
            0, // status (0: 진행 전)
            1, // providerId
            "http://example.com/image.jpg" // imageUrl
        );
    }

    @After
    public void tearDown() throws Exception {
        // 테스트 후 데이터 정리
        String deleteSQL = "DELETE FROM rental_provide_post WHERE title = ?";
        try (var pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setString(1, "Test Title");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    public void testCreateRentalProvidePost() throws SQLException {
        boolean isCreated = providePostService.createRentalProvidePost(testPost);

        assertTrue("RentalProvidePost 생성에 실패했습니다.", isCreated);

        // 생성된 데이터를 조회하여 확인
        RentalProvidePost createdPost = providePostService.searchRentalProvidePostsByTitle("Test Title").get(0);
        assertNotNull("RentalProvidePost 객체가 null입니다.", createdPost);
        assertEquals("Test Title", createdPost.getTitle());
        assertEquals("Test Item", createdPost.getRentalItem());
    }

    @Test
    public void testGetRentalProvidePostById() throws SQLException {
        // 먼저 데이터를 생성
        providePostService.createRentalProvidePost(testPost);

        // 생성된 데이터의 ID를 조회
        RentalProvidePost createdPost = providePostService.searchRentalProvidePostsByTitle("Test Title").get(0);

        // ID로 데이터 조회
        RentalProvidePost retrievedPost = providePostService.getRentalProvidePostById(createdPost.getId());
        assertNotNull("조회된 RentalProvidePost 객체가 null입니다.", retrievedPost);
        assertEquals(createdPost.getId(), retrievedPost.getId());
    }

    @Test
    public void testSearchRentalProvidePostsByTitle() throws SQLException {
        // 데이터를 생성
        providePostService.createRentalProvidePost(testPost);

        // 제목으로 검색
        List<RentalProvidePost> posts = providePostService.searchRentalProvidePostsByTitle("Test Title");
        assertFalse("검색된 RentalProvidePost 목록이 비어 있습니다.", posts.isEmpty());

        // 검색된 데이터의 제목을 확인
        RentalProvidePost post = posts.get(0);
        assertEquals("Test Title", post.getTitle());
        assertEquals("Test Item", post.getRentalItem());
    }

    @Test
    public void testGetAllRentalProvidePosts() throws SQLException {
        // 데이터를 생성
        providePostService.createRentalProvidePost(testPost);

        // 모든 데이터를 조회
        List<RentalProvidePost> posts = providePostService.getAllRentalProvidePosts();
        assertFalse("조회된 RentalProvidePost 목록이 비어 있습니다.", posts.isEmpty());
    }
}
