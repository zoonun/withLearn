package com.ssafy.api.service;

import com.ssafy.api.request.ConferenceCategoryPostReq;
import com.ssafy.api.request.ConferenceCreaterPostReq;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.ConferenceCategory;
import com.ssafy.db.entity.UserConference;
import com.ssafy.db.repository.ConferenceCategoryRepository;
import com.ssafy.db.repository.ConferenceRepository;
import com.ssafy.db.repository.ConferenceRepositorySupport;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
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
		ConferenceCategory conferenceCategory = conferenceCategoryRepository.findById(conferenceCreaterInfo.getConferenceCategoryId()).get();
		conference.setIs_active(true);
		conference.setDescription(conferenceCreaterInfo.getDescription());
		conference.setTitle(conferenceCreaterInfo.getTitle());
		conference.setConferenceCategory(conferenceCategory);

// 파일 이름을 업로드 한 날짜로 바꾸어서 저장할 것이다
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String current_date = simpleDateFormat.format(new Date());

		System.out.println(1);

		// 프로젝트 폴더에 저장하기 위해 절대경로를 설정 (Window 의 Tomcat 은 Temp 파일을 이용한다)
		String absolutePath = new File("").getAbsolutePath() + "\\";



		// 경로를 지정하고 그곳에다가 저장할 심산이다
		String path = "images/" + current_date;
		File file = new File(path);
		// 저장할 위치의 디렉토리가 존지하지 않을 경우
		System.out.println(2);
		if(!file.exists())
			// mkdir() 함수와 다른 점은 상위 디렉토리가 존재하지 않을 때 그것까지 생성
			file.mkdirs();
		String fileName = conferenceCreaterInfo.getThumbnail().getName();
		// 파일이 비어 있지 않을 때 작업을 시작해야 오류가 나지 않는다
		System.out.println(3);
		if(conferenceCreaterInfo.getThumbnail()!=null){
			// jpeg, png, gif 파일들만 받아서 처리할 예정
			String originalFileExtension = fileName.substring(fileName.lastIndexOf("."));

			String new_file_name = Long.toString(System.nanoTime()) + originalFileExtension;

			// 저장된 파일로 변경하여 이를 보여주기 위함
			file = new File(absolutePath + path + "/" + new_file_name);
			conference.setThumbnail(new_file_name);
		}

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
