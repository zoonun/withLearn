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
    QComment qComment = QComment.comment;


    public Optional<List<Community>> findPostByConferenceCategoryId(Long conferenceCategoryid) {
        JPAQuery<Community> communities  = jpaQueryFactory.select(qCommunity).from(qCommunity);
        if(conferenceCategoryid!=null) communities.where(qCommunity.conferenceCategory.id.eq(conferenceCategoryid));

        if(communities == null) return Optional.empty();
        return Optional.ofNullable(communities.fetch());
    }

    public Optional<List<Comment>> findCommentsByConferenceCategoryId(Long communityId) {
        JPAQuery<Comment> Comments  = jpaQueryFactory.select(qComment).from(qComment).where(qComment.community.id.eq(communityId));

        if(Comments == null) return Optional.empty();
        return Optional.ofNullable(Comments.fetch());
    }
}