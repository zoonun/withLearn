package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * 컨퍼런스 모델 정의.
 */
@Entity
@Getter
@Setter
public class ConferenceHistory extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    private Conference conference;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    short action;
    Date inserted_time;
}
