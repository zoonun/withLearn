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
@ApiModel("CommunityListPostRes")
public class CommunityListPostRes extends BaseResponseBody {
    @ApiModelProperty(name = "Review List")
    List<Community> communityList = new ArrayList<>();

    public static CommunityListPostRes of(Integer statusCode, String message, Optional<List<Community>> communities) {
        CommunityListPostRes res = new CommunityListPostRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
//        res.setCommunityList(reviews.get());
        return res;
    }
}
