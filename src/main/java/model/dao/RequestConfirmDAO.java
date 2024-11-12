package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.domain.RequestConfirm;

/**
 * 요청글 확인을 위해 데이터베이스 작업을 전담하는 DAO 클래스
 * Rental_provide_confirm 테이블에 등록 정보를 추가, 검색 수행 
 */
public class RequestConfirmDAO {
	private JDBCUtil jdbcUtil = null;
	public RequestConfirmDAO() {
		jdbcUtil = new JDBCUtil();
	}
	
	public RequestConfirm create(RequestConfirm rc) throws SQLException{
		String sql = "INSERT INTO Rental_request_confirm " +
                "(id, actual_return_date, penalty_points, overdue_days, provide_post_id, requester_id) " +
                "VALUES (Rental_request_confirm_seq.NEXTVAL, TO_DATE('2024-11-02', 'YYYY-MM-DD'), 0, 0, 1, 1)";
	
		Object[] param = new Object[] {rc.getId(), rc.getActual_return_date(), rc.getPenalty_points(), rc.getOverdue_days(), rc.getRequest_post_id(), rc.getProvider_id()};

		jdbcUtil.setSqlAndParameters(sql, param);
		String key[] = {"id"}; // PK 컬럼 이름
		
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
	

	public RequestConfirm findById(int id) throws SQLException {
        String sql = "SELECT id, actual_return_date, penalty_points, overdue_days, provide_post_id, requester_id " +
                     "FROM Rental_provide_confirm " +
                     "WHERE id = ?";

        jdbcUtil.setSqlAndParameters(sql, new Object[]{id});

        try {
            ResultSet rs = jdbcUtil.executeQuery(); 
            if (rs.next()) {
            	RequestConfirm pc = new RequestConfirm();
                pc.setId(rs.getInt("id"));
                pc.setActual_return_date(rs.getDate("actual_return_date"));
                pc.setPenalty_points(rs.getInt("penalty_points"));
                pc.setOverdue_days(rs.getInt("overdue_days"));
                pc.setRequest_post_id(rs.getInt("request_post_id"));
                pc.setProvider_id(rs.getInt("provider_id"));
                return pc; 
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close(); 
        }
        return null; 
    }
}

