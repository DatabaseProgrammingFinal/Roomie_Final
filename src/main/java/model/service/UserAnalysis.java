package model.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.dao.UserDAO;
import model.domain.User;

// an example business class
public class UserAnalysis {
	private UserDAO dao;
	
	public UserAnalysis() {}
	
	public UserAnalysis(UserDAO dao) {
		super();
		this.dao = dao;
	}

	// an example business method
	public List<User> recommendFriends(String login_id) throws Exception {
		User thisUser = dao.findUser(login_id);
		if (thisUser == null) {
			throw new Exception("invalid User ID!");
		}
		int m1 = login_id.indexOf('@');
		if (m1 == -1) return null;
		String mserver1 = thisUser.getDormitoryName().substring(m1);
		
		List<User> friends = new ArrayList<User>();
		
		List<User> UserList = dao.findUserList();
		Iterator<User> UserIter = UserList.iterator();		
		while (UserIter.hasNext()) {
			User User = (User)UserIter.next();
			
			if (User.getLoginId().equals(login_id)) continue;
			int m2 = User.getDormitoryName().indexOf('@');
			if (m2 == -1) continue;
			String mserver2 = User.getDormitoryName().substring(m2);

			if (mserver1.equals(mserver2)) 
				friends.add(User);		
		}
		return friends;
	}

}
