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
    	System.out.println("DEBUG: Message created successfully");
        String sql = "INSERT INTO Message (id, content, sent_date, status, request_post_id, provide_post_id, sender_id, recipient_id) "
                   + "VALUES (Message_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
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
        String[] key = {"id"}; // PK 반환 설정

        try {
            System.out.println("DEBUG: Executing SQL: " + sql);

            int result = jdbcUtil.executeUpdate(key); // 실행 결과 반환
            System.out.println("DEBUG: executeUpdate result: " + result);

            if (result > 0) {
                ResultSet rs = jdbcUtil.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    message.setId(generatedId);
                    System.out.println("DEBUG: Generated ID: " + generatedId);
                } else {
                    System.out.println("DEBUG: No key generated.");
                }
            } else {
                System.out.println("DEBUG: No rows inserted.");
            }

            jdbcUtil.commit(); // 트랜잭션 커밋
            return message;

        } catch (Exception e) {
            jdbcUtil.rollback(); // 트랜잭션 롤백
            e.printStackTrace();
            throw new SQLException("Message 저장 중 오류 발생", e);

        } finally {
            jdbcUtil.close(); // JDBC 연결 종료
            System.out.println("DEBUG: JDBC connection closed.");
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

                int receiverId = rs.getInt("recipient_id");
                int senderId = rs.getInt("sender_id");
                System.out.println("DEBUG: Receiver ID: " + receiverId);
                System.out.println("DEBUG: Sender ID: " + senderId);

                // UserDAO를 통해 Sender와 Receiver를 조회
                User sender = new User();                		
                if (sender != null) {
                    message.setSender(sender); // Sender 설정
                }
                
                User receiver = new User();
                if (receiver != null) {
                    message.setReceiver(receiver); // Receiver 설정
                }
                
                
                messages.add(message);
            }
            return messages;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
        	jdbcUtil.commit();
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
                
                int receiverId = rs.getInt("recipient_id");
                System.out.println("DEBUG: Receiver ID: " + receiverId);
                System.out.println("DEBUG: Sender ID: " + senderId);

                // UserDAO를 통해 Sender와 Receiver를 조회
                User sender = new User();                		
                if (sender != null) {
                    message.setSender(sender); // Sender 설정
                }
                
                User receiver = new User();
                if (receiver != null) {
                    message.setReceiver(receiver); // Receiver 설정
                }
                
                
                messages.add(message);
            }
            return messages;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
        	jdbcUtil.commit();
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
                
             // Receiver와 Sender 확인
                int senderId = rs.getInt("sender_id");
                System.out.println("DEBUG: Receiver ID: " + recipientId);
                System.out.println("DEBUG: Sender ID: " + senderId);

                // UserDAO를 통해 Sender와 Receiver를 조회
                User sender = new User();                		
                if (sender != null) {
                    message.setSender(sender); // Sender 설정
                }
                
                User receiver = new User();
                if (receiver != null) {
                    message.setReceiver(receiver); // Receiver 설정
                }
                
                messages.add(message);
            }
            return messages;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
        	jdbcUtil.commit();
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
            
                int receiverId = rs.getInt("recipient_id");
                int senderId = rs.getInt("sender_id");
                System.out.println("DEBUG: Receiver ID: " + receiverId);
                System.out.println("DEBUG: Sender ID: " + senderId);

                // UserDAO를 통해 Sender와 Receiver를 조회
                User sender = new User();                		
                if (sender != null) {
                    message.setSender(sender); // Sender 설정
                }
                
                User receiver = new User();
                if (receiver != null) {
                    message.setReceiver(receiver); // Receiver 설정
                }
                
                
                messages.add(message);
            }
            return messages;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
        	jdbcUtil.commit();
            jdbcUtil.close();
        }
    }
    
    public int delete(int messageId) throws SQLException {
        String sql = "DELETE FROM Message WHERE id = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] {messageId});

        try {
            return jdbcUtil.executeUpdate();
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
            return 0;
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close();
        }
    }
 // 특정 사용자가 송신자 또는 수신자인 메시지 가져오기
    public List<Message> findMessagesByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM Message WHERE sender_id = ? OR recipient_id = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] {userId, userId});

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

                int senderId = rs.getInt("sender_id");
                int recipientId = rs.getInt("recipient_id");
                System.out.println("DEBUG: Sender ID: " + senderId);
                System.out.println("DEBUG: Recipient ID: " + recipientId);

                // Sender와 Receiver 설정
                User sender = new User();
                sender.setId(senderId);
                message.setSender(sender);

                User receiver = new User();
                receiver.setId(recipientId);
                message.setReceiver(receiver);

                messages.add(message);
            }
            return messages;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close();
        }
    }

}
