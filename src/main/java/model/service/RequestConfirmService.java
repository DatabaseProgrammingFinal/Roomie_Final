package model.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;

import model.dao.RequestConfirmDAO;
import model.domain.RentalRequestPost;
import model.domain.RequestConfirm;

/**
 * RequestConfirm 관련 비즈니스 로직을 처리하는 Service 클래스.
 */
public class RequestConfirmService {
    private RequestConfirmDAO requestConfirmDAO;

    public RequestConfirmService() {
        requestConfirmDAO = new RequestConfirmDAO();
    }

    /**
     * 새로운 RequestConfirm 데이터를 생성합니다.
     * 
     * @param rc RequestConfirm 객체
     * @return 생성된 RequestConfirm 객체
     * @throws SQLException 데이터베이스 오류
     */
    public RequestConfirm createRequestConfirm(RequestConfirm requestConfirm) throws SQLException {
        try {
            return requestConfirmDAO.create(requestConfirm);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * ID로 RequestConfirm 데이터를 검색합니다.
     * 
     * @param id RequestConfirm의 ID
     * @return 검색된 RequestConfirm 객체
     * @throws SQLException 데이터베이스 오류
     */
    public RequestConfirm getRequestConfirmById(int id) throws SQLException {
        try {
            RequestConfirm rc = requestConfirmDAO.findById(id);
            if (rc == null) {
                throw new SQLException("RequestConfirm ID " + id + "에 해당하는 데이터가 존재하지 않습니다.");
            }
            return rc;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * RequestConfirm 데이터를 수정합니다.
     * 
     * @param rc 수정할 RequestConfirm 객체
     * @throws SQLException 데이터베이스 오류
     */
    public void updateRequestConfirm(RequestConfirm rc) throws SQLException {
        try {
            requestConfirmDAO.update(rc);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("RequestConfirm 업데이트 중 오류 발생", e);
        }
    }

    /**
     * RequestConfirm 데이터를 삭제합니다.
     * 
     * @param id 삭제할 RequestConfirm의 ID
     * @throws SQLException 데이터베이스 오류
     */
    public void deleteRequestConfirm(int id) throws SQLException {
        try {
            requestConfirmDAO.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("RequestConfirm 삭제 중 오류 발생", e);
        }
    }

    /**
     * 요청자와 제공자 정보를 가져옵니다.
     * 
     * @param providerId    제공자의 ID
     * @param requestPostId 요청 글 ID
     * @return 요청자와 제공자 정보를 포함한 Map 객체
     * @throws SQLException 데이터베이스 오류
     */
    public Map<String, Object> getRequesterAndProviderInfo(int providerId, int requestPostId) throws SQLException {
        try {
            Map<String, Object> info = requestConfirmDAO.getRequesterAndProviderInfo(providerId, requestPostId);
            if (info == null) {
                throw new SQLException("Invalid providerId or requestPostId.");
            }
            return info;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 요청 글 ID로 대여 정보를 조회합니다.
     * 
     * @param requestConfirmId RequestConfirm의 ID
     * @return 대여 정보를 포함한 Map 객체
     * @throws SQLException 데이터베이스 오류
     */
    public Map<String, Object> getRentalDecisionDetails(int requestConfirmId) throws SQLException {
        try {
            Map<String, Object> rentalDetails = requestConfirmDAO.getRentalDecisionDetails(requestConfirmId);
            if (rentalDetails == null) {
                throw new SQLException("RequestConfirm ID " + requestConfirmId + "에 대한 대여 정보를 찾을 수 없습니다.");
            }
            return rentalDetails;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void updateRentalDecisionDetails(int requestConfirmId, String store, String rentalPlace, String returnPlace,
            java.sql.Date rentalDate, java.sql.Date returnDate) throws SQLException {
        try {
            requestConfirmDAO.updateRentalDecisionDetails(requestConfirmId, store, rentalPlace, returnPlace, rentalDate,
                    returnDate);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("RentalRequestPost 업데이트 중 오류 발생", e);
        }
    }

    /**
     * RentalRequestPost ID로 데이터를 검색합니다.
     * 
     * @param requestConfirmId RequestConfirm의 ID
     * @return 검색된 RentalRequestPost 객체
     * @throws SQLException 데이터베이스 오류
     */
    public RentalRequestPost getRentalRequestPostById(int requestConfirmId) throws SQLException {
        return requestConfirmDAO.getRentalRequestPostById(requestConfirmId);
    }

    // 반납 날짜 업데이트
    public void confirmReturn(int requestConfirmId) throws SQLException {
        Date currentDate = new Date(System.currentTimeMillis()); // 현재 날짜
        requestConfirmDAO.updateActualReturnDate(requestConfirmId, currentDate);
    }

    public void updateActualReturnDate(int requestConfirmId, Date actualReturnDate) throws Exception {
        requestConfirmDAO.updateActualReturnDate(requestConfirmId, actualReturnDate);
    }

    /**
     * RequestConfirm의 actual_return_date 조회
     */
    public Date getActualReturnDate(int requestConfirmId) throws Exception {
        return requestConfirmDAO.getActualReturnDate(requestConfirmId); // DAO 호출
    }

    public void updateOverdueDays(int requestConfirmId) throws Exception {
        requestConfirmDAO.updateOverdueDays(requestConfirmId);
    }

    /**
     * Confirm ID로 overdue_days, penalty_points, points 조회
     */
    public Map<String, Integer> getOverdueAndPoints(int requestConfirmId) throws Exception {
        return requestConfirmDAO.getOverdueAndPoints(requestConfirmId);
    }

    /**
     * Point 업데이트 (penalty_points 차감 및 points 추가)
     */
    public void updateMemberPoints(int requestConfirmId) throws Exception {
        try {
            requestConfirmDAO.updateMemberPoints(requestConfirmId);
        } catch (SQLException ex) {
            throw new Exception("Error updating member points", ex);
        }
    }

    /**
     * Confirm ID를 기반으로 RentalrequestPost의 상태를 업데이트합니다.
     * 
     * @param requestConfirmId requestConfirm의 ID
     * @throws SQLException 데이터베이스 오류
     */
    public void updatePostStatus(int requestConfirmId) throws SQLException {
        try {
            requestConfirmDAO.updatePostStatus(requestConfirmId);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Error updating post status", ex);
        }
    }
}
