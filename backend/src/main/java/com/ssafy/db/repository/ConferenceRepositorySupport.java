package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQuery;
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

    public Optional<List<ConferenceCategory>> findCategories() {
        List<ConferenceCategory> categoryList = jpaQueryFactory.select(qConferenceCategory).from(qConferenceCategory).fetch();
        return Optional.ofNullable(categoryList);
    }

    public Optional<Conference> findConferenceByConferenceId(Long conferenceId) {
        Conference conference = jpaQueryFactory.select(qConference).from(qConference)
                .where(qConference.id.eq(conferenceId)).fetchOne();
        if(conference == null) return Optional.empty();
        return Optional.ofNullable(conference);
    }

    public Optional<List<Conference>> findAllConference(String title, String sort, Integer size, Long conferenceCategory) {
        JPAQuery<Conference> conferences = jpaQueryFactory.select(qConference).from(qConference);

        // where 절
        if ( title != null && conferenceCategory == null) conferences.where(qConference.title.eq(title));
        else if ( title == null && conferenceCategory != null) conferences.where(qConference.conferenceCategory.id.eq(conferenceCategory));
        else if ( title != null && conferenceCategory != null) conferences.where(qConference.title.eq(title), qConference.conferenceCategory.id.eq(conferenceCategory));

        // 정렬
        if(sort!=null) {
            String[] sortOption = sort.split(",");
            if (sortOption[1].equals("asc") && sortOption[0].equals("title"))
                conferences.orderBy(qConference.title.asc());
            else if (sortOption[1].equals("desc") && sortOption[0].equals("title"))
                conferences.orderBy(qConference.title.desc());
            else if (sortOption[1].equals("asc") && sortOption[0].equals("call_start_time"))
                conferences.orderBy(qConference.callStartTime.asc());
            else if (sortOption[1].equals("desc") && sortOption[0].equals("call_start_time"))
                conferences.orderBy(qConference.callStartTime.desc());
        }
        // size
        if(size != null) conferences.limit(size);

        if(conferences == null) return Optional.empty();
        return Optional.ofNullable(conferences.fetch());
    }

    public Optional<ConferenceCategory> findCategoriesByName(String name) {
        ConferenceCategory conferenceCategory = jpaQueryFactory.select(qConferenceCategory).from(qConferenceCategory)
                .where(qConferenceCategory.name.eq(name)).fetchOne();
        if(conferenceCategory == null) return Optional.empty();
        return Optional.ofNullable(conferenceCategory);
    }
}