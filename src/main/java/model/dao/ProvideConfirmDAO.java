package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.domain.ProvideConfirm;

/**
 * 제공글 확인을 위해 데이터베이스 작업을 전담하는 DAO 클래스
 * RENTAL_PROVIDE_CONFIRM 테이블에 등록 정보를 추가, 검색 수행 
 */
public class ProvideConfirmDAO {
	private JDBCUtil jdbcUtil = null;
	
	public ProvideConfirmDAO() {
		jdbcUtil = new JDBCUtil();
	}
	
	/*테이블에 새로운 행 생성, pk 값은 sequence이용해서 자동 생성*/
	public ProvideConfirm create(ProvideConfirm pc) throws SQLException {
	    String sql = "INSERT INTO Rental_provide_confirm " +
	                 "(id, actual_return_date, penalty_points, overdue_days, provide_post_id, requester_id) " +
	                 "VALUES (Rental_request_confirm_seq.NEXTVAL, ?, ?, ?, ?, ?)";

	    Object[] param = new Object[] {
	        new java.sql.Date(pc.getActual_return_date().getTime()), // Date 객체로 변환
	        pc.getPenalty_points(),
	        pc.getOverdue_days(),
	        pc.getProvide_post_id(),
	        pc.getRequester_id()
	    };
	    jdbcUtil.setSqlAndParameters(sql, param);

	    String[] key = {"id"}; // PK 컬럼 이름
	    try {
	        jdbcUtil.executeUpdate(key); // insert 문 실행
	        ResultSet rs = jdbcUtil.getGeneratedKeys();
	        if (rs.next()) {
	            int generatedKey = rs.getInt(1); 
	            pc.setId(generatedKey);          
	        }
	        return pc;
	    } catch (Exception ex) {
	        jdbcUtil.rollback();
	        ex.printStackTrace();
	        return null;
	    } finally {
	        jdbcUtil.commit();
	        jdbcUtil.close(); 
	    }
	}

	
	public ProvideConfirm findById(int id) throws SQLException {
        String sql = "SELECT id, actual_return_date, penalty_points, overdue_days, provide_post_id, requester_id " +
                     "FROM Rental_provide_confirm " +
                     "WHERE id = ?";

        jdbcUtil.setSqlAndParameters(sql, new Object[]{id});

        try {
            ResultSet rs = jdbcUtil.executeQuery(); 
            if (rs.next()) {
                ProvideConfirm pc = new ProvideConfirm();
                pc.setId(rs.getInt("id"));
                pc.setActual_return_date(rs.getDate("actual_return_date"));
                pc.setPenalty_points(rs.getInt("penalty_points"));
                pc.setOverdue_days(rs.getInt("overdue_days"));
                pc.setProvide_post_id(rs.getInt("provide_post_id"));
                pc.setRequester_id(rs.getInt("requester_id"));
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
