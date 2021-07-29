package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * 방 생성 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("ConferenceModiferPostReq")
public class ConferenceModiferPostReq {
	@ApiModelProperty(name="conferenceCategoryId", example="1")
	long conferenceCategoryId;
	@ApiModelProperty(name="title", example="title")
	String title;
	@ApiModelProperty(name="description", example="description")
	String description;
	@ApiModelProperty(name="is_active", example="false")
	Boolean is_active;
	@ApiModelProperty(name="conference_day", example="mon")
	String conference_day;
	@ApiModelProperty(name="conference_time", example="2019-01-02")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date conference_time;
	@ApiModelProperty(name="apply_end_time", example="2019-01-10")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date apply_end_time;
	@ApiModelProperty(name="apply_start_time", example="2019-01-01")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date apply_start_time;
	@ApiModelProperty(name="price", example="2000")
	Integer price;
	@ApiModelProperty(name="thumbnail", example="썸네일")
	MultipartFile thumbnail;
}
