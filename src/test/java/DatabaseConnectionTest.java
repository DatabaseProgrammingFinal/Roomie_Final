import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;

import static org.junit.Assert.*; // JUnit 4에서 Assertions을 위해 Assert 클래스를 사용

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseConnectionTest {

    private static Connection connection;

    @BeforeClass
    public static void setup() {
        try {
            String url = "jdbc:oracle:thin:@//dblab.dongduk.ac.kr:1521/orclpdb";
            String username = "dbp240210";
            String password = "21703";
            connection = DriverManager.getConnection(url, username, password);

            if (connection == null || connection.isClosed()) {
                throw new RuntimeException("Connection 초기화 실패");
            }
            System.out.println("Connection이 성공적으로 생성되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("데이터베이스 연결 실패");
        }
    }


    @Test
    public void testDatabaseConnection() {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT 1 FROM DUAL")) {

            assertTrue("결과가 없습니다.", rs.next());
        } catch (Exception e) {
            e.printStackTrace();
            fail("쿼리 실행 실패");
        }
    }

    @AfterClass
    public static void tearDown() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection이 정상적으로 닫혔습니다.");
            } else {
                System.out.println("Connection이 이미 닫혀 있거나 null입니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Connection 종료 실패");
        }
    }

}