package model.dao;

import java.sql.*;

public class JDBCUtil {
    private static ConnectionManager connMan = new ConnectionManager();
    private String sql = null; // 실행할 query
    private Object[] parameters = null; // PreparedStatement 의 매개변수 값을 저장하는 배열
    private static Connection conn = null;
    private PreparedStatement pstmt = null;
    private CallableStatement cstmt = null;
    private ResultSet rs = null;
    private int resultSetType = ResultSet.TYPE_FORWARD_ONLY, resultSetConcurrency = ResultSet.CONCUR_READ_ONLY;

    // 기본 생성자
    public JDBCUtil() {
    }

    // Connection을 반환하는 메서드 추가
    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = connMan.getConnection();
            conn.setAutoCommit(false);
        }
        return conn;
    }

    // sql 변수 getter
    public String getSql() {
        return this.sql;
    }

    // 매개변수 배열에서 특정위치의 매개변수를 반환하는 메소드
    private Object getParameter(int index) throws Exception {
        if (index >= getParameterSize())
            throw new Exception("INDEX 값이 파라미터의 갯수보다 많습니다.");
        return parameters[index];
    }

    // 매개변수의 개수를 반환하는 메소드
    private int getParameterSize() {
        return parameters == null ? 0 : parameters.length;
    }

    // sql 및 Object[] 변수 setter
    public void setSqlAndParameters(String sql, Object[] parameters) {
        this.sql = sql;
        this.parameters = parameters;
        this.resultSetType = ResultSet.TYPE_FORWARD_ONLY;
        this.resultSetConcurrency = ResultSet.CONCUR_READ_ONLY;
    }

    // sql 및 Object[], resultSetType, resultSetConcurrency 변수 setter
    public void setSqlAndParameters(String sql, Object[] parameters, int resultSetType, int resultSetConcurrency) {
        this.sql = sql;
        this.parameters = parameters;
        this.resultSetType = resultSetType;
        this.resultSetConcurrency = resultSetConcurrency;
    }

    // 현재의 PreparedStatement를 반환
    private PreparedStatement getPreparedStatement() throws SQLException {
        if (conn == null) {
            conn = getConnection();
            conn.setAutoCommit(false);
        }
        if (pstmt != null)
            pstmt.close();
        pstmt = conn.prepareStatement(sql, resultSetType, resultSetConcurrency);
        return pstmt;
    }

    // JDBCUtil의 쿼리와 매개변수를 이용해 executeQuery를 수행하는 메소드
    public ResultSet executeQuery() {
        try {
            pstmt = getPreparedStatement();
            for (int i = 0; i < getParameterSize(); i++) {
                pstmt.setObject(i + 1, getParameter(i));
            }
            rs = pstmt.executeQuery();
            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // JDBCUtil의 쿼리와 매개변수를 이용해 executeUpdate를 수행하는 메소드
    public int executeUpdate() throws SQLException, Exception {
        pstmt = getPreparedStatement();
        int parameterSize = getParameterSize();
        System.out.println("Executing SQL: " + sql); // SQL 쿼리 출력
        System.out.println("Parameters:"); // 매개변수 출력 시작


        int expectedParams = sql.split("\\?").length - 1; // 예상되는 ?의 개수 계산
        System.out.println("Expected parameters count: " + expectedParams);

        
        for (int i = 0; i < parameterSize; i++) {
            Object param = getParameter(i);
            if (param == null) { // 매개변수 값이 null인 경우
                pstmt.setNull(i + 1, java.sql.Types.NULL); // 적절한 SQL 타입 지정
                System.out.println("  [" + (i + 1) + "]: NULL");
            } else {
                pstmt.setObject(i + 1, param);
                System.out.println("  [" + (i + 1) + "]: " + param + " (" + param.getClass().getSimpleName() + ")");
            }
        }

        System.out.println("SQL execution started...");
        return pstmt.executeUpdate();
    }


    // 현재의 CallableStatement를 반환
    private CallableStatement getCallableStatement() throws SQLException {
        if (conn == null) {
            conn = getConnection();
            conn.setAutoCommit(false);
        }
        if (cstmt != null)
            cstmt.close();
        cstmt = conn.prepareCall(sql);
        return cstmt;
    }

    // JDBCUtil의 쿼리와 매개변수를 이용해 CallableStatement의 execute를 수행하는 메소드
    public boolean execute(JDBCUtil source) throws SQLException, Exception {
        cstmt = getCallableStatement();
        for (int i = 0; i < source.getParameterSize(); i++) {
            cstmt.setObject(i + 1, source.getParameter(i));
        }
        return cstmt.execute();
    }

    // PK 컬럼 이름 배열을 이용하여 PreparedStatement를 생성 (INSERT문에서 Sequence를 통해 PK 값을 생성하는 경우)
    private PreparedStatement getPreparedStatement(String[] columnnicknames) throws SQLException {
        if (conn == null) {
            conn = getConnection();
            conn.setAutoCommit(false);
        }
        if (pstmt != null)
            pstmt.close();
        pstmt = conn.prepareStatement(sql, columnnicknames);
        return pstmt;
    }

    // 위 메소드를 이용하여 PreparedStatement를 생성한 후 executeUpdate 실행
    public int executeUpdate(String[] columnnicknames) throws SQLException, Exception {
        pstmt = getPreparedStatement(columnnicknames); // 위 메소드를 호출
        int parameterSize = getParameterSize();
        for (int i = 0; i < parameterSize; i++) {
            if (getParameter(i) == null) { // 매개변수 값이 널이 부분이 있을 경우
                pstmt.setString(i + 1, null);
            } else {
                pstmt.setObject(i + 1, getParameter(i));
            }
        }
        return pstmt.executeUpdate();
    }

    // PK 컬럼의 값(들)을 포함하는 ResultSet 객체 구하기
    public ResultSet getGeneratedKeys() {
        try {
            return pstmt.getGeneratedKeys();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 자원 반환
    public void close() {
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
                pstmt = null;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (cstmt != null) {
            try {
                cstmt.close();
                cstmt = null;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void commit() {
        try {
            conn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void rollback() {
        try {
            conn.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // DataSource 를 종료
    public void shutdownPool() {
        this.close();
        connMan.close();
    }

    // 현재 활성화 상태인 Connection 의 개수와 비활성화 상태인 Connection 개수 출력
    public void printDataSourceStats() {
        connMan.printDataSourceStats();
    }
}
