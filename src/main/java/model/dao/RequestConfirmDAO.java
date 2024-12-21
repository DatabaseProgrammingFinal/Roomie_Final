package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.domain.RequestConfirm;

/**
 * 요청글 확인을 위해 데이터베이스 작업을 전담하는 DAO 클래스
 * Rental_request_confirm 테이블에 등록 정보를 추가, 검색 수행
 */
public class RequestConfirmDAO {
    private JDBCUtil jdbcUtil = null;

    public RequestConfirmDAO() {
        jdbcUtil = new JDBCUtil();
    }

    /* 테이블에 새로운 행 생성, pk 값은 sequence를 이용해 자동 생성 */
    public RequestConfirm create(RequestConfirm rc) throws SQLException {
        String sql = "INSERT INTO Rental_request_confirm " +
                     "(id, actual_return_date, penalty_points, overdue_days, request_post_id, provider_id) " +
                     "VALUES (Rental_request_confirm_seq.NEXTVAL, ?, ?, ?, ?, ?)";

        Object[] param = new Object[] {
            new java.sql.Date(rc.getActual_return_date().getTime()), // Date 객체로 변환
            rc.getPenalty_points(),
            rc.getOverdue_days(),
            rc.getRequest_post_id(),
            rc.getProvider_id()
        };
        jdbcUtil.setSqlAndParameters(sql, param);

        String[] key = {"id"}; // PK 컬럼 이름
        try {
            jdbcUtil.executeUpdate(key); // insert 문 실행
            ResultSet rs = jdbcUtil.getGeneratedKeys();
            if (rs.next()) {
                int generatedKey = rs.getInt(1);
                rc.setId(generatedKey);
            }
            return rc;
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
            return null;
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close();
        }
    }

    /* 특정 ID로 데이터를 조회 */
    public RequestConfirm findById(int id) throws SQLException {
        String sql = "SELECT id, actual_return_date, penalty_points, overdue_days, request_post_id, provider_id " +
                     "FROM Rental_request_confirm " +
                     "WHERE id = ?";

        jdbcUtil.setSqlAndParameters(sql, new Object[]{id});

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                RequestConfirm rc = new RequestConfirm();
                rc.setId(rs.getInt("id"));
                rc.setActual_return_date(rs.getDate("actual_return_date"));
                rc.setPenalty_points(rs.getInt("penalty_points"));
                rc.setOverdue_days(rs.getInt("overdue_days"));
                rc.setRequest_post_id(rs.getInt("request_post_id"));
                rc.setProvider_id(rs.getInt("provider_id"));
                return rc;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }
}
