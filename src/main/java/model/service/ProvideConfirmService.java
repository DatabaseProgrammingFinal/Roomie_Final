package model.service;

import java.sql.SQLException;
import model.dao.ProvideConfirmDAO;
import model.domain.ProvideConfirm;

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
    public ProvideConfirm createProvideConfirm(ProvideConfirm pc) throws SQLException {
        try {
            return provideConfirmDAO.create(pc);
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
}

