import java.sql.*;

public class RentalRequestPostTest {
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
            INSERT INTO rental_request_post 
                (id, title, rental_item, content, points, rental_start_date, rental_end_date, rental_location, status, requester_id)
            VALUES (RENTAL_REQUEST_POST_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, "Test Title");
            pstmt.setString(2, "Test Item");
            pstmt.setString(3, "Test Content");
            pstmt.setInt(4, 10);
            pstmt.setDate(5, Date.valueOf("2024-11-01"));
            pstmt.setDate(6, Date.valueOf("2024-11-10"));
            pstmt.setString(7, "Dormitory A");
            pstmt.setInt(8, 0); // 상태 값 0 (예: 요청 상태)
            pstmt.setInt(9, 1); // 요청자 ID

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    private static void checkInsertedData(Connection connection) throws SQLException {
        String query = "SELECT * FROM rental_request_post WHERE title = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, "Test Title");
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
                    System.out.println("Requester ID: " + rs.getInt("requester_id"));
                } else {
                    System.out.println("데이터 삽입 실패.");
                }
            }
        }
    }
}
