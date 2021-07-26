package com.ssafy.api.service;

import com.ssafy.api.request.ConferenceCategoryPostReq;
import com.ssafy.api.request.ConferenceCreaterPostReq;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.ConferenceCategory;
import com.ssafy.db.entity.UserConference;
import com.ssafy.db.repository.ConferenceCategoryRepository;
import com.ssafy.db.repository.ConferenceRepository;
import com.ssafy.db.repository.ConferenceRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *	방 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
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



	@Override
	public Conference createConference(ConferenceCreaterPostReq conferenceCreaterInfo) {
		Conference conference = new Conference();
		ConferenceCategory conferenceCategory = new ConferenceCategory();
		conference.setIs_active(true);
		conference.setDescription(conferenceCreaterInfo.getDescription());
		conference.setTitle(conferenceCreaterInfo.getTitle());
		conferenceCategory.setId(conferenceCreaterInfo.getConferenceCategoryId());
		conference.setConferenceCategory(conferenceCategory);
		return conferenceRepository.save(conference);
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
	public void patchConferenceInfo(ConferenceCreaterPostReq patcherInfo, Long conference_id) {
		Conference conference = new Conference();
		ConferenceCategory conferenceCategory = new ConferenceCategory();
		conference = getConferenceByConferenceId(conference_id);
		conference.setTitle(patcherInfo.getTitle());
		conference.setDescription(patcherInfo.getDescription());
		conferenceCategory.setId(patcherInfo.getConferenceCategoryId());
		conference.setConferenceCategory(conferenceCategory);
		conferenceRepository.save(conference);
	}

	@Override
	public Optional<List<Conference>> getAllConference(String title, String sort, Integer size, Long conferenceCategory) {
		Optional<List<Conference>> conference = conferenceRepositorySupport.findAllConference(title, sort, size, conferenceCategory);
		return conference;
	}

	@Override
	public void createConferenceCategory(ConferenceCategoryPostReq categoryInfo) {
		ConferenceCategory conferenceCategory = new ConferenceCategory();
		System.out.println("serviceImpl"+ categoryInfo.getName());
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
