package com.ssafy.api.service;

import com.ssafy.api.request.ConferenceCategoryPostReq;
import com.ssafy.api.request.ConferenceModiferPostReq;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.ConferenceCategory;
import com.ssafy.db.entity.UserConference;
import com.ssafy.db.repository.ConferenceCategoryRepository;
import com.ssafy.db.repository.ConferenceRepository;
import com.ssafy.db.repository.ConferenceRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
    ConferenceCategoryRepository conferenceCategoryRepository;

    @Autowired
    ConferenceRepositorySupport conferenceRepositorySupport;

    public Conference setConference(Conference conference, ConferenceCategory conferenceCategory, String description, String title, String thumbnail, String conferenceDay, Date conferenceTime, Date applyEndTime, Date applyStartTime, Integer price) {
        conference.setTitle(title);
        conference.setDescription(description);
        conference.setConferenceCategory(conferenceCategory);
        conference.setConference_day(conferenceDay);
        conference.setConference_time(conferenceTime);
        conference.setApply_start_time(applyStartTime);
        conference.setApply_end_time(applyEndTime);
        conference.setPrice(price);
        conference.setThumbnail(thumbnail);
        return conference;
    }

    @Override
    public void deleteConference(Long conference_id) {
        conferenceRepository.deleteById(conference_id);
    }

    @Override
    public Conference createConference(String description, String title, Long conferenceCategoryId, String thumbnail, String conferenceDay, Date conferenceTime, Date applyEndTime, Date applyStartTime, Integer price) {
        Conference conference = new Conference();
        ConferenceCategory conferenceCategory = conferenceCategoryRepository.findById(conferenceCategoryId).get();
        conference = setConference(conference, conferenceCategory, description, title, thumbnail, conferenceDay, conferenceTime, applyEndTime, applyStartTime, price);
        conference.setIs_active(false);
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
    public Optional<List<Conference>> getConferences(String title, String sort, Integer size, Long conferenceCategory, String userName) {
        Optional<List<Conference>> conference = conferenceRepositorySupport.findConferences(title, sort, size, conferenceCategory, userName);
        return conference;
    }

    @Override
    public void createConferenceCategory(ConferenceCategoryPostReq categoryInfo) {
        ConferenceCategory conferenceCategory = new ConferenceCategory();
        System.out.println("serviceImpl" + categoryInfo.getName());
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
