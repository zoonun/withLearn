package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Conference;
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
@ApiModel("ConferenceOnboardStatusRes")
public class ConferenceOnboardStatusRes extends BaseResponseBody {
	@ApiModelProperty(name="Conference List")
	boolean isActive;
	Long conferenceId;
	
	public static ConferenceOnboardStatusRes of(Integer statusCode, String message, Conference conference) {
		ConferenceOnboardStatusRes res = new ConferenceOnboardStatusRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setActive(conference.getIs_active());
		res.setConferenceId(conference.getId());
		return res;
	}
}
