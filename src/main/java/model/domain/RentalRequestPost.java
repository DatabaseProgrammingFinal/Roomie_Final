package model.domain;

import java.util.Date;

public class RentalRequestPost {
    private int id; // 대여 요청글 ID
    private String title; // 제목
    private String rentalItem; // 대여 물품
    private String content; // 내용
    private int points; // 포인트
    private Date rentalStartDate; // 대여 시작일
    private Date rentalEndDate; // 대여 종료일
    private String rentalLocation; // 대여 위치
    private String returnLocation; // 반납 위치
    private int status; // 상태 (0 : 진행 전, 1 : 완료)
    private int requesterId; // 요청자 ID (외래키)

    // 기본 생성자
    public RentalRequestPost() {}

    // 모든 필드를 포함한 생성자
    public RentalRequestPost(int id, String title, String rentalItem, String content, int points, Date rentalStartDate, 
                             Date rentalEndDate, String rentalLocation, String returnLocation, int status, int requesterId) {
        this.id = id;
        this.title = title;
        this.rentalItem = rentalItem;
        this.content = content;
        this.points = points;
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
        this.rentalLocation = rentalLocation;
        this.returnLocation = returnLocation;
        this.status = status;
        this.requesterId = requesterId;
    }

    // Getter 및 Setter
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
    
    public String getReturnLocation() {
        return returnLocation;
    }

    public void setReturnLocation(String returnLocation) {
        this.returnLocation = returnLocation;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(int requesterId) {
        this.requesterId = requesterId;
    }

    @Override
    public String toString() {
        return "RentalRequestPost{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", rentalItem='" + rentalItem + '\'' +
                ", content='" + content + '\'' +
                ", points=" + points +
                ", rentalStartDate=" + rentalStartDate +
                ", rentalEndDate=" + rentalEndDate +
                ", rentalLocation='" + rentalLocation + '\'' +
                 ", returnLocation='" + returnLocation + '\'' +
                ", status=" + status +
                ", requesterId=" + requesterId +
                '}';
    }
}
