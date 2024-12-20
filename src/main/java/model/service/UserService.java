package model.service;

import java.sql.SQLException;
import java.util.List;

import model.dao.UserDAO;
import model.domain.User;

/**
 * 사용자 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
public class UserService {
    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO(); // DAO 객체 초기화
    }

    /**
     * 새로운 사용자 생성
     */
    public int createUser(User user) {
        try {
            return userDAO.create(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0; // 실패 시 0 반환
        }
    }

    /**
     * 사용자 정보 업데이트
     */
    public int updateUser(User user) {
        try {
            return userDAO.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0; // 실패 시 0 반환
        }
    }

    /**
     * 사용자 삭제
     */
    public boolean deleteUser(String loginId) {
        try {
            return userDAO.remove(loginId) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // 실패 시 false 반환
        }
    }

    /**
     * 특정 사용자 검색 (loginId로)
     */
    public User findUserByLoginId(String loginId) {
        try {
            return userDAO.findUser(loginId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // 실패 시 null 반환
        }
    }

    /**
     * 특정 사용자 검색 (userId로)
     */
    public User findUserById(int userId) {
        try {
            return userDAO.findUserById(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // 실패 시 null 반환
        }
    }

    /**
     * 전체 사용자 리스트 검색
     */
    public List<User> getAllUsers() {
        try {
            return userDAO.findUserList();
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // 실패 시 null 반환
        }
    }

    /**
     * 특정 loginId가 존재하는지 확인
     */
    public boolean isExistingUser(String loginId) {
        try {
            return userDAO.existingUser(loginId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // 실패 시 false 반환
        }
    }
}
