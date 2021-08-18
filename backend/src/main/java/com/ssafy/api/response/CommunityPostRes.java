package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Community;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 리뷰 생성 API ([POST] /api/v1/reviews) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("CommunityPostRes")
public class CommunityPostRes extends BaseResponseBody{
	@ApiModelProperty(name="글 생성")
	long conferenceId;
	Integer rate;
	String content;
	String userId;

	public static CommunityPostRes of(Integer statusCode, String message, Community community) {
		CommunityPostRes res = new CommunityPostRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		return res;
	}
}