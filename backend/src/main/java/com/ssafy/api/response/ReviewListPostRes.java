package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Review;
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
@ApiModel("ReviewListPostRes")
public class ReviewListPostRes extends BaseResponseBody {
    @ApiModelProperty(name = "Review List")
    List<Review> reviewList = new ArrayList<>();

    public static ReviewListPostRes of(Integer statusCode, String message, Optional<List<Review>> reviews) {
        ReviewListPostRes res = new ReviewListPostRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setReviewList(reviews.get());
        return res;
    }
}
