import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.dao.ConnectionManager;
import model.dao.MessageDAO;
import model.dao.UserDAO;
import model.domain.Message;
import model.domain.User;

public class MessageDAOTest {
    private MessageDAO messageDAO;
    private UserDAO userDAO;
    private Message testMessage;
    private User sender;
    private User receiver;
    private ConnectionManager connectionManager;

    @Before
    public void setUp() throws Exception {
        connectionManager = new ConnectionManager();
        if (connectionManager.getConnection() == null) {
            throw new IllegalStateException("ConnectionManager에서 Connection을 가져올 수 없습니다. context.properties 파일 설정을 확인하세요.");
        }
        
        messageDAO = new MessageDAO();
        userDAO = new UserDAO();

        // Sender와 Receiver 사용자 생성
        sender = new User(
            "user01", "password123", "김철수", "행복관", "101", "example.jpg", 100
        );
        receiver = new User(
            "user02", "password456", "이영희", "자유관", "202", "example2.jpg", 150
        );

        // 기존 데이터 제거 및 삽입
        if (userDAO.findUser(sender.getLoginId()) != null) {
            userDAO.remove(sender.getLoginId());
        }
        if (userDAO.findUser(receiver.getLoginId()) != null) {
            userDAO.remove(receiver.getLoginId());
        }
        userDAO.create(sender);
        userDAO.create(receiver);

        // 테스트 메시지 생성
        testMessage = new Message(
            "테스트 메시지 내용",
            new Date(),
            1,
            2,
            null,
            sender,
            receiver
        );
        messageDAO.create(testMessage);
    }

    @After
    public void tearDown() throws Exception {
        if (testMessage != null && testMessage.getId() != 0) {
            messageDAO.delete(testMessage.getId());
        }
        if (sender != null) {
            userDAO.remove(sender.getLoginId());
        }
        if (receiver != null) {
            userDAO.remove(receiver.getLoginId());
        }
    }

    @Test
    public void testCreate() throws SQLException {
        // 메시지 생성
        Message createdMessage = messageDAO.create(testMessage);
        assertNotNull(createdMessage);
        assertTrue(createdMessage.getId() > 0);

        // 데이터베이스에 생성 확인
        List<Message> messages = messageDAO.showAllMessages();
        assertTrue(messages.stream().anyMatch(message -> message.getContent().equals(testMessage.getContent())));
    }

    @Test
    public void testShowAllMessages() throws SQLException {
        List<Message> messages = messageDAO.showAllMessages();
        assertNotNull(messages);
        assertFalse(messages.isEmpty());
    }

    @Test
    public void testShowSentMessages() throws SQLException {
        List<Message> sentMessages = messageDAO.showSentMessages(sender.getId());

        assertNotNull(sentMessages); // null 체크
        assertFalse(sentMessages.isEmpty()); // 리스트가 비어 있지 않은지 확인

        // sender 필드가 null이 아닌지 확인
        assertTrue(sentMessages.stream().allMatch(message -> message.getSender() != null));
        assertTrue(sentMessages.stream().anyMatch(message -> message.getSender().getId() == sender.getId()));
    }

    @Test
    public void testShowReceivedMessages() throws SQLException {
        List<Message> receivedMessages = messageDAO.showReceivedMessages(receiver.getId());
        assertNotNull(receivedMessages);
        assertTrue(receivedMessages.stream().anyMatch(message -> message.getReceiver().getId() == receiver.getId()));
    }

    @Test
    public void testSearchMessages() throws SQLException {
        List<Message> searchResults = messageDAO.searchMessages("테스트");
        assertNotNull(searchResults);
        assertTrue(searchResults.stream().anyMatch(message -> message.getContent().contains("테스트")));
    }
}
