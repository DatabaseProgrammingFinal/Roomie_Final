package model.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionManager {
    private static DataSource ds = null;

    public ConnectionManager() {
        if (ds == null) { // DataSource가 초기화되지 않은 경우만 설정
            initializeDataSource();
        }
    }

    private void initializeDataSource() {
        InputStream input = null;
        Properties prop = new Properties();

        try {
            // context.properties 파일 읽기
            input = Thread.currentThread().getContextClassLoader().getResourceAsStream("context.properties");
            if (input == null) {
                throw new IOException("context.properties 파일을 찾을 수 없습니다. 경로를 확인하세요.");
            }
            prop.load(input);

            // DataSource 생성 및 설정
            BasicDataSource bds = new BasicDataSource();
            bds.setDriverClassName(prop.getProperty("DB_DRIVER"));
            bds.setUrl(prop.getProperty("DB_URL"));
            bds.setUsername(prop.getProperty("DB_USER"));
            bds.setPassword(prop.getProperty("DB_PASSWORD"));

            // 연결 풀 크기 설정 (필요 시)
            bds.setInitialSize(5); // 초기 연결 개수
            bds.setMaxTotal(20); // 최대 연결 개수
            bds.setMaxIdle(10); // 최대 유휴 연결 개수
            bds.setMinIdle(2); // 최소 유휴 연결 개수

            ds = bds; // DataSource 초기화

        } catch (IOException e) {
            System.err.println("데이터베이스 설정 파일 로드 중 오류 발생:");
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            if (ds == null) {
                throw new SQLException("DataSource가 초기화되지 않았습니다.");
            }
            conn = ds.getConnection(); // 연결 가져오기
        } catch (SQLException e) {
            System.err.println("데이터베이스 연결 중 오류 발생:");
            e.printStackTrace();
        }
        return conn;
    }

    public void close() {
        if (ds instanceof BasicDataSource) {
            try {
                ((BasicDataSource) ds).close();
                System.out.println("DataSource가 성공적으로 종료되었습니다.");
            } catch (SQLException ex) {
                System.err.println("DataSource 종료 중 오류 발생:");
                ex.printStackTrace();
            }
        }
    }

    // 현재 활성화 상태인 Connection의 개수와 비활성화 상태인 Connection 개수 출력
    public void printDataSourceStats() {
        if (ds instanceof BasicDataSource) {
            BasicDataSource bds = (BasicDataSource) ds;
            System.out.println("현재 활성 연결 수 (NumActive): " + bds.getNumActive());
            System.out.println("현재 유휴 연결 수 (NumIdle): " + bds.getNumIdle());
        } else {
            System.err.println("DataSource가 BasicDataSource 인스턴스가 아닙니다.");
        }
    }
}
