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
	private static UserManager userMan = new UserManager();
	private UserDAO userDAO;
	private CommunityDAO commDAO;
	private UserAnalysis userAanlysis;

	private UserManager() {
		try {
			userDAO = new UserDAO();
			commDAO = new CommunityDAO();
			userAanlysis = new UserAnalysis(userDAO);
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
	
	public static UserManager getInstance() {
		return userMan;
	}
	
	public int create(User user) throws SQLException, ExistingUserException {
		if (userDAO.existingUser(user.getlogin_id()) == true) {
			throw new ExistingUserException(user.getlogin_id() + "는 존재하는 아이디입니다.");
		}
		return userDAO.create(user);
	}

	public int update(User user) throws SQLException, UserNotFoundException {
		int oldCommId = findUser(user.getlogin_id()).getCommId();
		if (user.getCommId() != oldCommId) { 	// 소속 커뮤티니가 변경됨
			Community comm = commDAO.findCommunity(oldCommId);  // 기존 소속 커뮤니티
			if (comm != null && user.getlogin_id().equals(comm.getChairId())) {
				// 사용자가 기존 소속 커뮤니티의 회장인 경우 -> 그 커뮤니티의 회장을 null로 변경 및 저장
				comm.setChairId(null);
				commDAO.updateChair(comm);
			}
		}
		return userDAO.update(user);
	}	

	public int remove(String login_id) throws SQLException, UserNotFoundException {
		int commId = findUser(login_id).getCommId();
		Community comm = commDAO.findCommunity(commId);  // 소속 커뮤니티
		if (comm != null && login_id.equals(comm.getChairId())) {
			// 사용자가 소속 커뮤니티의 회장인 경우 -> 그 커뮤니티의 회장을 null로 변경 및 저장
			comm.setChairId(null);
			commDAO.updateChair(comm);
		}
		return userDAO.remove(login_id);
	}

	public User findUser(String login_id)
		throws SQLException, UserNotFoundException {
		User user = userDAO.findUser(login_id);
		
		if (user == null) {
			throw new UserNotFoundException(login_id + "는 존재하지 않는 아이디입니다.");
		}		
		return user;
	}

	public List<User> findUserList() throws SQLException {
			return userDAO.findUserList();
	}
	
	public List<User> findUserList(int currentPage, int countPerPage)
		throws SQLException {
		return userDAO.findUserList(currentPage, countPerPage);
	}

	public boolean login(String login_id, String password)
		throws SQLException, UserNotFoundException, PasswordMismatchException {
		User user = findUser(login_id);

		if (!user.matchPassword(password)) {
			throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
		}
		return true;
	}

	public List<User> makeFriends(String login_id) throws Exception {
		return userAanlysis.recommendFriends(login_id);
	}
	
	public Community createCommunity(Community comm) throws SQLException {
		return commDAO.create(comm);		
	}

	public int updateCommunity(Community comm) throws SQLException {
		return commDAO.update(comm);				
	}
	
	public Community findCommunity(int commId) throws SQLException {
		Community comm = commDAO.findCommunity(commId); 
		
		List<User> memberList = userDAO.findUsersInCommunity(commId);
		comm.setMemberList(memberList);
		
		int numOfMembers = userDAO.getNumberOfUsersInCommunity(commId);
		comm.setNumOfMembers(numOfMembers);
		return comm;
	}
	
	public List<Community> findCommunityList() throws SQLException {
		return commDAO.findCommunityList();
	}
	
	public List<User> findCommunityMembers(int commId) throws SQLException {
		return userDAO.findUsersInCommunity(commId);
	}

	public UserDAO getUserDAO() {
		return this.userDAO;
	}
}
