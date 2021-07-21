package com.ssafy.db.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 방 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class ConferenceRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QConference qConference = QConference.conference;
    QConferenceCategory qConferenceCategory = QConferenceCategory.conferenceCategory;

    public Optional<QueryResults<ConferenceCategory>> findCategories() {
        QueryResults<ConferenceCategory> categoryList = jpaQueryFactory.select(qConferenceCategory).from(qConferenceCategory).fetchResults();
        System.out.println(categoryList);
        if(categoryList == null) return Optional.empty();
        return Optional.ofNullable(categoryList);
    }
}
