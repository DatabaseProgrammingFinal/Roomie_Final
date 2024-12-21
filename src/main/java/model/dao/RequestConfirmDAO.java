package model.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import model.domain.RentalRequestPost;
import model.domain.RequestConfirm;

/**
 * 요청글 확인을 위해 데이터베이스 작업을 전담하는 DAO 클래스 Rental_request_confirm 테이블에 등록 정보를 추가, 검색
 * 수행
 */
public class RequestConfirmDAO {
    private JDBCUtil jdbcUtil = null;

    public RequestConfirmDAO() {
        jdbcUtil = new JDBCUtil();
    }

    /* 테이블에 새로운 행 생성, pk 값은 sequence이용해서 자동 생성 */
    public RequestConfirm create(RequestConfirm rc) throws SQLException {
        String sql = "INSERT INTO Rental_request_confirm "
                + "(id, actual_return_date, penalty_points, overdue_days, request_post_id, provider_id) "
                + "VALUES (Rental_request_confirm_seq.NEXTVAL, ?, ?, ?, ?, ?)";

        Object[] param = new Object[] { new java.sql.Date(rc.getActual_return_date().getTime()), // Date 객체로 변환
                rc.getPenalty_points(), rc.getOverdue_days(), rc.getRequest_post_id(), rc.getProvider_id() };
        jdbcUtil.setSqlAndParameters(sql, param);

        String[] key = { "id" }; // PK 컬럼 이름
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

    public void update(RequestConfirm rc) throws SQLException {
        String sql = "UPDATE Rental_request_confirm " + "SET penalty_points = ?, overdue_days = ? " + "WHERE id = ?";

        Object[] param = new Object[] { rc.getPenalty_points(), rc.getOverdue_days(), rc.getId() };

        jdbcUtil.setSqlAndParameters(sql, param);

        try {
            jdbcUtil.executeUpdate();
            jdbcUtil.commit();
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
    }

    public void updateActualReturnDate(int requestConfirmId, Date actualReturnDate) throws SQLException {
        String sql = "UPDATE Rental_request_confirm " + "SET actual_return_date = ? " + "WHERE id = ?";

        Object[] params = new Object[] { new java.sql.Date(actualReturnDate.getTime()), requestConfirmId };

        jdbcUtil.setSqlAndParameters(sql, params);

        try {
            jdbcUtil.executeUpdate();
            jdbcUtil.commit();
        } catch (Exception ex) {
            jdbcUtil.rollback();
            throw new SQLException("Error updating actual return date", ex);
        } finally {
            jdbcUtil.close();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Rental_request_confirm WHERE id = ?";

        jdbcUtil.setSqlAndParameters(sql, new Object[] { id });

        try {
            jdbcUtil.executeUpdate();
            jdbcUtil.commit();
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
    }

    public RequestConfirm findById(int id) throws SQLException {
        String sql = "SELECT id, actual_return_date, penalty_points, overdue_days, request_post_id, provider_id "
                + "FROM Rental_request_confirm " + "WHERE id = ?";

        jdbcUtil.setSqlAndParameters(sql, new Object[] { id });

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

    public Map<String, Object> getRequesterAndProviderInfo(int providerId, int requestPostId) throws SQLException {
        String requesterSql = "SELECT nickname, dormitory_name, room_number " + "FROM Member " + "WHERE id = ?";
        jdbcUtil.setSqlAndParameters(requesterSql, new Object[] { providerId });

        System.out.println("Executing SQL: " + requesterSql + " with ID: " + providerId);

        Map<String, Object> result = new HashMap<>();
        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                System.out.println("Requester found: " + rs.getString("nickname"));
                Map<String, Object> requester = new HashMap<>();
                requester.put("nickname", rs.getString("nickname"));
                requester.put("dormitory_name", rs.getString("dormitory_name"));
                requester.put("room_number", rs.getString("room_number"));
                result.put("requester", requester);
            } else {
                throw new SQLException("Requester not found with ID: " + providerId);
            }
        } finally {
            jdbcUtil.close();
        }

        // 제공자 정보 가져오기
        String providerSql = "SELECT m.nickname, m.dormitory_name, m.room_number " + "FROM Rental_request_post rr "
                + "JOIN Member m ON rr.requester_id = m.id " + "WHERE rr.id = ?";
        jdbcUtil.setSqlAndParameters(providerSql, new Object[] { requestPostId });

        System.out.println("Executing SQL: " + providerSql + " with Request Post ID: " + requestPostId);

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                System.out.println("Provider found: " + rs.getString("nickname"));
                Map<String, Object> provider = new HashMap<>();
                provider.put("nickname", rs.getString("nickname"));
                provider.put("dormitory_name", rs.getString("dormitory_name"));
                provider.put("room_number", rs.getString("room_number"));
                result.put("provider", provider);
            } else {
                throw new SQLException("Provider not found for requestPostId: " + requestPostId);
            }
        } finally {
            jdbcUtil.close();
        }

        return result;
    }

    /**
     * 대여 정보를 상세 조회하여 반환합니다.
     * 
     * @param requestConfirmId 요청 대여 ID
     * @return 대여 정보를 포함한 Map 객체
     * @throws SQLException 데이터베이스 오류
     */
    public Map<String, Object> getRentalDecisionDetails(int requestConfirmId) throws SQLException {
        String sql = "SELECT " + "rr.rental_item AS itemnickname, "
                + "m1.nickname AS provider_nickname, m1.dormitory_name AS provider_dormitory, m1.room_number AS provider_room, "
                + "m2.nickname AS requester_nickname, m2.dormitory_name AS requester_dormitory, m2.room_number AS requester_room, "
                + "rr.points AS store, rr.rental_location AS rental_place, rr.return_location AS return_place, "
                + "rr.rental_start_date AS rental_date, rr.rental_end_date AS return_date "
                + "FROM Rental_request_post rr " + "JOIN Rental_request_confirm rrc ON rr.id = rrc.request_post_id "
                + "JOIN Member m1 ON rrc.provider_id = m1.id " + "JOIN Member m2 ON rr.requester_id = m2.id "
                + "WHERE rrc.id = ?"; // RequestConfirm의 id를 기준으로 변경

        jdbcUtil.setSqlAndParameters(sql, new Object[] { requestConfirmId });

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                Map<String, Object> result = new HashMap<>();

                result.put("itemnickname", rs.getString("itemnickname"));

                Map<String, Object> provider = new HashMap<>();
                provider.put("nickname", rs.getString("provider_nickname"));
                provider.put("dormitory_name", rs.getString("provider_dormitory"));
                provider.put("room_number", rs.getString("provider_room"));
                result.put("provider", provider);

                Map<String, Object> requester = new HashMap<>();
                requester.put("nickname", rs.getString("requester_nickname"));
                requester.put("dormitory_name", rs.getString("requester_dormitory"));
                requester.put("room_number", rs.getString("requester_room"));
                result.put("requester", requester);

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

    public RentalRequestPost getRentalRequestPostById(int requestConfirmId) throws SQLException {
        // SQL 작성: request_post_id를 기반으로 RentalRequestPost를 조회
        String sql = "SELECT * FROM RentalRequestPost WHERE id = "
                + "(SELECT request_post_id FROM Rental_request_confirm WHERE id = ?)";

        jdbcUtil.setSqlAndParameters(sql, new Object[] { requestConfirmId });

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                // RentalRequestPost 객체 생성 및 데이터 설정
                RentalRequestPost rentalRequestPost = new RentalRequestPost();
                rentalRequestPost.setId(rs.getInt("id"));
                rentalRequestPost.setPoints(rs.getInt("points"));
                rentalRequestPost.setRentalLocation(rs.getString("rental_location"));
                rentalRequestPost.setReturnLocation(rs.getString("return_location"));
                rentalRequestPost.setRentalStartDate(rs.getDate("rental_start_date"));
                rentalRequestPost.setRentalEndDate(rs.getDate("rental_end_date"));
                return rentalRequestPost;
            } else {
                throw new SQLException("RentalRequestPost ID " + requestConfirmId + "에 해당하는 데이터가 존재하지 않습니다.");
            }
        } finally {
            jdbcUtil.close();
        }
    }

