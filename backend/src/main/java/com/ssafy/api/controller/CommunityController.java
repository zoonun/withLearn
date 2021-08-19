package com.ssafy.api.controller;

import com.ssafy.api.response.CommentListPostRes;
import com.ssafy.api.response.CommunityDetailRes;
import com.ssafy.api.response.CommunityListPostRes;
import com.ssafy.api.service.ConferenceService;
import com.ssafy.api.service.CommunityService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Comment;
import com.ssafy.db.entity.Community;
import com.ssafy.db.repository.CommunityRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;

/**
 * 리뷰 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "커뮤니티 API", tags = {"Community"})
@Controller
@RequestMapping("/api/v1/")
public class CommunityController {
    @Autowired
    ConferenceService conferenceService;

    @Autowired
    CommunityService communityService;

    @Autowired
    CommunityRepository communityRepository;

    @PostMapping("community")
    @ApiOperation(value = "커뮤니티 글 달기", notes = "글을 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
    })
    public ResponseEntity<? extends BaseResponseBody> creatCommunityPost(
            @RequestParam("descript") String descript, @RequestParam("conferenceCategoryid") Long conferenceCategoryid,
            @RequestParam("title") String title, @ApiIgnore Authentication authentication) {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String userId = userDetails.getUsername();

        communityService.createPost(descript, title, userId, conferenceCategoryid);
        return ResponseEntity.status(201).body(BaseResponseBody.of(201, "success."));
    }

    @GetMapping("community")
    @ApiOperation(value = "커뮤니티 글 보기", notes = "글을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<CommunityListPostRes> getCommunityPosts(
            @RequestParam(required = false) Long conferenceCategoryid) {

        Optional<List<Community>> communities = communityService.getPosts(conferenceCategoryid);
        return ResponseEntity.status(201).body(CommunityListPostRes.of(201, "success.", communities));
    }

    @GetMapping("community/detail")
    @ApiOperation(value = "커뮤니티 글 상세보기", notes = "글을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<CommunityDetailRes> getCommunityPostsDetail(
            @RequestParam(required = false) Long communutyId) {

        Community community = communityRepository.findById(communutyId).get();
        return ResponseEntity.status(201).body(CommunityDetailRes.of(201, "success.", community));
    }

    @PostMapping("comment")
    @ApiOperation(value = "커뮤니티 댓글 달기", notes = "댓글을 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
    })
    public ResponseEntity<? extends BaseResponseBody> creatCommunityPost(
            @RequestParam("descript") String descript, @RequestParam("communityId") Long communityId, @ApiIgnore Authentication authentication) {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String userId = userDetails.getUsername();

        communityService.createComment(descript, userId, communityId);
        return ResponseEntity.status(201).body(BaseResponseBody.of(201, "success."));
    }

    @GetMapping("comment")
    @ApiOperation(value = "커뮤니티 댓글 보기", notes = "댓글을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<CommentListPostRes> getComments(@RequestParam(required = true) Long communityId) {

        Optional<List<Comment>> coments = communityService.getComments(communityId);
        return ResponseEntity.status(201).body(CommentListPostRes.of(201, "success.", coments));
    }

}