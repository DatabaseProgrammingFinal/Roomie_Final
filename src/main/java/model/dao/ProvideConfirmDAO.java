package model.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import model.domain.ProvideConfirm;
import model.domain.RentalProvidePost;

/**
 * 제공글 확인을 위해 데이터베이스 작업을 전담하는 DAO 클래스 RENTAL_PROVIDE_CONFIRM 테이블에 등록 정보를 추가, 검색
 * 수행
 */
public class ProvideConfirmDAO {
    private JDBCUtil jdbcUtil = null;

    public ProvideConfirmDAO() {
        jdbcUtil = new JDBCUtil();
    }

    /* 테이블에 새로운 행 생성, pk 값은 sequence이용해서 자동 생성 */
    public ProvideConfirm create(ProvideConfirm pc) throws SQLException {
        String sql = "INSERT INTO Rental_provide_confirm "
                + "(id, actual_return_date, penalty_points, overdue_days, provide_post_id, requester_id) "
                + "VALUES (Rental_provide_confirm_seq.NEXTVAL, ?, ?, ?, ?, ?)";

        Object[] param = new Object[] { new java.sql.Date(pc.getActual_return_date().getTime()), // Date 객체로 변환
                pc.getPenalty_points(), pc.getOverdue_days(), pc.getProvide_post_id(), pc.getRequester_id() };
        jdbcUtil.setSqlAndParameters(sql, param);

        String[] key = { "id" }; // PK 컬럼 이름
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

    public void update(ProvideConfirm pc) throws SQLException {
        String sql = "UPDATE Rental_provide_confirm " + "SET penalty_points = ?, overdue_days = ? " + "WHERE id = ?";

        Object[] param = new Object[] { pc.getPenalty_points(), pc.getOverdue_days(), pc.getId() };

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

    public void updateActualReturnDate(int provideConfirmId, Date actualReturnDate) throws SQLException {
        String sql = "UPDATE Rental_provide_confirm " + "SET actual_return_date = ? " + "WHERE id = ?";

        Object[] params = new Object[] { new java.sql.Date(actualReturnDate.getTime()), provideConfirmId };

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
        String sql = "DELETE FROM Rental_provide_confirm WHERE id = ?";

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

    public ProvideConfirm findById(int id) throws SQLException {
        String sql = "SELECT id, actual_return_date, penalty_points, overdue_days, provide_post_id, requester_id "
                + "FROM Rental_provide_confirm " + "WHERE id = ?";

        jdbcUtil.setSqlAndParameters(sql, new Object[] { id });

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
        String requesterSql = "SELECT nickname, dormitory_name, room_number " + "FROM Member " + "WHERE id = ?";
        jdbcUtil.setSqlAndParameters(requesterSql, new Object[] { requesterId });

        System.out.println("Executing SQL: " + requesterSql + " with ID: " + requesterId);

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
                throw new SQLException("Requester not found with ID: " + requesterId);
            }
        } finally {
            jdbcUtil.close();
        }

        // 제공자 정보 가져오기
        String providerSql = "SELECT m.nickname, m.dormitory_name, m.room_number " + "FROM Rental_provide_post rp "
                + "JOIN Member m ON rp.provider_id = m.id " + "WHERE rp.id = ?";
        jdbcUtil.setSqlAndParameters(providerSql, new Object[] { providePostId });

        System.out.println("Executing SQL: " + providerSql + " with Provide Post ID: " + providePostId);

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
                throw new SQLException("Provider not found for providePostId: " + providePostId);
            }
        } finally {
            jdbcUtil.close();
        }

