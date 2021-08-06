package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 리뷰 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class ReviewRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QReview qReview = QReview.review;

    public Optional<List<Review>> findReviewByUserId(String userId) {
        JPAQuery<Review> reviews = jpaQueryFactory.select(qReview).from(qReview);

        // where 절
        if(userId!=null) reviews.where(qReview.user.userId.eq(userId));

        if(reviews == null) return Optional.empty();
        return Optional.ofNullable(reviews.fetch());
    }
}