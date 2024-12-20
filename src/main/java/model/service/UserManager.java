package model.service;

import java.sql.SQLException;
import java.util.List;

import model.dao.CommunityDAO;
import model.dao.UserDAO;
import model.domain.Community;
import model.domain.User;

/**
 * 사용자 관리 API를 사용하는 개발자들이 직접 접근하게 되는 클래스.
 * UserDAO를 이용하여 데이터베이스에 데이터 조작 작업이 가능하도록 하며,
 * 데이터베이스의 데이터들을 이용하여 비지니스 로직을 수행하는 역할을 한다.
 * 비지니스 로직이 복잡한 경우에는 비지니스 로직만을 전담하는 클래스를 
 * 별도로 둘 수 있다.
 */
public class UserManager {
	private static UserManager UserMan = new UserManager();
	private UserDAO UserDAO;
	private CommunityDAO commDAO;
	private UserAnalysis UserAnalysis;

	private UserManager() {
		try {
			UserDAO = new UserDAO();
			commDAO = new CommunityDAO();
			UserAnalysis = new UserAnalysis(UserDAO);
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
	
	public static UserManager getInstance() {
		return UserMan;
	}
	
	public int create(User User) throws SQLException, ExistingUserException {
		if (UserDAO.existingUser(User.getLoginId()) == true) {
			throw new ExistingUserException(User.getLoginId() + "는 존재하는 아이디입니다.");
		}
		return UserDAO.create(User);
	}

	public int update(User User) throws SQLException, UserNotFoundException {
		 try {
	            // 사용자 정보 업데이트
	            return UserDAO.update(User);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return 0;  // SQLException 발생 시 실패
	        }
	}	

	public int remove(String login_id) throws SQLException, UserNotFoundException {
		try {
            // 먼저, 사용자가 존재하는지 확인
            if (UserDAO.existingUser(login_id)) {
                // 사용자 존재하면 삭제
                return UserDAO.remove(login_id);
            } else {
                System.out.println("사용자가 존재하지 않습니다.");
                return 0;  // 사용자 없음
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;  // SQLException 발생 시 실패
        }
	}

	public User findUser(String login_id)
		throws SQLException, UserNotFoundException {
		User User = UserDAO.findUser(login_id);
		
		if (User == null) {
			throw new UserNotFoundException(login_id + "는 존재하지 않는 아이디입니다.");
		}		
		return User;
	}

	public List<User> findUserList() throws SQLException {
			return UserDAO.findUserList();
	}
	
	public List<User> findUserList(int currentPage, int countPerPage)
		throws SQLException {
		return UserDAO.findUserList();
	}

	public int login(String login_id, String password)
		throws SQLException, UserNotFoundException, PasswordMismatchException {
		User User = findUser(login_id);

		if (!User.matchPassword(password)) {
			throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
		}
		return User.getId();
	}

	public List<User> makeFriends(String login_id) throws Exception {
		return UserAnalysis.recommendFriends(login_id);
	}
	public boolean isIdTaken(String loginId) throws SQLException {
	    return UserDAO.existingUser(loginId); // 기존 메서드 호출
	}
	public boolean isNicknameTaken(String nickname) throws SQLException {
	    return UserDAO.existingNickname(nickname); // UserDAO에서 닉네임 중복 여부 확인
	}
	public Community createCommunity(Community comm) throws SQLException {
		return commDAO.create(comm);		
	}

	public int updateCommunity(Community comm) throws SQLException {
		return commDAO.update(comm);				
	}
	
	public Community findCommunity(int commId) throws SQLException {
		Community comm = commDAO.findCommunity(commId); 
		
		List<User> memberList = UserDAO.findUsersInCommunity(commId);
		comm.setMemberList(memberList);
		
		int numOfMembers = UserDAO.getNumberOfUsersInCommunity(commId);
		comm.setNumOfMembers(numOfMembers);
		return comm;
	}
	
	public List<Community> findCommunityList() throws SQLException {
		return commDAO.findCommunityList();
	}
	
	public List<User> findCommunityMembers(int commId) throws SQLException {
		return UserDAO.findUsersInCommunity(commId);
	}

	public UserDAO getUserDAO() {
		return this.UserDAO;
	}
	
}
