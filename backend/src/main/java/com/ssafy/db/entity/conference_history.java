package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Date;

/**
 * 컨퍼런스 모델 정의.
 */
@Entity
@Getter
@Setter
public class conference_history extends BaseEntity{
    int conference_id;
    int user_id;
    short action;
    Date inserted_time;
}
