package model.domain;
import java.util.Date;
public class RentalPost {
    private int id; // 대여 제공글 ID
    private String title; // 제목
    private String rentalItem; // 대여 물품
    private String content; // 내용
    private int points; // 포인트
    private Date rentalStartDate; // 대여 시작일
    private Date rentalEndDate; // 대여 종료일
    private String rentalLocation; // 대여 위치
    private int status; // 상태 (예: 0 : 진행 전, 1 : 완료)
    private Integer providerId; // 제공자 ID (외래키) (nullable)
    private Integer requesterId; // 요청자 ID (외래키) (nullable)
    private String imageUrl; // 이미지 URL (optional)
    // 기본 생성자
    public RentalPost() {}
    // 생성자
    public RentalPost(int id, String title, String rentalItem, String content, int points, Date rentalStartDate, Date rentalEndDate, String rentalLocation, int status, Integer providerId, Integer requesterId, String imageUrl) {
        this.id = id;
        this.title = title;
        this.rentalItem = rentalItem;
        this.content = content;
        this.points = points;
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
        this.rentalLocation = rentalLocation;
        this.status = status;
        this.providerId = providerId;
        this.requesterId = requesterId;
        this.imageUrl = imageUrl;
    }
    // getter, setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getRentalItem() {
        return rentalItem;
    }
    public void setRentalItem(String rentalItem) {
        this.rentalItem = rentalItem;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
    public Date getRentalStartDate() {
        return rentalStartDate;
    }
    public void setRentalStartDate(Date rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }
    public Date getRentalEndDate() {
        return rentalEndDate;
    }
    public void setRentalEndDate(Date rentalEndDate) {
        this.rentalEndDate = rentalEndDate;
    }
    public String getRentalLocation() {
        return rentalLocation;
    }
    public void setRentalLocation(String rentalLocation) {
        this.rentalLocation = rentalLocation;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public Integer getProviderId() {
        return providerId;
    }
    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }
    public Integer getRequesterId() {
        return requesterId;
    }
    public void setRequesterId(Integer requesterId) {
        this.requesterId = requesterId;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    @Override
    public String toString() {
        return "RentalPost{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", rentalItem='" + rentalItem + '\'' +
                ", content='" + content + '\'' +
                ", points=" + points +
                ", rentalStartDate=" + rentalStartDate +
                ", rentalEndDate=" + rentalEndDate +
                ", rentalLocation='" + rentalLocation + '\'' +
                ", status=" + status +
                ", providerId=" + providerId +
                ", requesterId=" + requesterId +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}