    public void updateRentalDecisionDetails(int requestConfirmId, String store, String rentalPlace, String returnPlace,
            Date rentalDate, Date returnDate) throws SQLException {
        // Rental_request_confirm 테이블에서 request_post_id를 가져오는 쿼리
        String fetchRequestPostIdSql = "SELECT request_post_id FROM Rental_request_confirm WHERE id = ?";

        jdbcUtil.setSqlAndParameters(fetchRequestPostIdSql, new Object[] { requestConfirmId });

        int requestPostId;

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                requestPostId = rs.getInt("request_post_id");
            } else {
                throw new SQLException("해당 requestConfirmId에 대한 레코드가 존재하지 않습니다: " + requestConfirmId);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SQLException("request_post_id 조회 중 오류 발생", ex);
        } finally {
            jdbcUtil.close();
        }

        // Rental_request_post 테이블의 데이터를 업데이트하는 쿼리
        String updateSql = "UPDATE Rental_request_post "
                + "SET points = ?, rental_location = ?, return_location = ?, rental_start_date = ?, rental_end_date = ? "
                + "WHERE id = ?";

        Object[] updateParams = new Object[] { Integer.parseInt(store), // store 값을 정수로 변환하여 전달
                rentalPlace, returnPlace, rentalDate, returnDate, requestPostId };

        jdbcUtil.setSqlAndParameters(updateSql, updateParams);

