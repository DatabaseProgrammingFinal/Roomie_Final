package model.dao;

import model.domain.RentalRequestPost;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestPostDAO {
private JDBCUtil jdbcUtil = null;
   
   public RequestPostDAO() {         
        jdbcUtil = new JDBCUtil();   // JDBCUtil 객체 생성
    }

    // 1. 대여 요청글 등록
    public int createRentalRequestPost(RentalRequestPost post) throws Exception {
        String sql = "INSERT INTO rental_request_post (id, title, rental_item, content, points, rental_start_date, rental_end_date, rental_location, return_location, status, requester_id)" +
            "VALUES (rental_request_post_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] param = new Object[] {
              post.getTitle(), post.getRentalItem(),
              post.getContent(), post.getPoints(), post.getRentalStartDate(), post.getRentalEndDate(),
              post.getRentalLocation(), post.getReturnLocation(), post.getStatus(), post.getRequesterId()
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


    // 2. 대여 요청글 조회 (ID로)
    public RentalRequestPost getRentalRequestPostById(int id) throws Exception {
        String sql = "SELECT * FROM rental_request_post WHERE id = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] { id });

        try {
            ResultSet rs = jdbcUtil.executeQuery(); // 쿼리 실행
            if (rs.next()) {
                return mapToRentalRequestPost(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            jdbcUtil.close(); // resource 반환
        }
        return null;
    }

    // 3. 제목으로 대여 요청글 검색
    public List<RentalRequestPost> searchRentalRequestPostsByTitle(String title) throws Exception {
        String sql = "SELECT * FROM rental_request_post WHERE LOWER(title) LIKE ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] { "%" + title.toLowerCase() + "%" });
        List<RentalRequestPost> posts = new ArrayList<>();

        try {
            ResultSet rs = jdbcUtil.executeQuery(); // 쿼리 실행
            while (rs.next()) {
                posts.add(mapToRentalRequestPost(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            jdbcUtil.close(); // resource 반환
        }
        return posts;
    }

    // 4. 모든 대여 요청글 조회
    public List<RentalRequestPost> getAllRentalRequestPosts() throws Exception {
        String query = "SELECT * FROM rental_request_post";
        jdbcUtil.setSqlAndParameters(query, null); // 파라미터 없음
        List<RentalRequestPost> posts = new ArrayList<>();

        try {
            ResultSet rs = jdbcUtil.executeQuery(); // 쿼리 실행
            while (rs.next()) {
                posts.add(mapToRentalRequestPost(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            jdbcUtil.close(); // resource 반환
        }
        return posts;
    }
    
 // ResultSet을 RentalRequestPost 객체로 매핑하는 메서드
    private RentalRequestPost mapToRentalRequestPost(ResultSet rs) throws SQLException {
        RentalRequestPost post = new RentalRequestPost();
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
        post.setRequesterId(rs.getInt("requester_id"));
        return post;
    }

}