        return result;
    }

    /**
     * 대여 정보를 상세 조회하여 반환합니다.
     * 
     * @param provideConfirmId 제공 대여 ID
     * @return 대여 정보를 포함한 Map 객체
     * @throws SQLException 데이터베이스 오류
     */
    public Map<String, Object> getRentalDecisionDetails(int provideConfirmId) throws SQLException {
        String sql = "SELECT " + "rp.rental_item AS itemnickname, "
                + "m1.nickname AS requester_nickname, m1.dormitory_name AS requester_dormitory, m1.room_number AS requester_room, "
                + "m2.nickname AS provider_nickname, m2.dormitory_name AS provider_dormitory, m2.room_number AS provider_room, "
                + "rp.points AS store, rp.rental_location AS rental_place, rp.return_location AS return_place, "
                + "rp.rental_start_date AS rental_date, rp.rental_end_date AS return_date "
                + "FROM Rental_provide_post rp " + "JOIN Rental_provide_confirm rpc ON rp.id = rpc.provide_post_id "
                + "JOIN Member m1 ON rpc.requester_id = m1.id " + "JOIN Member m2 ON rp.provider_id = m2.id "
                + "WHERE rpc.id = ?"; // ProvideConfirm의 id를 기준으로 변경

        jdbcUtil.setSqlAndParameters(sql, new Object[] { provideConfirmId });

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

    public RentalProvidePost getRentalProvidePostById(int provideConfirmId) throws SQLException {
        // SQL 작성: provide_post_id를 기반으로 RentalProvidePost를 조회
        String sql = "SELECT * FROM RentalProvidePost WHERE id = "
                + "(SELECT provide_post_id FROM Rental_provide_confirm WHERE id = ?)";

        jdbcUtil.setSqlAndParameters(sql, new Object[] { provideConfirmId });

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                // RentalProvidePost 객체 생성 및 데이터 설정
                RentalProvidePost rentalProvidePost = new RentalProvidePost();
                rentalProvidePost.setId(rs.getInt("id"));
                rentalProvidePost.setPoints(rs.getInt("points"));
                rentalProvidePost.setRentalLocation(rs.getString("rental_location"));
                rentalProvidePost.setReturnLocation(rs.getString("return_location"));
                rentalProvidePost.setRentalStartDate(rs.getDate("rental_start_date"));
                rentalProvidePost.setRentalEndDate(rs.getDate("rental_end_date"));
                return rentalProvidePost;
            } else {
                throw new SQLException("RentalProvidePost ID " + provideConfirmId + "에 해당하는 데이터가 존재하지 않습니다.");
            }
        } finally {
            jdbcUtil.close();
        }
    }

    public void updateRentalDecisionDetails(int provideConfirmId, String store, String rentalPlace, String returnPlace,
            Date rentalDate, Date returnDate) throws SQLException {
        // Rental_provide_confirm 테이블에서 provide_post_id를 가져오는 쿼리
        String fetchProvidePostIdSql = "SELECT provide_post_id FROM Rental_provide_confirm WHERE id = ?";

        jdbcUtil.setSqlAndParameters(fetchProvidePostIdSql, new Object[] { provideConfirmId });

        int providePostId;

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                providePostId = rs.getInt("provide_post_id");
            } else {
                throw new SQLException("해당 provideConfirmId에 대한 레코드가 존재하지 않습니다: " + provideConfirmId);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SQLException("provide_post_id 조회 중 오류 발생", ex);
        } finally {
            jdbcUtil.close();
        }

        // Rental_provide_post 테이블의 데이터를 업데이트하는 쿼리
        String updateSql = "UPDATE Rental_provide_post "
                + "SET points = ?, rental_location = ?, return_location = ?, rental_start_date = ?, rental_end_date = ? "
                + "WHERE id = ?";

        Object[] updateParams = new Object[] { Integer.parseInt(store), // store 값을 정수로 변환하여 전달
                rentalPlace, returnPlace, rentalDate, returnDate, providePostId };

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

    public RentalProvidePost findConfirmById(int id) throws SQLException {
        String sql = "SELECT id, title, rental_item, content, points, rental_start_date, rental_end_date, rental_location, return_location, status, provider_id, image_url "
                + "FROM Rental_provide_post " + "WHERE id = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] { id });

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                RentalProvidePost rentalProvidePost = new RentalProvidePost();
                rentalProvidePost.setId(rs.getInt("id"));
                rentalProvidePost.setTitle(rs.getString("title"));
                rentalProvidePost.setRentalItem(rs.getString("rental_item"));
                rentalProvidePost.setContent(rs.getString("content"));
                rentalProvidePost.setPoints(rs.getInt("points"));
                rentalProvidePost.setRentalStartDate(rs.getDate("rental_start_date"));
                rentalProvidePost.setRentalEndDate(rs.getDate("rental_end_date"));
                rentalProvidePost.setRentalLocation(rs.getString("rental_location"));
                rentalProvidePost.setReturnLocation(rs.getString("return_location"));
                rentalProvidePost.setStatus(rs.getInt("status"));
                rentalProvidePost.setProviderId(rs.getInt("provider_id"));
                rentalProvidePost.setImageUrl(rs.getString("image_url"));
                return rentalProvidePost;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SQLException("Error finding RentalProvidePost by ID", ex);
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

    /**
     * ProvideConfirm의 actual_return_date 조회
     */
    public Date getActualReturnDate(int provideConfirmId) throws SQLException {
        String sql = "SELECT actual_return_date FROM Rental_provide_confirm WHERE id = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] { provideConfirmId }); // SQL과 매개변수 설정

        try {
            ResultSet rs = jdbcUtil.executeQuery(); // 쿼리 실행
            if (rs.next()) {
                return rs.getDate("actual_return_date"); // actual_return_date 반환
            }
        } catch (Exception ex) {
            throw new SQLException("ProvideConfirm ID로 actual_return_date를 가져오는 중 오류 발생: " + provideConfirmId, ex);
        } finally {
            jdbcUtil.close(); // 리소스 정리
        }
        return null; // 데이터가 없을 경우 null 반환
    }

    /**
     * 연체 일수(overdue_days) 및 패널티 점수(penalty_points) 계산 및 업데이트
     */
    public void updateOverdueDays(int provideConfirmId) throws SQLException {
        String sqlOverdueDays = "UPDATE Rental_provide_confirm rpc "
                + "SET overdue_days = (SELECT CASE WHEN actual_return_date > rp.rental_end_date THEN "
                + "CAST(actual_return_date - rp.rental_end_date AS INTEGER) ELSE 0 END "
                + "FROM Rental_provide_post rp WHERE rp.id = rpc.provide_post_id) " + "WHERE rpc.id = ?";

        String sqlPenaltyPoints = "UPDATE Rental_provide_confirm " + "SET penalty_points = overdue_days * 50 "
                + "WHERE id = ?";

        try {
            // 연체 일수 업데이트
            jdbcUtil.setSqlAndParameters(sqlOverdueDays, new Object[] { provideConfirmId });
            jdbcUtil.executeUpdate();

            // 패널티 점수 업데이트
            jdbcUtil.setSqlAndParameters(sqlPenaltyPoints, new Object[] { provideConfirmId });
            jdbcUtil.executeUpdate();

            jdbcUtil.commit();
        } catch (Exception ex) {
            jdbcUtil.rollback();
            throw new SQLException("Error updating overdue days and penalty points", ex);
        } finally {
            jdbcUtil.close();
        }
    }

    /**
     * 특정 provideConfirmId를 기반으로 Rental_provide_post 테이블의 status를 1로 업데이트
     */
    public void updatePostStatus(int provideConfirmId) throws SQLException {
        String sqlUpdateStatus = "UPDATE Rental_provide_post rp " + "SET rp.status = 1 "
                + "WHERE rp.id = (SELECT rpc.provide_post_id FROM Rental_provide_confirm rpc WHERE rpc.id = ?)";

        try {
            // SQL 및 파라미터 설정
            jdbcUtil.setSqlAndParameters(sqlUpdateStatus, new Object[] { provideConfirmId });

            // 업데이트 실행
            jdbcUtil.executeUpdate();

            // 커밋
            jdbcUtil.commit();
        } catch (Exception ex) {
            // 롤백 처리
            jdbcUtil.rollback();
            throw new SQLException("Error updating post status", ex);
        } finally {
            // 자원 정리
            jdbcUtil.close();
        }
    }

    /**
     * overdueDays, penalty_points, points 조회
     */
    public Map<String, Integer> getOverdueAndPoints(int provideConfirmId) throws SQLException {
        String sql = "SELECT rpc.overdue_days, rpc.penalty_points, rp.points " + "FROM Rental_provide_confirm rpc "
                + "JOIN Rental_provide_post rp ON rpc.provide_post_id = rp.id " + "WHERE rpc.id = ?";

        jdbcUtil.setSqlAndParameters(sql, new Object[] { provideConfirmId });

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
            throw new SQLException("Error fetching overdue days, penalty points, and points for ProvideConfirm ID: "
                    + provideConfirmId, ex);
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

    /**
     * Point 업데이트 (penalty_points 차감 및 points 추가)
     */
    public void updateMemberPoints(int provideConfirmId) throws SQLException {
        // 요청자 ID, 제공자 ID, penalty_points, points 가져오기
        String fetchSql = "SELECT rpc.requester_id, rp.provider_id, rpc.penalty_points, rp.points "
                + "FROM Rental_provide_confirm rpc " + "JOIN Rental_provide_post rp ON rpc.provide_post_id = rp.id "
                + "WHERE rpc.id = ?";

        jdbcUtil.setSqlAndParameters(fetchSql, new Object[] { provideConfirmId });

        int requesterId, providerId, penaltyPoints, points;

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                requesterId = rs.getInt("requester_id");
                providerId = rs.getInt("provider_id");
                penaltyPoints = rs.getInt("penalty_points");
                points = rs.getInt("points");
            } else {
                throw new SQLException("ProvideConfirm not found with ID: " + provideConfirmId);
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