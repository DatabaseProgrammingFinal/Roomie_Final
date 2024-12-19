package model.service;

import java.sql.SQLException;
import java.util.Map;

import model.dao.ProvideConfirmDAO;
import model.domain.ProvideConfirm;
import model.domain.RentalProvidePost;


/**
 * ProvideConfirm 관련 비즈니스 로직을 처리하는 Service 클래스.
 */
public class ProvideConfirmService {
    private ProvideConfirmDAO provideConfirmDAO;

    public ProvideConfirmService() {
        provideConfirmDAO = new ProvideConfirmDAO();
    }

    /**
     * 새로운 ProvideConfirm 데이터를 생성합니다.
     * @param pc ProvideConfirm 객체
     * @return 생성된 ProvideConfirm 객체
     * @throws SQLException 데이터베이스 오류
     */
    public ProvideConfirm createProvideConfirm(ProvideConfirm provideConfirm) throws SQLException {
        try {
            return provideConfirmDAO.create(provideConfirm);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    /**
     * ID로 ProvideConfirm 데이터를 검색합니다.
     * @param id ProvideConfirm의 ID
     * @return 검색된 ProvideConfirm 객체
     * @throws SQLException 데이터베이스 오류
     */
    public ProvideConfirm getProvideConfirmById(int id) throws SQLException {
        try {
            ProvideConfirm pc = provideConfirmDAO.findById(id);
            if (pc == null) {
                throw new SQLException("ProvideConfirm ID " + id + "에 해당하는 데이터가 존재하지 않습니다.");
            }
            return pc;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * ProvideConfirm 데이터를 수정합니다.
     * @param pc 수정할 ProvideConfirm 객체
     * @throws SQLException 데이터베이스 오류
     */
    public void updateProvideConfirm(ProvideConfirm pc) throws SQLException {
        try {
            provideConfirmDAO.update(pc);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("ProvideConfirm 업데이트 중 오류 발생", e);
        }
    }

    /**
     * ProvideConfirm 데이터를 삭제합니다.
     * @param id 삭제할 ProvideConfirm의 ID
     * @throws SQLException 데이터베이스 오류
     */
    public void deleteProvideConfirm(int id) throws SQLException {
        try {
            provideConfirmDAO.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("ProvideConfirm 삭제 중 오류 발생", e);
        }
    }

    /**
     * 요청자와 제공자 정보를 가져옵니다.
     * @param confirmId ProvideConfirm의 ID
     * @return 요청자와 제공자 정보를 포함한 Map 객체
     * @throws SQLException 데이터베이스 오류
     */
    public Map<String, Object> getRequesterAndProviderInfo(int requesterId, int providePostId) throws SQLException {
        try {
            Map<String, Object> info = provideConfirmDAO.getRequesterAndProviderInfo(requesterId, providePostId);
            if (info == null) {
                throw new SQLException("Invalid requesterId or providePostId.");
            }
            return info;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 제공 글 ID로 대여 정보를 조회합니다.
     * @param provideConfirmId ProvideConfirm의 ID
     * @return 대여 정보를 포함한 Map 객체
     * @throws SQLException 데이터베이스 오류
     */
    public Map<String, Object> getRentalDecisionDetails(int provideConfirmId) throws SQLException {
        try {
            Map<String, Object> rentalDetails = provideConfirmDAO.getRentalDecisionDetails(provideConfirmId);
            if (rentalDetails == null) {
                throw new SQLException("ProvideConfirm ID " + provideConfirmId + "에 대한 대여 정보를 찾을 수 없습니다.");
            }
            return rentalDetails;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 대여 제공 글 데이터를 생성합니다.
     * @param rentalProvidePost RentalProvidePost 객체
     * @return 생성된 RentalProvidePost 객체
     * @throws SQLException 데이터베이스 오류
//     */
//    public RentalProvidePost createRentalProvidePost(RentalProvidePost rentalProvidePost) throws SQLException {
//        try {
//            return provideConfirmDAO.createRentalProvidePost(rentalProvidePost);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new SQLException("RentalProvidePost 생성 중 오류 발생", e);
//        }
//    }

    /**
     * RentalProvidePost 데이터를 수정합니다.
     * @param rentalProvidePost 수정할 RentalProvidePost 객체
     * @throws SQLException 데이터베이스 오류
     */
    public void updateRentalDecisionDetails(RentalProvidePost rentalProvidePost) throws SQLException {
        try {
            provideConfirmDAO.updateRentalDecisionDetails(rentalProvidePost);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("RentalProvidePost 업데이트 중 오류 발생", e);
        }
    }

    /**
     * RentalProvidePost ID로 데이터를 검색합니다.
     * @param id RentalProvidePost의 ID
     * @return 검색된 RentalProvidePost 객체
     * @throws SQLException 데이터베이스 오류
     */
    public RentalProvidePost getRentalProvidePostById(int id) throws SQLException {
        try {
            RentalProvidePost rentalProvidePost = provideConfirmDAO.findConfirmById(id);
            if (rentalProvidePost == null) {
                throw new SQLException("RentalProvidePost ID " + id + "에 해당하는 데이터가 존재하지 않습니다.");
            }
            return rentalProvidePost;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

   
}
