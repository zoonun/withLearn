package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * 방 생성 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("ConferenceRegisterPostRequest")
public class ConferenceModiferPostReq {
	@ApiModelProperty(name="conferenceCategoryId", example="1")
	long conferenceCategoryId;
	@ApiModelProperty(name="title", example="방 제목")
	String title;
	@ApiModelProperty(name="description", example="방 설명")
	String description;
	@ApiModelProperty(name="is_active", example="활성, 비활성")
	Boolean is_active;
	@ApiModelProperty(name="is_free", example="유료, 무료")
	Boolean is_free;
	@ApiModelProperty(name="conference_day", example="강의 날짜")
	String conference_day;
	@ApiModelProperty(name="conference_time", example="강의 시간")
	Date conference_time;
	@ApiModelProperty(name="apply_end_time", example="수강 신청 마감 날짜")
	Date apply_end_time;
	@ApiModelProperty(name="apply_start_time", example="수강 신청 시작 날짜")
	Date apply_start_time;
	@ApiModelProperty(name="price", example="가격")
	Integer price;
//	@ApiModelProperty(name="thumbnail", example="썸네일")
//	MultipartFile thumbnail;
}
