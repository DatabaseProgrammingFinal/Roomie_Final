package model.service;

import java.sql.SQLException;

import model.dao.RequestConfirmDAO;
import model.domain.RequestConfirm;

/**
 * RequestConfirm 비즈니스 로직을 담당하는 서비스 클래스
 */
public class RequestConfirmService {
    private RequestConfirmDAO requestConfirmDAO;

    public RequestConfirmService() {
        requestConfirmDAO = new RequestConfirmDAO();
    }

    /**
     * RequestConfirm 생성
     * @param requestConfirm 생성할 RequestConfirm 객체
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
     * 특정 ID로 RequestConfirm 조회
     * @param id 조회할 RequestConfirm ID
     * @return RequestConfirm 객체
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
}
