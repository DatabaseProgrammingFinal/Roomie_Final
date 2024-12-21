import model.dao.MessageDAO;
import model.dao.UserDAO;
import model.domain.Message;
import model.domain.User;
import model.service.MessageService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class MessageServiceTest {

    private MessageService messageService;
    private User sender;
    private User receiver;
    private Message testMessage;

    @Before
    public void setUp() throws Exception {
        messageService = new MessageService();

        // Sender와 Receiver 생성
        sender = new User("sender01", "password", "SenderUser", "DormA", "101", "profile.jpg", 100);
        receiver = new User("receiver01", "password", "ReceiverUser", "DormB", "102", "profile.jpg", 50);

        UserDAO userDAO = new UserDAO();
        userDAO.create(sender);
        userDAO.create(receiver);

        // 테스트 메시지 생성
        testMessage = new Message(
                "테스트 메시지 내용",
                new Date(),
                0, // 상태
                2, // requestPostId
                null, // providePostId
                sender,
                receiver
        );
        messageService.createMessage(testMessage);
    }

    @After
    public void tearDown() throws Exception {
        MessageDAO messageDAO = new MessageDAO();
        if (testMessage != null && testMessage.getId() > 0) {
            messageDAO.delete(testMessage.getId());
        }

        UserDAO userDAO = new UserDAO();
        if (sender != null) {
            userDAO.remove(sender.getLoginId());
        }
        if (receiver != null) {
            userDAO.remove(receiver.getLoginId());
        }
    }

    @Test
    public void testCreateMessage() throws SQLException {
        Message message = new Message(
                "새로운 메시지 내용",
                new Date(),
                1,
                2,
                null,
                sender,
                receiver
        );

        Message createdMessage = messageService.createMessage(message);
        assertNotNull(createdMessage);
        assertTrue(createdMessage.getId() > 0);
    }
/*
    @Test
    public void testGetSentMessages() throws SQLException {
        List<Message> sentMessages = messageService.getSentMessages(sender.getId());
        assertNotNull(sentMessages);
        assertFalse(sentMessages.isEmpty());
        assertTrue(sentMessages.stream().anyMatch(m -> m.getContent().equals("테스트 메시지 내용")));
    }

    @Test
    public void testGetReceivedMessages() throws SQLException {
        List<Message> receivedMessages = messageService.getReceivedMessages(receiver.getId());
        assertNotNull(receivedMessages);
        assertFalse(receivedMessages.isEmpty());
        assertTrue(receivedMessages.stream().anyMatch(m -> m.getContent().equals("테스트 메시지 내용")));
    }

    @Test
    public void testDeleteMessage() throws SQLException {
        boolean isDeleted = messageService.deleteMessage(testMessage.getId());
        assertTrue(isDeleted);
    }
*/
    @Test
    public void testSearchMessages() throws SQLException {
        List<Message> foundMessages = messageService.searchMessages("테스트");
        assertNotNull(foundMessages);
        assertFalse(foundMessages.isEmpty());
        assertTrue(foundMessages.stream().anyMatch(m -> m.getContent().contains("테스트")));
    }
}
