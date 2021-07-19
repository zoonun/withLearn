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
public class Conference extends BaseEntity{
    int owner_id;
    int conference_category;
    @CreationTimestamp
    LocalDateTime call_start_time;
    Date call_update_time;
    String thumbnail;
    String title;
    String description;
    Boolean is_active;
}
