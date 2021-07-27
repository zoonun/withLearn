package com.ssafy.api.response;

import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.ConferenceCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("ConferenceListPostRes")
public class ConferenceListPostRes {
	@ApiModelProperty(name="Conference List")
	Optional<List<Conference>> conferenceList;
	
	public static ConferenceListPostRes of(Optional<List<Conference>> conferences) {
		ConferenceListPostRes res = new ConferenceListPostRes();
		res.setConferenceList(conferences);
		return res;
	}
}
