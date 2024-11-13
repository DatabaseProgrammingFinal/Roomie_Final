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
	private UserAnalysis UserAanlysis;

	private UserManager() {
		try {
			UserDAO = new UserDAO();
			commDAO = new CommunityDAO();
			UserAanlysis = new UserAnalysis(UserDAO);
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
		int oldCommId = findUser(User.getLoginId()).getCommId();
		if (User.getCommId() != oldCommId) { 	// 소속 커뮤티니가 변경됨
			Community comm = commDAO.findCommunity(oldCommId);  // 기존 소속 커뮤니티
			if (comm != null && User.getLoginId().equals(comm.getChairId())) {
				// 사용자가 기존 소속 커뮤니티의 회장인 경우 -> 그 커뮤니티의 회장을 null로 변경 및 저장
				comm.setChairId(null);
				commDAO.updateChair(comm);
			}
		}
		return UserDAO.update(User);
	}	

	public int remove(String login_id) throws SQLException, UserNotFoundException {
		int commId = findUser(login_id).getCommId();
		Community comm = commDAO.findCommunity(commId);  // 소속 커뮤니티
		if (comm != null && login_id.equals(comm.getChairId())) {
			// 사용자가 소속 커뮤니티의 회장인 경우 -> 그 커뮤니티의 회장을 null로 변경 및 저장
			comm.setChairId(null);
			commDAO.updateChair(comm);
		}
		return UserDAO.remove(login_id);
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

	public boolean login(String login_id, String password)
		throws SQLException, UserNotFoundException, PasswordMismatchException {
		User User = findUser(login_id);

		if (!User.matchPassword(password)) {
			throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
		}
		return true;
	}

	public List<User> makeFriends(String login_id) throws Exception {
		return UserAanlysis.recommendFriends(login_id);
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
