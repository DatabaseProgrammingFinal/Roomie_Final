package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.domain.User;

/**
 * 사용자 관리를 위해 데이터베이스 작업을 전담하는 DAO 클래스
 * Member 테이블에 사용자 정보를 추가, 수정, 삭제, 검색 수행 
 */
public class UserDAO {
    private JDBCUtil jdbcUtil = null;

    public UserDAO() {			
        jdbcUtil = new JDBCUtil();	// JDBCUtil 객체 생성
    }

    /**
     * Member 테이블에 새로운 사용자 생성
     */
    public int create(User user) throws SQLException {
        String sql = "INSERT INTO Member (id, login_id, password, nickname, dormitory_name, room_number, profile_url, points, commId, commnickname) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] param = new Object[] {
            user.getId(),
            user.getLogin_id(),
            user.getPassword(),
            user.getNickname(),
            user.getDormitory_name(),
            user.getRoom_number(),
            user.getProfile_url(),
            user.getPoints(),
            (user.getCommId() != 0) ? user.getCommId() : null,
            user.getCommnickname()
        };
        jdbcUtil.setSqlAndParameters(sql, param); // JDBCUtil에 insert문과 매개 변수 설정

        try {				
            int result = jdbcUtil.executeUpdate();	// insert 문 실행
            return result;
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {		
            jdbcUtil.commit();
            jdbcUtil.close();	// resource 반환
        }		
        return 0;			
    }

    /**
     * 기존의 사용자 정보를 수정
     */
    public int update(User user) throws SQLException {
        String sql = "UPDATE Member " +
                     "SET password=?, nickname=?, dormitory_name=?, room_number=?, profile_url=?, points=?, commId=?, commnickname=? " +
                     "WHERE login_id=?";
        Object[] param = new Object[] {
            user.getPassword(),
            user.getNickname(),
            user.getDormitory_name(),
            user.getRoom_number(),
            user.getProfile_url(),
            user.getPoints(),
            (user.getCommId() != 0) ? user.getCommId() : null,
            user.getCommnickname(),
            user.getLogin_id()
        };
        jdbcUtil.setSqlAndParameters(sql, param); // JDBCUtil에 update문과 매개 변수 설정

        try {				
            int result = jdbcUtil.executeUpdate();	// update 문 실행
            return result;
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close();	// resource 반환
        }		
        return 0;
    }

    /**
     * 사용자 ID에 해당하는 사용자를 삭제
     */
    public int remove(String login_id) throws SQLException {
        String sql = "DELETE FROM Member WHERE login_id=?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] {login_id}); // JDBCUtil에 delete문과 매개 변수 설정

        try {				
            int result = jdbcUtil.executeUpdate();	// delete 문 실행
            return result;
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close();	// resource 반환
        }		
        return 0;
    }

    /**
     * 주어진 사용자 ID에 해당하는 사용자 정보를 데이터베이스에서 찾아 User 도메인 클래스에 저장하여 반환
     */
    public User findUser(String login_id) throws SQLException {
        String sql = "SELECT id, login_id, password, nickname, dormitory_name, room_number, profile_url, points, commId, commnickname " +
                     "FROM Member WHERE login_id=?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] {login_id}); // JDBCUtil에 query문과 매개 변수 설정

        try {
            ResultSet rs = jdbcUtil.executeQuery(); // query 실행
            if (rs.next()) { // 사용자 정보 발견
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("login_id"),
                    rs.getString("password"),
                    rs.getString("nickname"),
                    rs.getString("dormitory_name"),
                    rs.getString("room_number"),
                    rs.getString("profile_url"),
                    rs.getInt("points"),
                    rs.getInt("commId"),
                    rs.getString("commnickname")
                );
                return user;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close(); // resource 반환
        }
        return null;
    }

    /**
     * 전체 사용자 정보를 검색하여 List에 저장 및 반환
     */
    public List<User> findUserList() throws SQLException {
        String sql = "SELECT id, login_id, nickname, dormitory_name, room_number, profile_url, points, commId, commnickname " +
                     "FROM Member ORDER BY login_id";
        jdbcUtil.setSqlAndParameters(sql, null); // JDBCUtil에 query문 설정

        try {
            ResultSet rs = jdbcUtil.executeQuery(); // query 실행			
            List<User> userList = new ArrayList<User>(); // User 리스트 생성
            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("login_id"),
                    null, // 비밀번호는 반환하지 않음
                    rs.getString("nickname"),
                    rs.getString("dormitory_name"),
                    rs.getString("room_number"),
                    rs.getString("profile_url"),
                    rs.getInt("points"),
                    rs.getInt("commId"),
                    rs.getString("commnickname")
                );
                userList.add(user); // List에 User 객체 저장
            }
            return userList;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close(); // resource 반환
        }
        return null;
    }

    /**
     * 특정 커뮤니티에 속한 사용자들을 검색하여 List에 저장 및 반환
     */
    public List<User> findUsersInCommunity(int communityId) throws SQLException {
        String sql = "SELECT id, login_id, nickname, dormitory_name, room_number, profile_url, points FROM Member " +
                     "WHERE commId=?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] {communityId}); // JDBCUtil에 query문과 매개 변수 설정

        try {
            ResultSet rs = jdbcUtil.executeQuery(); // query 실행
            List<User> memList = new ArrayList<User>(); // member들의 리스트 생성
            while (rs.next()) {
                User member = new User(
                    rs.getInt("id"),
                    rs.getString("login_id"),
                    null,
                    rs.getString("nickname"),
                    rs.getString("dormitory_name"),
                    rs.getString("room_number"),
                    rs.getString("profile_url"),
                    rs.getInt("points"),
                    communityId,
                    null
                );
                memList.add(member); // List에 Community 객체 저장
            }		
            return memList;					
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close(); // resource 반환
        }
        return null;
    }

    /**
     * 특정 커뮤니티에 속한 사용자들의 수를 count하여 반환
     */
    public int getNumberOfUsersInCommunity(int communityId) {
        String sql = "SELECT COUNT(login_id) FROM Member WHERE commId=?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] {communityId}); // JDBCUtil에 query문과 매개 변수 설정

        try {
            ResultSet rs = jdbcUtil.executeQuery(); // query 실행
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close(); // resource 반환
        }
        return 0;
    }

    /**
     * 주어진 사용자 login_id에 해당하는 사용자가 존재하는지 검사 
     */
    public boolean existingUser(String login_id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Member WHERE login_id=?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] {login_id}); // JDBCUtil에 query문과 매개 변수 설정

        try {
            ResultSet rs = jdbcUtil.executeQuery(); // query 실행
            if (rs.next()) {
                int count = rs.getInt(1);
                return (count == 1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close(); // resource 반환
        }
        return false;
    }
}

