package model.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.domain.RentalPost;
public class RentalRequestPostDAO {
    private JDBCUtil jdbcUtil = null;
    public RentalRequestPostDAO() {
        jdbcUtil = new JDBCUtil();
    }
    // 대여 요청 글 등록
    public RentalPost create(RentalPost post) throws SQLException {
        String sql = "INSERT INTO Rental_request_post (id, title, rental_item, content, points, rental_start_date, rental_end_date, rental_location, status, requester_id) " +
                     "VALUES (Rental_request_post_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = new Object[] {
            post.getTitle(),
            post.getRentalItem(),
            post.getContent(),
            post.getPoints(),
            new java.sql.Date(post.getRentalStartDate().getTime()),
            new java.sql.Date(post.getRentalEndDate().getTime()),
            post.getRentalLocation(),
            post.getStatus(),
            post.getRequesterId()
        };
        jdbcUtil.setSqlAndParameters(sql, params);
        String[] key = {"id"};
        try {
            jdbcUtil.executeUpdate(key);
            ResultSet rs = jdbcUtil.getGeneratedKeys();
            if (rs.next()) {
                int generatedKey = rs.getInt(1);
                post.setId(generatedKey);
            }
            return post;
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
            return null;
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close();
        }
    }
    // 특정 대여 요청 글 조회 (ID로)
    public RentalPost findById(int id) throws SQLException {
        String sql = "SELECT * FROM Rental_request_post WHERE id = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] {id});
        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                RentalPost post = new RentalPost();
                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setRentalItem(rs.getString("rental_item"));
                post.setContent(rs.getString("content"));
                post.setPoints(rs.getInt("points"));
                post.setRentalStartDate(rs.getDate("rental_start_date"));
                post.setRentalEndDate(rs.getDate("rental_end_date"));
                post.setRentalLocation(rs.getString("rental_location"));
                post.setStatus(rs.getInt("status"));
                post.setRequesterId(rs.getInt("requester_id"));
                return post;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }
    // 제목으로 대여 요청 글 검색
    public List<RentalPost> searchByTitle(String title) throws SQLException {
        String sql = "SELECT * FROM Rental_request_post WHERE title LIKE ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] {"%" + title + "%"});
        try {
            ResultSet rs = jdbcUtil.executeQuery();
            List<RentalPost> posts = new ArrayList<>();
            while (rs.next()) {
                RentalPost post = new RentalPost();
                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setRentalItem(rs.getString("rental_item"));
                post.setContent(rs.getString("content"));
                post.setPoints(rs.getInt("points"));
                post.setRentalStartDate(rs.getDate("rental_start_date"));
                post.setRentalEndDate(rs.getDate("rental_end_date"));
                post.setRentalLocation(rs.getString("rental_location"));
                post.setStatus(rs.getInt("status"));
                post.setRequesterId(rs.getInt("requester_id"));
                posts.add(post);
            }
            return posts;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }
    // 모든 대여 요청 글 조회
    public List<RentalPost> findAll() throws SQLException {
        String sql = "SELECT * FROM Rental_request_post";
        jdbcUtil.setSqlAndParameters(sql, null);
        try {
            ResultSet rs = jdbcUtil.executeQuery();
            List<RentalPost> posts = new ArrayList<>();
            while (rs.next()) {
                RentalPost post = new RentalPost();
                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setRentalItem(rs.getString("rental_item"));
                post.setContent(rs.getString("content"));
                post.setPoints(rs.getInt("points"));
                post.setRentalStartDate(rs.getDate("rental_start_date"));
                post.setRentalEndDate(rs.getDate("rental_end_date"));
                post.setRentalLocation(rs.getString("rental_location"));
                post.setStatus(rs.getInt("status"));
                post.setRequesterId(rs.getInt("requester_id"));
                posts.add(post);
            }
            return posts;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }
}