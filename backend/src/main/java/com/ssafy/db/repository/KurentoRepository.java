package com.ssafy.db.repository;

import com.ssafy.db.entity.Chatting;
import com.ssafy.db.entity.Kurento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 화상 통화 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface KurentoRepository extends JpaRepository<Kurento, Long> {

}