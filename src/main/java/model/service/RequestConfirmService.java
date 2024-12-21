package model.service;

import model.dao.RequestPostDAO;
import model.domain.RentalProvidePost;
import model.domain.RentalRequestPost;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * RentalRequestPost 비즈니스 로직을 담당하는 서비스 클래스
 */
public class RequestConfirmService {
    private RequestPostDAO requestPostDAO;

    /**
     * 생성자: DAO 객체 초기화
     * @param connection 데이터베이스 연결 객체
     */
    public RequestConfirmService() {
        this.requestPostDAO = new RequestPostDAO();
    }

    /**
     * 대여요청글 등록
     * @param post 등록할 RentalRequestPost 객체
     * @return 성공 여부
     * @throws SQLException 데이터베이스 오류
     */
    public int createRentalRequestPost(RentalRequestPost post) throws Exception {
        try {
            return requestPostDAO.createRentalRequestPost(post);
        } catch (SQLException e) {
            System.err.println("[ERROR] 대여글 등록 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }

    /**
     * ID로 특정 대여요청글 조회
     * @param id 조회할 대여요청글 ID
     * @return RentalRequestPost 객체
     * @throws SQLException 데이터베이스 오류
     */
    public RentalRequestPost getRentalRequestPostById(int id)  throws Exception {
        try {
            RentalRequestPost post = requestPostDAO.getRentalRequestPostById(id);
            if (post == null) {
                throw new SQLException("RentalRequestPost ID " + id + "에 해당하는 데이터가 존재하지 않습니다.");
            }
            return post;
        } catch (SQLException e) {
            System.err.println("[ERROR] 대여글 조회 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }

    /**
     * 제목으로 대여요청글 검색
     * @param title 검색할 제목
     * @return RentalRequestPost 객체 목록
     * @throws SQLException 데이터베이스 오류
     */
    public List<RentalRequestPost> searchRentalRequestPostsByTitle(String title) throws Exception {
        List<RentalRequestPost> posts = requestPostDAO.searchRentalRequestPostsByTitle(title);
        if (posts.isEmpty()) {
            System.out.println("[INFO] 검색어 '" + title + "'에 해당하는 대여글이 없습니다.");
        }
        return posts;
    }

    /**
     * 모든 대여요청글 조회
     * @return RentalRequestPost 객체 목록
     * @throws SQLException 데이터베이스 오류
     */
    public List<RentalRequestPost> getAllRentalRequestPosts() throws Exception {
        return requestPostDAO.getAllRentalRequestPosts();
    }
}