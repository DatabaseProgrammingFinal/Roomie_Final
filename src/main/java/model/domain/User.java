package model.domain;

/**
 * 사용자 관리를 위해 필요한 도메인 클래스. USERINFO 테이블과 대응됨
 */
public class User {
	private String userId;
	private String password;
	private String name;
	private String dorm;
	private String room;
	private int commId;
	private String commName;

	public User() { }		// 기본 생성자
	
	public User(String userId, String password, String name, String dorm, String room, int commId) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.dorm = dorm;
		this.room = room;
		this.commId = commId;
	}
	
	public User(String userId, String password, String name, String dorm, String room, int commId, String commName) {
		this(userId, password, name, dorm, room, commId);
		this.commName = commName;
	}

	public User(String userId, String name, String dorm, String room) {
		this.userId = userId;
		this.name = name;
		this.dorm = dorm;
		this.room = room;		
	}
	
	/*public void update(User updateUser) {
        this.password = updateUser.password;
        this.name = updateUser.name;
        this.dorm = updateUser.dorm;
        this.room = updateUser.room;
    }*/
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDorm() {
		return dorm;
	}

	public void setdorm(String dorm) {
		this.dorm = dorm;
	}

	public String getRoom() {
		return room;
	}

	public void setroom(String room) {
		this.room = room;
	}

	public int getCommId() {
		return commId;
	}

	public void setCommId(int commId) {
		this.commId = commId;
	}

	public String getCommName() {
		return commName;
	}

	public void setCommName(String commName) {
		this.commName = commName;
	}

	
	/* 비밀번호 검사 */
	public boolean matchPassword(String password) {
		if (password == null) {
			return false;
		}
		return this.password.equals(password);
	}
	
	public boolean isSameUser(String userid) {
        return this.userId.equals(userid);
    }

	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", dorm=" + dorm + ", room="
				+ room + ", commId=" + commId + "]";
	}

	
	
}
