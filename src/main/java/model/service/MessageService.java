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

    // 기본 생성자
    public MessageService() {
        this.messageDAO = new MessageDAO();
        this.userDAO = new UserDAO();
    }

    /**
     * 메시지 생성
     *
     * @param message 생성할 Message 객체
     * @return 생성된 Message 객체
     * @throws SQLException 데이터베이스 오류
     */
    public Message createMessage(Message message) throws SQLException {
        // request_post_id와 provide_post_id 중 하나가 반드시 있어야 함
        if (message.getRequestPostId() == null && message.getProvidePostId() == null) {
            throw new IllegalArgumentException("request_post_id와 provide_post_id 중 하나는 값이 있어야 합니다.");
        }
        return messageDAO.create(message);
    }

    /**
     * 모든 메시지 조회
     *
     * @return 모든 메시지 리스트
     * @throws SQLException 데이터베이스 오류
     */
    public List<Message> getAllMessages() throws SQLException {
        return messageDAO.showAllMessages();
    }

    /**
     * 특정 발신자가 보낸 메시지 조회
     *
     * @param senderId 발신자 ID
     * @return 발신자가 보낸 메시지 리스트
     * @throws SQLException 데이터베이스 오류
     */
    public List<Message> getSentMessages(int senderId) throws SQLException {
        List<Message> messages = messageDAO.showSentMessages(senderId);

        // Sender와 Receiver의 User 객체를 채움
        for (Message message : messages) {
            if (message.getReceiver() != null) {
                User receiver = userDAO.findUserById(message.getReceiver().getId());
                message.setReceiver(receiver);
            }
        }
        return messages;
    }

    /**
     * 특정 수신자가 받은 메시지 조회
     *
     * @param recipientId 수신자 ID
     * @return 수신자가 받은 메시지 리스트
     * @throws SQLException 데이터베이스 오류
     */
    public List<Message> getReceivedMessages(int recipientId) throws SQLException {
        List<Message> messages = messageDAO.showReceivedMessages(recipientId);

        // Sender와 Receiver의 User 객체를 채움
        for (Message message : messages) {
            if (message.getSender() != null) {
                User sender = userDAO.findUserById(message.getSender().getId());
                message.setSender(sender);
            }
        }
        return messages;
    }

    /**
     * 메시지 검색
     *
     * @param query 검색어
     * @return 검색 결과 리스트
     * @throws SQLException 데이터베이스 오류
     */
    public List<Message> searchMessages(String query) throws SQLException {
        return messageDAO.searchMessages(query);
    }

    /**
     * 메시지 삭제
     *
     * @param messageId 삭제할 메시지 ID
     * @return 삭제 성공 여부
     * @throws SQLException 데이터베이스 오류
     */
    public boolean deleteMessage(int messageId) throws SQLException {
        int result = messageDAO.delete(messageId);
        return result > 0;
    }
}
