package com.ssafy.api.response;

import com.querydsl.core.QueryResults;
import com.ssafy.db.entity.ConferenceCategory;
import com.ssafy.db.entity.User;
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
@ApiModel("ConferenceCategoryRes")
public class ConferenceCategoryRes {
	@ApiModelProperty(name="User ID")
	Optional<List<ConferenceCategory>> categoryList;
	
	public static ConferenceCategoryRes of(Optional<List<ConferenceCategory>> categories) {
		ConferenceCategoryRes res = new ConferenceCategoryRes();
		res.setCategoryList(categories);
		return res;
	}
}
