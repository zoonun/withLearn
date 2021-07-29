package com.ssafy.api.service;

import com.ssafy.api.request.ConferenceCategoryPostReq;
import com.ssafy.api.request.ConferenceModiferPostReq;
import com.ssafy.db.entity.Chatting;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.ConferenceCategory;
import com.ssafy.db.entity.UserConference;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 채팅 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("ChattingService")
public class ChattingServiceImpl implements ChattingService {
    @Autowired
    ChattingRepository chattingRepository;

    @Autowired
    ConferenceRepository conferenceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChattingService chattingService;

    @Override
    public void saveChatting(Chatting message) {
        Chatting chat = new Chatting();
        chat.setContents(message.getContents());
//        chat.setConference(conferenceRepository.findConferenceById(message.getConference().getId()));
//        chat.setUser(userRepository.findById());
        chattingRepository.save(message);
    }
}