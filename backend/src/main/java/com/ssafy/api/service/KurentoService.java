package com.ssafy.api.service;

import com.ssafy.db.entity.Chatting;
import com.ssafy.db.entity.Kurento;

import java.util.List;

/**
 *	화상 통화 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface KurentoService {
    Kurento registImage(String imageUrl, String imageName, String userId);
    List<Kurento> getImages(String userId);
    void deleteImage(String userId, String imageName);
}
