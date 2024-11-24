package model.dao;

import model.domain.RentalProvidePost;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalProvidePostDAO {
    private Connection connection;

    // 생성자
    public RentalProvidePostDAO(Connection connection) {
        this.connection = connection;
    }

    // 1. 대여글 등록
    public boolean createRentalProvidePost(RentalProvidePost post) {
        String query = "INSERT INTO rental_provide_posts (title, rental_item, content, points, rental_start_date, rental_end_date, rental_location, status, provider_id, image_url) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, post.getTitle());
            pstmt.setString(2, post.getRentalItem());
            pstmt.setString(3, post.getContent());
            pstmt.setInt(4, post.getPoints());
            pstmt.setDate(5, new java.sql.Date(post.getRentalStartDate().getTime()));
            pstmt.setDate(6, new java.sql.Date(post.getRentalEndDate().getTime()));
            pstmt.setString(7, post.getRentalLocation());
            pstmt.setInt(8, post.getStatus());
            pstmt.setInt(9, post.getProviderId());
            pstmt.setString(10, post.getImageUrl());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. 특정 대여글 조회 (ID로)
    public RentalProvidePost getRentalProvidePostById(int id) {
        String query = "SELECT * FROM rental_provide_posts WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRentalProvidePost(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3. 제목으로 대여글 검색
    public List<RentalProvidePost> searchRentalProvidePostsByTitle(String title) {
        String query = "SELECT * FROM rental_provide_posts WHERE title LIKE ?";
        List<RentalProvidePost> posts = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, "%" + title + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    posts.add(mapResultSetToRentalProvidePost(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    // 4. 모든 대여글 조회
    public List<RentalProvidePost> getAllRentalProvidePosts() {
        String query = "SELECT * FROM rental_provide_posts";
        List<RentalProvidePost> posts = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                posts.add(mapResultSetToRentalProvidePost(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    // ResultSet을 RentalProvidePost 객체로 매핑하는 메서드
    private RentalProvidePost mapResultSetToRentalProvidePost(ResultSet rs) throws SQLException {
        RentalProvidePost post = new RentalProvidePost();
        post.setId(rs.getInt("id"));
        post.setTitle(rs.getString("title"));
        post.setRentalItem(rs.getString("rental_item"));
        post.setContent(rs.getString("content"));
        post.setPoints(rs.getInt("points"));
        post.setRentalStartDate(rs.getDate("rental_start_date"));
        post.setRentalEndDate(rs.getDate("rental_end_date"));
        post.setRentalLocation(rs.getString("rental_location"));
        post.setStatus(rs.getInt("status"));
        post.setProviderId(rs.getInt("provider_id"));
        post.setImageUrl(rs.getString("image_url"));
        return post;
    }
}