        try {
            jdbcUtil.executeUpdate();
            jdbcUtil.commit();
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
            throw new SQLException("대여 결정 세부정보 업데이트 중 오류 발생", ex);
        } finally {
            jdbcUtil.close();
        }
    }

    /**
     * RequestConfirm의 actual_return_date 조회
     */
    public Date getActualReturnDate(int requestConfirmId) throws SQLException {
        String sql = "SELECT actual_return_date FROM Rental_request_confirm WHERE id = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] { requestConfirmId }); // SQL과 매개변수 설정

        try {
            ResultSet rs = jdbcUtil.executeQuery(); // 쿼리 실행
            if (rs.next()) {
                return rs.getDate("actual_return_date"); // actual_return_date 반환
            }
        } catch (Exception ex) {
            throw new SQLException("RequestConfirm ID로 actual_return_date를 가져오는 중 오류 발생: " + requestConfirmId, ex);
        } finally {
            jdbcUtil.close(); // 리소스 정리
        }
        return null; // 데이터가 없을 경우 null 반환
    }

    public void updateOverdueDays(int requestConfirmId) throws SQLException {
        String sqlOverdueDays = "UPDATE Rental_request_confirm rrc "
                + "SET overdue_days = (SELECT CASE WHEN actual_return_date > rr.rental_end_date THEN "
                + "CAST(actual_return_date - rr.rental_end_date AS INTEGER) ELSE 0 END "
                + "FROM Rental_request_post rr WHERE rr.id = rrc.request_post_id) " + "WHERE rrc.id = ?";

        String sqlPenaltyPoints = "UPDATE Rental_request_confirm " + "SET penalty_points = overdue_days * 50 "
                + "WHERE id = ?";

        try {
            // 연체 일수 업데이트
            jdbcUtil.setSqlAndParameters(sqlOverdueDays, new Object[] { requestConfirmId });
            jdbcUtil.executeUpdate();

            // 패널티 점수 업데이트
            jdbcUtil.setSqlAndParameters(sqlPenaltyPoints, new Object[] { requestConfirmId });
            jdbcUtil.executeUpdate();

            jdbcUtil.commit();
        } catch (Exception ex) {
            jdbcUtil.rollback();
            throw new SQLException("Error updating overdue days and penalty points", ex);
        } finally {
            jdbcUtil.close();
        }
    }

    public Map<String, Integer> getOverdueAndPoints(int requestConfirmId) throws SQLException {
        String sql = "SELECT rrc.overdue_days, rrc.penalty_points, rr.points " + "FROM Rental_request_confirm rrc "
                + "JOIN Rental_request_post rr ON rrc.request_post_id = rr.id " + "WHERE rrc.id = ?";

        jdbcUtil.setSqlAndParameters(sql, new Object[] { requestConfirmId });

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                Map<String, Integer> result = new HashMap<>();
                result.put("overdue_days", rs.getInt("overdue_days"));
                result.put("penalty_points", rs.getInt("penalty_points"));
                result.put("points", rs.getInt("points"));
                return result;
            }
        } catch (Exception ex) {
            throw new SQLException("Error fetching overdue days, penalty points, and points for RequestConfirm ID: "
                    + requestConfirmId, ex);
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

    public void updateMemberPoints(int requestConfirmId) throws SQLException {
        // 요청자 ID, 제공자 ID, penalty_points, points 가져오기
        String fetchSql = "SELECT rrc.provider_id, rr.requester_id, rrc.penalty_points, rr.points "
                + "FROM Rental_request_confirm rrc " + "JOIN Rental_request_post rr ON rrc.request_post_id = rr.id "
                + "WHERE rrc.id = ?";

        jdbcUtil.setSqlAndParameters(fetchSql, new Object[] { requestConfirmId });

        int providerId, requesterId, penaltyPoints, points;

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                providerId = rs.getInt("provider_id");
                requesterId = rs.getInt("requester_id");
                penaltyPoints = rs.getInt("penalty_points");
                points = rs.getInt("points");
            } else {
                throw new SQLException("RequestConfirm not found with ID: " + requestConfirmId);
            }
        } finally {
            jdbcUtil.close();
        }

        // Step 2: Update 요청자 포인트 차감
        String updateRequesterSql = "UPDATE Member SET points = points - ? WHERE id = ?";
        jdbcUtil.setSqlAndParameters(updateRequesterSql, new Object[] { penaltyPoints, requesterId });

        try {
            jdbcUtil.executeUpdate();
        } catch (Exception ex) {
            jdbcUtil.rollback();
            throw new SQLException("Error updating requester points", ex);
        }

        // Step 3: Update 제공자 포인트 추가
        String updateProviderSql = "UPDATE Member SET points = points + ? WHERE id = ?";
        jdbcUtil.setSqlAndParameters(updateProviderSql, new Object[] { points, providerId });

        try {
            jdbcUtil.executeUpdate();
            jdbcUtil.commit();
        } catch (Exception ex) {
            jdbcUtil.rollback();
            throw new SQLException("Error updating provider points", ex);
        } finally {
            jdbcUtil.close();
        }
    }
}
