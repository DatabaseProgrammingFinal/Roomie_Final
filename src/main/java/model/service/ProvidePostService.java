package model.service;

import model.dao.ProvidePostDAO;
import model.domain.RentalProvidePost;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * RentalProvidePost 비즈니스 로직을 담당하는 서비스 클래스
 */
public class ProvidePostService {
    private ProvidePostDAO providePostDAO;

    /**
     * 생성자: DAO 객체 초기화
     * @param connection 데이터베이스 연결 객체
     */
    public ProvidePostService() {
        this.providePostDAO = new ProvidePostDAO();
    }
    
    /**
     * 대여글 등록
     * @param post 등록할 RentalProvidePost 객체
     * @return 성공 여부
     * @throws Exception 
     * @throws SQLException 데이터베이스 오류
     */
    public int createRentalProvidePost(RentalProvidePost post) throws Exception {
        try {
            return providePostDAO.createRentalProvidePost(post);
        } catch (SQLException e) {
            System.err.println("[ERROR] 대여글 등록 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }



    /**
     * ID로 특정 대여글 조회
     * @param id 조회할 대여글 ID
     * @return RentalProvidePost 객체
     * @throws Exception 
     */
    public RentalProvidePost getRentalProvidePostById(int id) throws Exception {
        try {
            RentalProvidePost post = providePostDAO.getRentalProvidePostById(id);
            if (post == null) {
                throw new SQLException("RentalProvidePost ID " + id + "에 해당하는 데이터가 존재하지 않습니다.");
            }
            return post;
        } catch (SQLException e) {
            System.err.println("[ERROR] 대여글 조회 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }

    /**
     * 제목으로 대여글 검색
     * @param title 검색할 제목
     * @return RentalProvidePost 객체 목록
     * @throws Exception 
     */
    public List<RentalProvidePost> searchRentalProvidePostsByTitle(String title) throws Exception {
        List<RentalProvidePost> posts = providePostDAO.searchRentalProvidePostsByTitle(title);
        if (posts.isEmpty()) {
            System.out.println("[INFO] 검색어 '" + title + "'에 해당하는 대여글이 없습니다.");
        }
        return posts;
    }


    /**
     * 모든 대여글 조회
     * @return RentalProvidePost 객체 목록
     * @throws Exception 
     */
    public List<RentalProvidePost> getAllRentalProvidePosts() throws Exception {
        return providePostDAO.getAllRentalProvidePosts();
    }
}