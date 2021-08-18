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
public class CommunityRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QCommunity qCommunity = QCommunity.community;

    public Optional<List<Community>> findReviewByConferenceCategoryId(Long conferenceCategoryid) {
        JPAQuery<Community> communities  = jpaQueryFactory.select(qCommunity).from(qCommunity);
        // where 절
        if(conferenceCategoryid!=null) communities.where(qCommunity.conferenceCategory.id.eq(conferenceCategoryid));

        if(communities == null) return Optional.empty();
        return Optional.ofNullable(communities.fetch());
    }
}