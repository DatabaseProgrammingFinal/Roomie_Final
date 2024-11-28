import java.sql.*;
import model.dao.RequestPostDAO;
import model.domain.RentalRequestPost;

public class RequestPostDAOTest {
    public static void main(String[] args) {
        // Oracle 데이터베이스 연결 정보
        String url = "jdbc:oracle:thin:@//dblab.dongduk.ac.kr:1521/orclpdb";
        String user = "dbp240210";
        String password = "21703";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // RentalRequestPostDAO 객체 생성
            RequestPostDAO requestPostDAO = new RequestPostDAO(connection);

            // 테스트 데이터 삽입
            boolean isInserted = insertTestData(connection, requestPostDAO);
            System.out.println("데이터 삽입 성공 여부: " + isInserted);

            // 데이터 확인
            checkInsertedData(connection, requestPostDAO);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 테스트 데이터를 삽입하는 메서드
    private static boolean insertTestData(Connection connection, RequestPostDAO dao) {
        RentalRequestPost newPost = new RentalRequestPost();
        newPost.setTitle("Test Request Post");
        newPost.setRentalItem("Projector");
        newPost.setContent("A request for a projector");
        newPost.setPoints(50);
        newPost.setRentalStartDate(Date.valueOf("2024-12-01"));
        newPost.setRentalEndDate(Date.valueOf("2024-12-05"));
        newPost.setRentalLocation("Library");
        newPost.setStatus(0); // 상태 값 0 (예: 요청 상태)
        newPost.setRequesterId(1); // 요청자 ID

        // DAO를 이용하여 데이터 삽입
        return dao.createRentalRequestPost(newPost);
    }

    // 삽입된 데이터를 확인하는 메서드
    private static void checkInsertedData(Connection connection, RequestPostDAO dao) {
        String title = "Test Request Post";
        try {
            // Title로 데이터 검색
            RentalRequestPost insertedPost = dao.searchRentalRequestPostsByTitle(title).stream().findFirst().orElse(null);

            if (insertedPost != null) {
                System.out.println("데이터 삽입 확인 성공!");
                System.out.println("ID: " + insertedPost.getId());
                System.out.println("Title: " + insertedPost.getTitle());
                System.out.println("Rental Item: " + insertedPost.getRentalItem());
                System.out.println("Content: " + insertedPost.getContent());
                System.out.println("Points: " + insertedPost.getPoints());
                System.out.println("Start Date: " + insertedPost.getRentalStartDate());
                System.out.println("End Date: " + insertedPost.getRentalEndDate());
                System.out.println("Location: " + insertedPost.getRentalLocation());
                System.out.println("Status: " + insertedPost.getStatus());
                System.out.println("Requester ID: " + insertedPost.getRequesterId());
            } else {
                System.out.println("삽입된 데이터를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
