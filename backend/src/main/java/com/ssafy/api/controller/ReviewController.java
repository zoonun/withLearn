package com.ssafy.api.controller;

import com.ssafy.api.response.ConferenceCreatePostRes;
import com.ssafy.api.response.ConferenceDetailRes;
import com.ssafy.api.response.ReviewCreatePostRes;
import com.ssafy.api.response.ReviewListPostRes;
import com.ssafy.api.service.ConferenceService;
import com.ssafy.api.service.ReviewService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.Review;
import com.ssafy.db.entity.UserConference;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * 리뷰 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "리뷰 API", tags = {"Review"})
@Controller
@RequestMapping("/api/v1/")
public class ReviewController {
    @Autowired
    ConferenceService conferenceService;

    @Autowired
    ReviewService reviewService;

    @PostMapping("reviews")
    @ApiOperation(value = "리뷰 달기", notes = "리뷰를 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
    })
    public ResponseEntity<ReviewCreatePostRes> createReview(
            @RequestParam("content") String content, @RequestParam("conferenceId") Long conference_id,
            @RequestParam("rate") Integer rate, @ApiIgnore Authentication authentication) {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String userId = userDetails.getUsername();
        Review review = reviewService.createReview(rate, content, userId, conference_id);
        return ResponseEntity.status(201).body(ReviewCreatePostRes.of(201, "success.", review));
    }

    @GetMapping(value = "reviews/")
    @ApiOperation(value = "리뷰 조회", notes = "리뷰를 조회한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<ReviewListPostRes> getReviews(
            @RequestParam(required = false) String userId) {
        Optional<List<Review>> reviews = reviewService.getReviews(userId);
        return ResponseEntity.status(200).body(ReviewListPostRes.of(200, "success.", reviews));
    }

    @DeleteMapping(value = "reviews")
    @ApiOperation(value = "리뷰 삭제", notes = "리뷰 정보를 삭제한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<? extends BaseResponseBody> getConferenceList(
            @RequestParam Long review_id) {
        reviewService.deleteConference(review_id);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }
}