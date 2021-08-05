package com.ssafy.api.service;

import com.ssafy.db.entity.Review;

import java.util.List;
import java.util.Optional;

/**
 *	리뷰 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface ReviewService {
    Review createReview(Integer rate, String content, String userId, Long conference_id);
    void deleteConference(Long review_id);
    Optional<List<Review>> getReviews(String userId);
}
