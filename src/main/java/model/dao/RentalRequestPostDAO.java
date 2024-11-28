package model.dao;

import model.domain.RentalRequestPost;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalRequestPostDAO {
    private final Connection connection;

    // 생성자
    public RentalRequestPostDAO(Connection connection) {
        this.connection = connection;
    }

    // 1. 대여 요청글 등록
    public boolean createRentalRequestPost(RentalRequestPost post) {
        String sql = """
            INSERT INTO rental_request_post 
                (title, rental_item, content, points, rental_start_date, rental_end_date, rental_location, status, requester_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, post.getTitle());
            pstmt.setString(2, post.getRentalItem());
            pstmt.setString(3, post.getContent());
            pstmt.setInt(4, post.getPoints());
            pstmt.setDate(5, new java.sql.Date(post.getRentalStartDate().getTime()));
            pstmt.setDate(6, new java.sql.Date(post.getRentalEndDate().getTime()));
            pstmt.setString(7, post.getRentalLocation());
            pstmt.setInt(8, post.getStatus());
            pstmt.setInt(9, post.getRequesterId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        post.setId(rs.getInt(1)); // 트리거가 생성한 ID 가져오기
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // 2. 대여 요청글 조회 (ID로)
    public RentalRequestPost getRentalRequestPostById(int id) {
        String sql = "SELECT * FROM rental_request_post WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapToRentalRequestPost(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3. 제목으로 대여 요청글 검색
    public List<RentalRequestPost> searchRentalRequestPostsByTitle(String title) {
        String sql = "SELECT * FROM rental_request_post WHERE title LIKE ?";
        List<RentalRequestPost> posts = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + title + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    posts.add(mapToRentalRequestPost(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    // 4. 모든 대여 요청글 조회
    public List<RentalRequestPost> getAllRentalRequestPosts() {
        String query = "SELECT * FROM rental_request_post";
        List<RentalRequestPost> posts = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                posts.add(mapToRentalRequestPost(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    // ResultSet을 RentalRequestPost 객체로 매핑하는 메서드
    private RentalRequestPost mapToRentalRequestPost(ResultSet rs) throws SQLException {
        return new RentalRequestPost(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("rental_item"),
                rs.getString("content"),
                rs.getInt("points"),
                rs.getDate("rental_start_date"),
                rs.getDate("rental_end_date"),
                rs.getString("rental_location"),
                rs.getInt("status"),
                rs.getInt("requester_id")
        );
    }
}
