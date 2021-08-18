package com.ssafy.api.service;

import com.ssafy.api.request.ConferenceCategoryPostReq;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.ConferenceCategory;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserConference;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 방 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("ConferenceService")
public class ConferenceServiceImpl implements ConferenceService {
    @Autowired
    ConferenceRepository conferenceRepository;

    @Autowired
    ConferenceService conferenceService;

    @Autowired
    UserConferenceRepository userConferenceRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ConferenceCategoryRepository conferenceCategoryRepository;

    @Autowired
    ConferenceRepositorySupport conferenceRepositorySupport;

    public Conference setConference(Conference conference, ConferenceCategory conferenceCategory, String description, String title, String thumbnail, String conferenceDay, Date conferenceTime, Date applyEndTime, Date applyStartTime, Integer price) {
        conference.setTitle(title);
        conference.setDescription(description);
        conference.setConferenceCategory(conferenceCategory);
        conference.setConference_day(conferenceDay);
        conference.setConference_time(conferenceTime);
        conference.setPrice(price);
        conference.setThumbnail(thumbnail);
        return conference;
    }

    @Override
    public void deleteConference(Long conference_id) {
        conferenceRepository.deleteById(conference_id);
    }

    @Override
    public void joinConference(String userId, Long conferenceId) {
        UserConference userConference = new UserConference();
        userConference.setUser(userService.getUserByUserId(userId));
        userConference.setConference(conferenceRepository.findById(conferenceId).get());
        userConferenceRepository.save(userConference);
    }

    @Override
    public Conference changeOnboardStates(Long conferenceId) {
        Conference conference = conferenceRepository.findById(conferenceId).get();
        if(conference.getIs_active() == true) conference.setIs_active(false);
        else conference.setIs_active(true);
        return conferenceRepository.save(conference);
    }

    @Override
    public Conference createConference(String userId, String description, String title, Long conferenceCategoryId, String thumbnail, String conferenceDay, Date conferenceTime, Date applyEndTime, Date applyStartTime, Integer price) {
        Conference conference = new Conference();
        User user = userService.getUserByUserId(userId);
        ConferenceCategory conferenceCategory = conferenceCategoryRepository.findById(conferenceCategoryId).get();
        conference = setConference(conference, conferenceCategory, description, title, thumbnail, conferenceDay, conferenceTime, applyEndTime, applyStartTime, price);
        conference.setIs_active(false);
        conference.setUser(user);
        return conferenceRepository.save(conference);
    }

    @Override
    public void patchConferenceInfo(String description, String title, Long conferenceCategoryId, String thumbnail, String conferenceDay, Date conferenceTime, Date applyEndTime, Date applyStartTime, Boolean isActive, Integer price, Long conference_id) {
        Conference conference = conferenceRepositorySupport.findConferenceByConferenceId(conference_id).get();
        ConferenceCategory conferenceCategory = conferenceCategoryRepository.findById(conferenceCategoryId).get();
        conference = setConference(conference, conferenceCategory, description, title, thumbnail, conferenceDay, conferenceTime, applyEndTime, applyStartTime, price);
        conferenceRepository.save(conference);
    }

    @Override
    public Optional<List<ConferenceCategory>> getCategories() {
        Optional<List<ConferenceCategory>> categories = conferenceRepositorySupport.findCategories();
        return categories;
    }

    @Override
    public Conference getConferenceByConferenceId(Long conferenceId) {
        Conference conference = conferenceRepositorySupport.findConferenceByConferenceId(conferenceId).get();
        return conference;
    }

    @Override
    public Optional<List<Conference>> getConferences(String title, String sort, Integer size, String conferenceCategory, String userName) {
        Optional<List<Conference>> conference = conferenceRepositorySupport.findConferences(title, sort, size, conferenceCategory, userName);
        return conference;
    }

    @Override
    public void createConferenceCategory(ConferenceCategoryPostReq categoryInfo) {
        ConferenceCategory conferenceCategory = new ConferenceCategory();
        conferenceCategory.setName(categoryInfo.getName());
        conferenceCategoryRepository.save(conferenceCategory);
    }

    @Override
    public Optional<ConferenceCategory> getConferenceCategoryByName(String name) {
        Optional<ConferenceCategory> conferenceCategories = conferenceRepositorySupport.findCategoriesByName(name);
        return conferenceCategories;
    }

    @Override
    public void deleteConferenceCategory(long categoryId) {
        conferenceCategoryRepository.deleteById(categoryId);
    }

    @Override
    public Optional<List<UserConference>> getUserConferenceByConferenceId(Long conference_id) {
        Optional<List<UserConference>> userConferences = conferenceRepositorySupport.findUserConferenceByConferenceId(conference_id);
        return userConferences;
    }
}
