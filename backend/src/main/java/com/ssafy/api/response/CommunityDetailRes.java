package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Community;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 리뷰 리스트 조회 API ([Get] /api/v1/reviews) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("CommunityDetailRes")
public class CommunityDetailRes extends BaseResponseBody {
    @ApiModelProperty(name = "Community Detail")
    Community communitydetail = new Community();

    public static CommunityDetailRes of(Integer statusCode, String message, Community communitydetail) {
        CommunityDetailRes res = new CommunityDetailRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setCommunitydetail(communitydetail);
        return res;
    }
}
