package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.domain.Message;
import model.domain.User;

public class MessageDAO {
    private JDBCUtil jdbcUtil = null;

    public MessageDAO() {
        jdbcUtil = new JDBCUtil();
    }

    // 메시지 생성
    public Message create(Message message) throws SQLException {
        String sql = "INSERT INTO Message (id, content, sent_date, status, request_post_id, provide_post_id, sender_id, recipient_id) " +
                     "VALUES (Message_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = new Object[] {
            message.getContent(),
            new java.sql.Date(message.getSentDate().getTime()),
            message.getStatus(),
            message.getRequestPostId(),
            message.getProvidePostId(),
            message.getSender().getId(),
            message.getReceiver().getId()
        };
        jdbcUtil.setSqlAndParameters(sql, params);
        String[] key = {"id"}; // PK

        try {
            jdbcUtil.executeUpdate(key);
            ResultSet rs = jdbcUtil.getGeneratedKeys();
            if (rs.next()) {
                int generatedKey = rs.getInt(1);
                message.setId(generatedKey);
            }
            return message;
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
            return null;
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close();
        }
    }

    // 모든 메시지 가져오기
    public List<Message> showAllMessages() throws SQLException {
        String sql = "SELECT * FROM Message";
        jdbcUtil.setSqlAndParameters(sql, null);

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            List<Message> messages = new ArrayList<>();
            while (rs.next()) {
                Message message = new Message();
                message.setId(rs.getInt("id"));
                message.setContent(rs.getString("content"));
                message.setSentDate(rs.getDate("sent_date"));
                message.setStatus(rs.getInt("status"));
                message.setRequestPostId(rs.getObject("request_post_id") != null ? rs.getInt("request_post_id") : null);
                message.setProvidePostId(rs.getObject("provide_post_id") != null ? rs.getInt("provide_post_id") : null);
                // Sender and Receiver would need to be retrieved or set as objects here
                messages.add(message);
            }
            return messages;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            jdbcUtil.close();
        }
    }

    // 보낸 메시지
    public List<Message> showSentMessages(int senderId) throws SQLException {
        String sql = "SELECT * FROM Message WHERE sender_id = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] {senderId});

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            List<Message> messages = new ArrayList<>();
            while (rs.next()) {
                Message message = new Message();
                message.setId(rs.getInt("id"));
                message.setContent(rs.getString("content"));
                message.setSentDate(rs.getDate("sent_date"));
                message.setStatus(rs.getInt("status"));
                message.setRequestPostId(rs.getObject("request_post_id") != null ? rs.getInt("request_post_id") : null);
                message.setProvidePostId(rs.getObject("provide_post_id") != null ? rs.getInt("provide_post_id") : null);
                messages.add(message);
            }
            return messages;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            jdbcUtil.close();
        }
    }

    // 받은 메시지
    public List<Message> showReceivedMessages(int recipientId) throws SQLException {
        String sql = "SELECT * FROM Message WHERE recipient_id = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] {recipientId});

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            List<Message> messages = new ArrayList<>();
            while (rs.next()) {
                Message message = new Message();
                message.setId(rs.getInt("id"));
                message.setContent(rs.getString("content"));
                message.setSentDate(rs.getDate("sent_date"));
                message.setStatus(rs.getInt("status"));
                message.setRequestPostId(rs.getObject("request_post_id") != null ? rs.getInt("request_post_id") : null);
                message.setProvidePostId(rs.getObject("provide_post_id") != null ? rs.getInt("provide_post_id") : null);
                messages.add(message);
            }
            return messages;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            jdbcUtil.close();
        }
    }

    // 메시지 검색
    public List<Message> searchMessages(String query) throws SQLException {
        String sql = "SELECT * FROM Message WHERE content LIKE ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] {"%" + query + "%"});

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            List<Message> messages = new ArrayList<>();
            while (rs.next()) {
                Message message = new Message();
                message.setId(rs.getInt("id"));
                message.setContent(rs.getString("content"));
                message.setSentDate(rs.getDate("sent_date"));
                message.setStatus(rs.getInt("status"));
                message.setRequestPostId(rs.getObject("request_post_id") != null ? rs.getInt("request_post_id") : null);
                message.setProvidePostId(rs.getObject("provide_post_id") != null ? rs.getInt("provide_post_id") : null);
             // 보낸 메시지 및 받은 메시지 관련 메서드에서 User 객체 생성 시, id만 전달하여 생성하도록 수정
                message.setSender(new User(rs.getInt("sender_id"))); // sender_id를 가진 User 객체 생성
                message.setReceiver(new User(rs.getInt("recipient_id"))); // recipient_id를 가진 User 객체 생성
                messages.add(message);
            }
            return messages;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            jdbcUtil.close();
        }
    }
}
