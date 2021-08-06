package com.ssafy.api.service;

import com.ssafy.api.request.ConferenceCategoryPostReq;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.ConferenceCategory;
import com.ssafy.db.entity.Review;
import com.ssafy.db.entity.UserConference;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 리뷰 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("ReviewService")
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewService reviewService;

    @Autowired
    UserService userService;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ConferenceRepository conferenceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewRepositorySupport reviewRepositorySupport;

    @Override
    public Review createReview(Integer rate, String content, String userId, Long conference_id) {
        Review review = new Review();
        review.setConference(conferenceRepository.findById(conference_id).get());
        review.setContent(content);
        review.setRate(rate);
        review.setUser(userService.getUserByUserId(userId));
        return reviewRepository.save(review);
    }

    @Override
    public void deleteConference(Long review_id) {
        reviewRepository.deleteById(review_id);
    }

    @Override
    public Optional<List<Review>> getReviews(String userId) {
        Optional<List<Review>> reviews = reviewRepositorySupport.findReviewByUserId(userId);
        return reviews;
    }
}
