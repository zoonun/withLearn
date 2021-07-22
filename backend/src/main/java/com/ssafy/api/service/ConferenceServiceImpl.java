package com.ssafy.api.service;

import com.querydsl.core.QueryResults;
import com.ssafy.api.request.ConferenceCreaterPostReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.ConferenceCategory;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ConferenceRepository;
import com.ssafy.db.repository.ConferenceRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
	ConferenceRepositorySupport conferenceRepositorySupport;

	Conference conference = new Conference();
	ConferenceCategory conferenceCategory = new ConferenceCategory();

	@Override
	public Conference createConference(ConferenceCreaterPostReq conferenceCreaterInfo) {
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
}
