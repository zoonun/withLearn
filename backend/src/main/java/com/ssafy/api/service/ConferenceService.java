package com.ssafy.api.service;

import com.ssafy.api.request.ConferenceCategoryPostReq;
import com.ssafy.api.request.ConferenceCreaterPostReq;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.ConferenceCategory;
import com.ssafy.db.entity.UserConference;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 *	방 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface ConferenceService {
	Conference createConference(ConferenceCreaterPostReq conferenceCreaterInfo) throws IOException;
	Optional<List<ConferenceCategory>> getCategories();
	Conference getConferenceByConferenceId(Long conferenceId);
    void patchConferenceInfo(ConferenceCreaterPostReq patcherInfo, Long conference_id);
	Optional<List<Conference>> getAllConference(String title, String sort, Integer size, Long conferenceCategory);
	void createConferenceCategory(ConferenceCategoryPostReq categoryInfo);
	Optional<ConferenceCategory> getConferenceCategoryByName(String name);
	void deleteConferenceCategory(long categoryId);
	Optional<List<UserConference>> getUserConferenceByConferenceId(Long conference_id);

}
