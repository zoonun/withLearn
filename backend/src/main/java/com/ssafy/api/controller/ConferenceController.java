package com.ssafy.api.controller;

import com.ssafy.api.request.ConferenceCategoryPostReq;
import com.ssafy.api.request.ConferenceCreaterPostReq;
import com.ssafy.api.response.*;
import com.ssafy.api.service.ConferenceService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.ConferenceCategory;
import com.ssafy.db.entity.UserConference;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * 방 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "방 API", tags = {"Conference"})
@RestController
@RequestMapping("/api/v1/")
public class ConferenceController {
    @Autowired
    ConferenceService conferenceService;

    @PostMapping("conferences")
    @ApiOperation(value = "방 생성", notes = "방을 생성 한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
    })
    public ResponseEntity<ConferenceCreatePostRes> createConference(
            @RequestBody @ApiParam(value = "방 정보", required = true) ConferenceCreaterPostReq createrInfo) {
        Conference conference = conferenceService.createConference(createrInfo);
        return ResponseEntity.status(201).body(ConferenceCreatePostRes.of(201,"success.",conference));
    }

    @GetMapping("conference-categories")
    @ApiOperation(value = "방 카테고리 조회", notes = "방 카테고리듫을 조회한다")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
    })
    public ResponseEntity<ConferenceCategoryRes> getCategories() {
        Optional<List<ConferenceCategory>> categories= conferenceService.getCategories();
        return ResponseEntity.status(201).body(ConferenceCategoryRes.of(categories));
    }

    @GetMapping(value = "conferences/{conference_id}")
    @ApiOperation(value = "방 상세정보 조회", notes = "방 ID를 가지고 상세 정보를 조회한다")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
    })
    public ResponseEntity<ConferenceDetailRes> getConferenceDetail(
            @PathVariable Long conference_id) {
        Conference conference= conferenceService.getConferenceByConferenceId(conference_id);
        Optional<List<UserConference>> userConference = conferenceService.getUserConferenceByConferenceId(conference_id);
        return ResponseEntity.status(201).body(ConferenceDetailRes.of(conference, userConference));
    }

    @PatchMapping(value = "conferences/{conference_id}")
    @ApiOperation(value = "방 정보 수정", notes = "방 ID를 가지고 정보를 수정한다")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
    })
    public ResponseEntity<? extends BaseResponseBody> patchConferenceInfo(
            @PathVariable Long conference_id, @RequestBody @ApiParam(value = "방 정보", required = true) ConferenceCreaterPostReq patcherInfo) {
        conferenceService.patchConferenceInfo(patcherInfo, conference_id);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping(value = "conferences")
    @ApiOperation(value = "방 목록 조회", notes = "방 목록 리스트를 검색한다")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
    })
    public ResponseEntity<ConferenceListPostRes> getConferenceList(
            @RequestParam(required = false) String title, @RequestParam(required = false) @ApiParam(value = "call_start_time,asc") String sort,
            @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size, @RequestParam(required = false) Long conferenceCategory) {
        Optional<List<Conference>> conferences= conferenceService.getAllConference(title,sort,size,conferenceCategory);
        return ResponseEntity.status(200).body(ConferenceListPostRes.of(conferences));
    }

    @PostMapping(value = "conference-categories")
    @ApiOperation(value = "방 카테고리 생성", notes = "방 카테고리를 생성한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 409, message = "카테고리 중복"),
    })
    public ResponseEntity<? extends BaseResponseBody> createConferenceCategory(
            @RequestBody @ApiParam(value = "카테고리 정보", required = true) ConferenceCategoryPostReq categoryInfo) {
        Optional<ConferenceCategory> conferenceCategory = conferenceService.getConferenceCategoryByName(categoryInfo.getName());
        if(conferenceCategory.isPresent()){
            return ResponseEntity.status(409).body(BaseResponseBody.of(409, "duplicate category"));
        }else{
            conferenceService.createConferenceCategory(categoryInfo);
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        }
    }

    @DeleteMapping(value = "conference-categories")
    @ApiOperation(value = "방 카테고리 삭제", notes = "방 카테고리를 삭제한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "사용자 없음"),
    })
    public ResponseEntity<? extends BaseResponseBody> deleteConferenceCategory(
            @RequestBody @ApiParam(value = "카테고리 정보", required = true) ConferenceCategoryPostReq categoryInfo) {
        Optional<ConferenceCategory> conferenceCategory = conferenceService.getConferenceCategoryByName(categoryInfo.getName());
        if(conferenceCategory.isPresent()){
            conferenceService.deleteConferenceCategory(conferenceCategory.get().getId());
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        }else{
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "non-existent category"));
        }
    }
}