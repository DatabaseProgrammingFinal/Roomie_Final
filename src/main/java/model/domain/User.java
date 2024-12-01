package model.domain;

public class User {
    private int id;
    private String loginId;
    private String password;
    private String nickname;
    private String dormitoryName;
    private String roomNumber;
    private String profileUrl;
    private int points;

    // 기본 생성자
    public User() {
    	
    }

    // 매개변수 생성자
    public User(int id, String loginId, String password, String nickname, 
                String dormitoryName, String roomNumber, String profileUrl, int points) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.dormitoryName = dormitoryName;
        this.roomNumber = roomNumber;
        this.profileUrl = profileUrl;
        this.points = points;
    }
    public User(String loginId, String password, String nickname, 
            String dormitoryName, String roomNumber, String profileUrl, int points) {

    this.loginId = loginId;
    this.password = password;
    this.nickname = nickname;
    this.dormitoryName = dormitoryName;
    this.roomNumber = roomNumber;
    this.profileUrl = profileUrl;
    this.points = points;
}

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDormitoryName() {
        return dormitoryName;
    }

    public void setDormitoryName(String dormitoryName) {
        this.dormitoryName = dormitoryName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    
    public boolean matchPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    // toString() 메서드 (디버깅 용도로 유용)
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", loginId='" + loginId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", dormitoryName='" + dormitoryName + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", profileUrl='" + profileUrl + '\'' +
                ", points=" + points +
                '}';
    }
}
