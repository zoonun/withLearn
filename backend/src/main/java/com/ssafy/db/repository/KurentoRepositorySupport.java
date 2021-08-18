package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * kurento 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class KurentoRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QKurento qKurento = QKurento.kurento;

    public Optional<List<Kurento>> findImagesByUserId(String userId) {
        List<Kurento> kurentos = jpaQueryFactory.select(qKurento).from(qKurento)
                .where(qKurento.user.userId.eq(userId)).fetch();
        if(kurentos == null) return Optional.empty();
        return Optional.ofNullable(kurentos);
    }

    public Kurento deleteImage(String userId, String imageName) {
        return jpaQueryFactory.select(qKurento).from(qKurento).where(qKurento.user.userId.eq(userId), qKurento.imageName.eq(imageName)).fetchOne();
    }
}