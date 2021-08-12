package com.ssafy.api.service;

import com.ssafy.db.entity.Kurento;
import com.ssafy.db.entity.Review;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * kurento 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("KurentoService")
public class KurentoServiceImpl implements KurentoService {
    @Autowired
    KurentoService kurentoService;

    @Autowired
    UserService userService;

    @Autowired
    KurentoRepository kurentoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    KurentoRepositorySupport kurentoRepositorySupport;

    @Override
    public Kurento registImage(String imageUrl, String imageName, String userId) {
        Kurento kurento = new Kurento();
        Optional<User> user = userRepository.findByUserId(userId);
        kurento.setImagePath(imageUrl);
        kurento.setImageName(imageName);
        kurento.setUser(user.get());
        return kurentoRepository.save(kurento);
    }

    @Override
    public List<Kurento> getImages(String userId) {
        Optional<List<Kurento>> images = kurentoRepositorySupport.findImagesByUserId(userId);
        return images.get();
    }

    @Override
    public void deleteImage(String userId, String imageName) {
        kurentoRepository.delete(kurentoRepositorySupport.deleteImage(userId, imageName));
    }
}
