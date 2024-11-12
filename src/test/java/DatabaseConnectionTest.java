import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import static org.junit.Assert.*;  // JUnit 4에서 Assertions을 위해 Assert 클래스를 사용

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnectionTest {

    private static Connection connection;

    @BeforeClass
    public static void setup() {
        try {
        	String url = "jdbc:oracle:thin:@//dblab.dongduk.ac.kr:1521/orclpdb";
        	String username = "dbp240210";
        	String password = "21703";
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            fail("데이터베이스 연결 실패");
        }
    }

    @Test
    public void testDatabaseConnection() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT 1 FROM DUAL");
            
            assertTrue(rs.next());
        } catch (Exception e) {
            e.printStackTrace();
            fail("쿼리 실행 실패");
        }
    }

    @AfterClass
    public static void teardown() {
        try {
            if (connection != null) connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
