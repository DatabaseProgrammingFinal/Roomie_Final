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
        // 먼저 중복된 login_id가 존재하는지 확인
        if (existingUser(user.getLoginId())) {
            // 중복된 login_id가 존재하면 삽입하지 않고 0을 반환
            return 0;
        }

        String sql = "INSERT INTO Member (id, login_id, password, nickname, dormitory_name, room_number, profile_url, points) " +
                     "VALUES (member_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
        Object[] param = new Object[] {
            user.getLoginId(), user.getPassword(), user.getNickname(),
            user.getDormitoryName(), user.getRoomNumber(), user.getProfileUrl(), user.getPoints()
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
                     "SET password=?, nickname=?, dormitory_name=?, room_number=?, profile_url=?, points=? " +
                     "WHERE login_id=?";
        Object[] param = new Object[] {
            user.getPassword(), user.getNickname(), user.getDormitoryName(),
            user.getRoomNumber(), user.getProfileUrl(), user.getPoints(), user.getLoginId()
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
        String sql = "SELECT id, login_id, password, nickname, dormitory_name, room_number, profile_url, points " +
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
                    rs.getInt("points")
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
        String sql = "SELECT id, login_id, nickname, dormitory_name, room_number, profile_url, points " +
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
                    rs.getInt("points")
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
    
    public List<User> findUsersInCommunity(int communityId) throws SQLException {
        String sql = "SELECT m.id, m.login_id, m.password, m.nickname, m.dormitory_name, " +
                     "m.room_number, m.profile_url, m.points " +
                     "FROM Member m " +
                     "JOIN community_member cm ON m.login_id = cm.login_id " +
                     "WHERE cm.community_id = ?";  // community_member 테이블에서 사용자를 찾음
        
        jdbcUtil.setSqlAndParameters(sql, new Object[] {communityId}); // JDBCUtil에 쿼리와 매개 변수 설정

        try {
            ResultSet rs = jdbcUtil.executeQuery(); // 쿼리 실행
            List<User> userList = new ArrayList<User>(); // User 객체 리스트 생성
            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("login_id"),
                    rs.getString("password"),  // 비밀번호 포함
                    rs.getString("nickname"),
                    rs.getString("dormitory_name"),
                    rs.getString("room_number"),
                    rs.getString("profile_url"),
                    rs.getInt("points")
                );
                userList.add(user);  // 리스트에 사용자 추가
            }
            return userList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;  // 예외 발생 시 null 반환
        } finally {
            jdbcUtil.close(); // 리소스 반환
        }
    }
    
    /**
     * 특정 커뮤니티에 속한 사용자 수 반환
     */
    public int getNumberOfUsersInCommunity(int commId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM community_member WHERE community_id = ?";  // count the number of users in the community
        
        jdbcUtil.setSqlAndParameters(sql, new Object[] {commId}); 
        try {
            ResultSet rs = jdbcUtil.executeQuery(); 
            if (rs.next()) {
                return rs.getInt(1); 
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Error retrieving the number of users in community", ex);
        } finally {
            jdbcUtil.close(); 
        }
        return 0; 
    }



    // 이거 방금 추가.
    public User findUserById(int userId) throws SQLException {
        String sql = "SELECT id, login_id, password, nickname, dormitory_name, room_number, profile_url, points " +
                     "FROM Member WHERE id = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[]{userId}); // SQL 및 매개변수 설정

        try {
            ResultSet rs = jdbcUtil.executeQuery(); // 쿼리 실행
            if (rs.next()) {
                // User 객체 생성 및 반환
                return new User(
                    rs.getInt("id"),
                    rs.getString("login_id"),
                    rs.getString("password"),
                    rs.getString("nickname"),
                    rs.getString("dormitory_name"),
                    rs.getString("room_number"),
                    rs.getString("profile_url"),
                    rs.getInt("points")
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close(); // 리소스 반환
        }
        return null; // 사용자 없음
    }

}
