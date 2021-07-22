package com.ssafy.api.service;

import com.ssafy.api.request.ConferenceCreaterPostReq;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.ConferenceCategory;

import java.util.List;
import java.util.Optional;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface ConferenceService {
	Conference createConference(ConferenceCreaterPostReq conferenceCreaterInfo);
	Optional<List<ConferenceCategory>> getCategories();
	Conference getConferenceByConferenceId(Long conferenceId);
//	void deleteConferenceByUserId(String userId);
//	void patchUser(UserRegisterPostReq registerInfo, String userId);
//  Boolean getConferenceCategories(String userId);
}
