package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Review;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 리뷰 생성 API ([POST] /api/v1/reviews) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("ReviewCreatePostRes")
public class ReviewCreatePostRes extends BaseResponseBody{
	@ApiModelProperty(name="리뷰 생성")
	long conferenceId;
	Integer rate;
	String content;
	String userId;

	public static ReviewCreatePostRes of(Integer statusCode, String message, Review review) {
		ReviewCreatePostRes res = new ReviewCreatePostRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setRate(review.getRate());
		res.setContent(review.getContent());
		res.setUserId(review.getUser().getUserId());
		res.setConferenceId(review.getConference().getId());
		return res;
	}
}