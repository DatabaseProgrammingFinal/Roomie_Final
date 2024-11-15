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
    private int commId;
    private String commNickname;

    public User() { }  // 기본 생성자
 // 추가 생성자: id를 통해 User 객체를 생성할 수 있도록 함
    
    public User(int id) {
        this.id = id;
    }
    
    // 모든 필드를 사용하는 생성자
    public User(int id, String loginId, String password, String nickname, String dormitoryName, 
                String roomNumber, String profileUrl, int points, int commId, String commNickname) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.dormitoryName = dormitoryName;
        this.roomNumber = roomNumber;
        this.profileUrl = profileUrl;
        this.points = points;
        this.commId = commId;
        this.commNickname = commNickname;
    }

    // 필요한 필드를 사용하는 추가 생성자 (컴파일 에러 해결용)
    public User(String loginId, String password, String nickname, String dormitoryName, 
                String roomNumber, int points) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.dormitoryName = dormitoryName;
        this.roomNumber = roomNumber;
        this.points = points;
    }

    // Getter and Setter methods with CamelCase naming convention
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

    public boolean matchPassword(String inputPassword) {
        return this.password.equals(inputPassword);
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

    public int getCommId() {
        return commId;
    }

    public void setCommId(int commId) {
        this.commId = commId;
    }

    public String getCommNickname() {
        return commNickname;
    }

    public void setCommNickname(String commNickname) {
        this.commNickname = commNickname;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", loginId=" + loginId + ", password=" + password + ", nickname=" + nickname
                + ", dormitoryName=" + dormitoryName + ", roomNumber=" + roomNumber + ", profileUrl=" + profileUrl
                + ", points=" + points + ", commId=" + commId + ", commNickname=" + commNickname + "]";
    }
}

