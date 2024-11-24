import java.sql.*;

public class RentalProvidePostTest {
    public static void main(String[] args) {
        // Oracle 데이터베이스 연결 정보
        String url = "jdbc:oracle:thin:@//dblab.dongduk.ac.kr:1521/orclpdb";
        String user = "dbp240210";
        String password = "21703";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // 테스트 데이터 삽입
            boolean isInserted = insertTestData(connection);
            System.out.println("데이터 삽입 성공 여부: " + isInserted);

            // 데이터 확인
            checkInsertedData(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean insertTestData(Connection connection) throws SQLException {
        // ID를 시퀀스를 사용하여 삽입하는 방법
        String insertSQL = """
            INSERT INTO rental_provide_post 
                (id, title, rental_item, content, points, rental_start_date, rental_end_date, rental_location, status, provider_id, image_url)
            VALUES (RENTAL_PROVIDE_POST_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, "Test Rental Post");
            pstmt.setString(2, "Laptop");
            pstmt.setString(3, "A laptop available for rent");
            pstmt.setInt(4, 100);
            pstmt.setDate(5, Date.valueOf("2024-11-01"));
            pstmt.setDate(6, Date.valueOf("2024-11-10"));
            pstmt.setString(7, "Dormitory A");
            pstmt.setInt(8, 1); // 상태 값 1 (예: 활성 상태)
            pstmt.setInt(9, 1); // 제공자 ID
            pstmt.setString(10, "https://example.com/laptop.jpg");

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    private static void checkInsertedData(Connection connection) throws SQLException {
        String query = "SELECT * FROM rental_provide_post WHERE title = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, "Test Rental Post");
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("데이터 삽입 확인 성공!");
                    System.out.println("Title: " + rs.getString("title"));
                    System.out.println("Item: " + rs.getString("rental_item"));
                    System.out.println("Points: " + rs.getInt("points"));
                    System.out.println("Start Date: " + rs.getDate("rental_start_date"));
                    System.out.println("End Date: " + rs.getDate("rental_end_date"));
                    System.out.println("Location: " + rs.getString("rental_location"));
                    System.out.println("Status: " + rs.getInt("status"));
                    System.out.println("Provider ID: " + rs.getInt("provider_id"));
                    System.out.println("Image URL: " + rs.getString("image_url"));
                } else {
                    System.out.println("데이터 삽입 실패.");
                }
            }
        }
    }
}
