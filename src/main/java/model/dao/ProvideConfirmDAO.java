package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;
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

    public Map<String, Object> getRequesterAndProviderInfo(int requesterId, int providePostId) throws SQLException {
        String sql = "SELECT " +
                     "m1.nickname AS requester_nickname, m1.dormitory_name AS requester_dormitory, m1.room_number AS requester_room, " +
                     "m2.nickname AS provider_nickname, m2.dormitory_name AS provider_dormitory, m2.room_number AS provider_room " +
                     "FROM Member m1 " +
                     "JOIN Rental_provide_confirm rpc ON rpc.requester_id = m1.id " +
                     "JOIN Rental_provide_post rp ON rp.id = rpc.provide_post_id " +
                     "JOIN Member m2 ON rp.provider_id = m2.id " +
                     "WHERE m1.id = ? AND rp.id = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[]{requesterId, providePostId});

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                Map<String, Object> result = new HashMap<>();
                Map<String, Object> requester = new HashMap<>();
                requester.put("nickname", rs.getString("requester_nickname"));
                requester.put("dormitory_name", rs.getString("requester_dormitory"));
                requester.put("room_number", rs.getString("requester_room"));
                result.put("requester", requester);

                Map<String, Object> provider = new HashMap<>();
                provider.put("nickname", rs.getString("provider_nickname"));
                provider.put("dormitory_name", rs.getString("provider_dormitory"));
                provider.put("room_number", rs.getString("provider_room"));
                result.put("provider", provider);

                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

    /**
     * 대여 정보를 상세 조회하여 반환합니다.
     * @param providePostId 제공 글 ID
     * @return 대여 정보를 포함한 Map 객체
     * @throws SQLException 데이터베이스 오류
     */
    public Map<String, Object> getRentalDecisionDetails(int providePostId) throws SQLException {
        String sql = "SELECT " +
                     "rp.rental_item AS itemnickname, " +
                     "m1.nickname AS requester_nickname, m1.dormitory_name AS requester_dormitory, m1.room_number AS requester_room, " +
                     "m2.nickname AS provider_nickname, m2.dormitory_name AS provider_dormitory, m2.room_number AS provider_room, " +
                     "rp.points AS store, rp.rental_location AS rental_place, rp.return_location AS return_place, " +
                     "rp.rental_start_date AS rental_date, rp.rental_end_date AS return_date " +
                     "FROM Rental_provide_post rp " +
                     "JOIN Rental_provide_confirm rpc ON rp.id = rpc.provide_post_id " +
                     "JOIN Member m1 ON rpc.requester_id = m1.id " +
                     "JOIN Member m2 ON rp.provider_id = m2.id " +
                     "WHERE rp.id = ?";

        jdbcUtil.setSqlAndParameters(sql, new Object[]{providePostId});

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                Map<String, Object> result = new HashMap<>();

                result.put("itemnickname", rs.getString("itemnickname"));

                Map<String, Object> requester = new HashMap<>();
                requester.put("nickname", rs.getString("requester_nickname"));
                requester.put("dormitory_name", rs.getString("requester_dormitory"));
                requester.put("room_number", rs.getString("requester_room"));
                result.put("requester", requester);

                Map<String, Object> provider = new HashMap<>();
                provider.put("nickname", rs.getString("provider_nickname"));
                provider.put("dormitory_name", rs.getString("provider_dormitory"));
                provider.put("room_number", rs.getString("provider_room"));
                result.put("provider", provider);

                result.put("store", rs.getInt("store"));
                result.put("rental_place", rs.getString("rental_place"));
                result.put("return_place", rs.getString("return_place"));
                result.put("rental_date", rs.getDate("rental_date"));
                result.put("return_date", rs.getDate("return_date"));

                return result;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SQLException("Error fetching rental decision details", ex);
        } finally {
            jdbcUtil.close();
        }
        return null;
    }
    
}