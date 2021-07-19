package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 컨퍼런스 모델 정의.
 */
@Entity
@Getter
@Setter
public class user_conference extends BaseEntity{
    int conference_id;
    int user_id;
}

