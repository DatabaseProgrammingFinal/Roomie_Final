package model.service;

import model.dao.MessageDAO;
import model.dao.UserDAO;
import model.domain.Message;
import model.domain.User;

import java.sql.SQLException;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    private UserDAO userDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
        this.userDAO = new UserDAO();
    }
    /**
     * 메시지 생성
     */
    public Message createMessage(Message message) throws SQLException {
    	
    	System.out.println("DEBUG: Message before creation: " + message);
        if (message.getSender() == null || message.getReceiver() == null) {
            throw new IllegalArgumentException("Sender와 Receiver 정보는 필수입니다.");
        }

        // 메시지 생성 시 필요한 기본값 설정
        if (message.getSentDate() == null) {
            message.setSentDate(new java.util.Date()); // 현재 시간으로 설정
        }
        if (message.getStatus() == 0) {
            message.setStatus(1); // 기본 상태값 설정
        }

        return messageDAO.create(message); // DAO를 통해 DB에 저장
    }

    /**
     * 필터에 따라 메시지 조회 (전체, 보낸 메시지, 받은 메시지)
     */
    public List<Message> getMessages(int userId, String filter) throws SQLException {
        List<Message> messages;

        switch (filter) {
            case "sent":
                messages = messageDAO.showSentMessages(userId); // 보낸 메시지
                break;
            case "received":
                messages = messageDAO.showReceivedMessages(userId); // 받은 메시지
                break;
            default:
                messages = messageDAO.findMessagesByUserId(userId); // 모든 메시지
                break;
        }

        // Sender와 Receiver 정보를 채워줌
        populateUserDetails(messages);
        return messages;
    }

    /**
     * 메시지 리스트에 Sender와 Receiver 정보를 채워줌
     */
    private void populateUserDetails(List<Message> messages) throws SQLException {
        for (Message message : messages) {
            if (message.getSender() != null) {
                User sender = userDAO.findUserById(message.getSender().getId());
                message.setSender(sender);
            }
            if (message.getReceiver() != null) {
                User receiver = userDAO.findUserById(message.getReceiver().getId());
                message.setReceiver(receiver);
            }
        }
    }
    
    public List<Message> getMessagesWithDetails(Integer senderId, Integer recipientId) throws SQLException {
        // 메시지 조회
        List<Message> messages = messageDAO.getMessages(senderId, recipientId);
        populateUserDetails(messages);
        return messages;
    } 
    public List<Message> getLatestMessages(int userId) throws SQLException {
        return messageDAO.findLatestMessagesByUserId(userId);
    }
    public List<Message> searchMessages(String query) throws SQLException {
        return messageDAO.searchMessages(query);
    }


}