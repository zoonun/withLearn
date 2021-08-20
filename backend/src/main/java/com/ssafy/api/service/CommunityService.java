package com.ssafy.api.service;

import com.ssafy.db.entity.Comment;
import com.ssafy.db.entity.Community;

import java.util.List;
import java.util.Optional;

/**
 *	리뷰 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface CommunityService {
    Community createPost(String descript, String title, String userId, Long conferenceCategoryid);
    Optional<List<Community>> getPosts(Long conferenceCategoryid);

    Optional<List<Comment>> getComments(Long communityId);

    Comment createComment(String descript, String userId, Long communityId);
}
