package model.domain;

import java.util.Date;

public class Message {
    private int id; // 쪽지 ID
    private String content; // 내용
    private Date sentDate; // 작성일
    private int status; // 상태 (0 : 진행 전, 1: 완료)
    private Integer requestPostId; // 대여 요청 글 id 외래키로 (NULL 허용)
    private Integer providePostId; // 대여 제공 글 id 외래키로 (NULL 허용)
    private User sender; // 발신자
    private User receiver; // 수신자

    // 기본 생성자
    public Message() {}

    // 생성자
    public Message(int id, String content, Date sentDate, int status, Integer requestPostId, Integer providePostId, User sender, User receiver) {
        this.id = id;
        this.content = content;
        this.sentDate = sentDate;
        this.status = status;
        this.requestPostId = requestPostId;
        this.providePostId = providePostId;
        this.sender = sender;
        this.receiver = receiver;
    }

    // getter 및 setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getRequestPostId() {
        return requestPostId;
    }

    public void setRequestPostId(Integer requestPostId) {
        this.requestPostId = requestPostId;
    }

    public Integer getProvidePostId() {
        return providePostId;
    }

    public void setProvidePostId(Integer providePostId) {
        this.providePostId = providePostId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", sentDate=" + sentDate +
                ", status=" + status +
                ", requestPostId=" + requestPostId +
                ", providePostId=" + providePostId +
                ", sender=" + sender +
                ", receiver=" + receiver +
                '}';
    }
}
