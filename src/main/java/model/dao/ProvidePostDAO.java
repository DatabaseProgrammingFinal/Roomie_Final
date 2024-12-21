package model.dao;

import model.domain.RentalProvidePost;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProvidePostDAO {
   private JDBCUtil jdbcUtil = null;
   
   public ProvidePostDAO() {         
        jdbcUtil = new JDBCUtil();   // JDBCUtil 객체 생성
    }

    // 1. 대여글 등록
    public int createRentalProvidePost(RentalProvidePost post) throws Exception {
        String sql = "INSERT INTO rental_provide_post (id, title, rental_item, content, points, rental_start_date, rental_end_date, rental_location, return_location, status, provider_id, image_url) " +
                "VALUES (rental_provide_post_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Object[] param = new Object[] {
              post.getTitle(), post.getRentalItem(),
              post.getContent(), post.getPoints(), post.getRentalStartDate(), post.getRentalEndDate(),
              post.getRentalLocation(), post.getReturnLocation(), post.getStatus(), post.getProviderId(), post.getImageUrl()
        };
        jdbcUtil.setSqlAndParameters(sql, param);
        
        try {
            int result = jdbcUtil.executeUpdate();   // insert 문 실행
             System.out.println("INSERT 실행 결과: " + result); // 디버깅 로그
             return result;
        } catch (SQLException e) {
           jdbcUtil.rollback();
            e.printStackTrace();
        }finally {      
            jdbcUtil.commit();
            jdbcUtil.close();   // resource 반환
        }      
        return 0;
    }

 // 2. 특정 대여글 조회 (ID로)
    public RentalProvidePost getRentalProvidePostById(int id) throws Exception {
        String query = "SELECT * FROM rental_provide_post WHERE id = ?";
        jdbcUtil.setSqlAndParameters(query, new Object[] { id });

        try {
            ResultSet rs = jdbcUtil.executeQuery(); // 쿼리 실행
            if (rs.next()) {
                return mapResultSetToRentalProvidePost(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            jdbcUtil.close(); // resource 반환
        }
        return null;
    }

    // 3. 제목으로 대여글 검색
    public List<RentalProvidePost> searchRentalProvidePostsByTitle(String title) throws Exception {
        String query = "SELECT * FROM rental_provide_post WHERE LOWER(title) LIKE ?";
        jdbcUtil.setSqlAndParameters(query, new Object[] { "%" + title.toLowerCase() + "%" });
        List<RentalProvidePost> posts = new ArrayList<>();

        try {
            ResultSet rs = jdbcUtil.executeQuery(); // 쿼리 실행
            while (rs.next()) {
                posts.add(mapResultSetToRentalProvidePost(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            jdbcUtil.close(); // resource 반환
        }
        return posts;
    }

    // 4. 모든 대여글 조회
    public List<RentalProvidePost> getAllRentalProvidePosts() throws Exception {
        String query = "SELECT * FROM rental_provide_post";
        jdbcUtil.setSqlAndParameters(query, null); // 파라미터 없음
        List<RentalProvidePost> posts = new ArrayList<>();

        try {
            ResultSet rs = jdbcUtil.executeQuery(); // 쿼리 실행
            while (rs.next()) {
                posts.add(mapResultSetToRentalProvidePost(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            jdbcUtil.close(); // resource 반환
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
        post.setReturnLocation(rs.getString("return_location"));
        post.setStatus(rs.getInt("status"));
        post.setProviderId(rs.getInt("provider_id"));
        post.setImageUrl(rs.getString("image_url"));
        return post;
    }
}