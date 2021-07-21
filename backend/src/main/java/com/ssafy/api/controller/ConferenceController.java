package com.ssafy.api.controller;

import com.querydsl.core.QueryResults;
import com.ssafy.api.request.ConferenceCreaterPostReq;
import com.ssafy.api.response.ConferenceCategoryRes;
import com.ssafy.api.response.ConferenceCreatePostRes;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.response.UserRes;
import com.ssafy.api.service.ConferenceService;
import com.ssafy.api.service.ConferenceService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.ConferenceCategory;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * 방 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "방 API", tags = {"Conference"})
@RestController
@RequestMapping("/api/v1/conference")
public class ConferenceController {
    @Autowired
    ConferenceService conferenceService;

    @PostMapping()
    @ApiOperation(value = "방 생성", notes = "방을 생성 한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
    })
    public ResponseEntity<ConferenceCreatePostRes> createConference(
            @RequestBody @ApiParam(value = "방 정보", required = true) ConferenceCreaterPostReq createrInfo) {
        Conference conference = conferenceService.createConference(createrInfo);

        return ResponseEntity.status(201).body(ConferenceCreatePostRes.of(201,"success.",conference));
    }

    @GetMapping()
    @ApiOperation(value = "방 카테고리 조회", notes = "방 카테고리듫을 조회한다")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
    })
    public ResponseEntity<ConferenceCategoryRes> getCategories( ConferenceCreaterPostReq createrInfo) {
        Optional<QueryResults<ConferenceCategory>> categories= conferenceService.getCategories();
//        List<ConferenceCategory> categories= conferenceService.fi

        return ResponseEntity.status(201).body(ConferenceCategoryRes.of(categories));
    }
}
