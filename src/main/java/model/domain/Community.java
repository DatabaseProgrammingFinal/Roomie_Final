package model.domain;

import java.util.Date;
import java.util.List;

/**
 * 커뮤티니 관리를 위해 필요한 도메인 클래스. Community 테이블과 대응됨
 */
public class Community {
	private int id;
	private String nickname;
	private String description;
	private Date startDate;
	private String chairId;
	private String chairnickname;
	private int numOfMembers;
	private List<User> memberList;

	public Community() { }		// 기본 생성자
	
	public Community(int id, String nickname, String description, Date startDate, String chairId, String chairnickname) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.description = description;
		this.startDate = startDate;
		this.chairId = chairId;
		this.chairnickname = chairnickname;
		this.setNumOfMembers(0);
	}
	
	public Community(int id, String nickname, String description, int numOfMembers) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.description = description;
		this.numOfMembers = numOfMembers;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getnickname() {
		return nickname;
	}

	public void setnickname(String nickname) {
		this.nickname = nickname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getChairId() {
		return chairId;
	}

	public void setChairId(String chairId) {
		this.chairId = chairId;
	}

	public String getChairnickname() {
		return chairnickname;
	}

	public void setChairnickname(String chairnickname) {
		this.chairnickname = chairnickname;
	}

	public int getNumOfMembers() {
		return numOfMembers;
	}

	public void setNumOfMembers(int numOfMembers) {
		this.numOfMembers = numOfMembers;
	}

	public List<User> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<User> memberList) {
		this.memberList = memberList;
	}

	@Override
	public String toString() {
		return "Community [id=" + id + ", nickname=" + nickname + ", description=" + description + ", startDate=" + startDate
				+ ", chairId=" + chairId + ", chairnickname=" + chairnickname + ", numOfMembers=" + numOfMembers + "]";
	}
}
