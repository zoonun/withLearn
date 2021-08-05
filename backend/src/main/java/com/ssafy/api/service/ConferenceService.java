package com.ssafy.api.service;

import com.ssafy.api.request.ConferenceCategoryPostReq;
import com.ssafy.api.request.ConferenceModiferPostReq;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.ConferenceCategory;
import com.ssafy.db.entity.UserConference;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *   방 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface ConferenceService {
	Optional<List<ConferenceCategory>> getCategories();
	Conference getConferenceByConferenceId(Long conferenceId);
	Optional<List<Conference>> getAllConference(String title, String sort, Integer size, Long conferenceCategory);
	void createConferenceCategory(ConferenceCategoryPostReq categoryInfo);
	Optional<ConferenceCategory> getConferenceCategoryByName(String name);
	void deleteConferenceCategory(long categoryId);
	Optional<List<UserConference>> getUserConferenceByConferenceId(Long conference_id);
    Conference createConference(String description, String title, Long conferenceCategory_id, String thumbnail, String conferenceDay, Date conferenceTime, Date applyEndTime, Date applyStartTime, Integer price);
	void patchConferenceInfo(String description, String title, Long conferenceCategoryId, String thumbnail, String conferenceDay, Date conferenceTime, Date applyEndTime, Date applyStartTime, Boolean isActive, Integer price, Long conference_id);
	Conference setConference(Conference conference, ConferenceCategory conferenceCategory, String description, String title, String thumbnail, String conferenceDay, Date conferenceTime, Date applyEndTime, Date applyStartTime, Integer price);
	void deleteConference(Long conference_id);
}
