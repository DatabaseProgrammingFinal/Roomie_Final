import java.sql.*;
import model.dao.RentalProvidePostDAO;
import model.domain.RentalProvidePost;

public class RentalProvidePostTest {
    public static void main(String[] args) {
        // Oracle 데이터베이스 연결 정보
        String url = "jdbc:oracle:thin:@//dblab.dongduk.ac.kr:1521/orclpdb";
        String user = "dbp240210";
        String password = "21703";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // RentalProvidePostDAO 객체 생성
            RentalProvidePostDAO rentalProvidePostDAO = new RentalProvidePostDAO(connection);

            // 테스트 데이터 삽입
            boolean isInserted = insertTestData(connection, rentalProvidePostDAO);
            System.out.println("데이터 삽입 성공 여부: " + isInserted);

            // 데이터 확인
            checkInsertedData(connection, rentalProvidePostDAO);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 테스트 데이터를 삽입하는 메서드
    private static boolean insertTestData(Connection connection, RentalProvidePostDAO dao) {
        RentalProvidePost newPost = new RentalProvidePost();
        newPost.setTitle("Test Rental Post");
        newPost.setRentalItem("Laptop");
        newPost.setContent("A laptop available for rent");
        newPost.setPoints(100);
        newPost.setRentalStartDate(Date.valueOf("2024-11-01"));
        newPost.setRentalEndDate(Date.valueOf("2024-11-10"));
        newPost.setRentalLocation("Dormitory A");
        newPost.setStatus(1); // 상태 값 1 (예: 활성 상태)
        newPost.setProviderId(1); // 제공자 ID
        newPost.setImageUrl("https://example.com/laptop.jpg");

        // DAO를 이용하여 데이터 삽입
        return dao.createRentalProvidePost(newPost);
    }

    // 삽입된 데이터를 확인하는 메서드
    private static void checkInsertedData(Connection connection, RentalProvidePostDAO dao) {
        String title = "Test Rental Post";
        try {
            // Title로 데이터 검색
            RentalProvidePost insertedPost = dao.searchRentalProvidePostsByTitle(title).stream().findFirst().orElse(null);

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
                System.out.println("Provider ID: " + insertedPost.getProviderId());
                System.out.println("Image URL: " + insertedPost.getImageUrl());
            } else {
                System.out.println("삽입된 데이터를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
