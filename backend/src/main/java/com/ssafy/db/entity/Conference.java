package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Date;
/**
 * 컨퍼런스 모델 정의.
 */
@Entity
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Conference extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    private ConferenceCategory conferenceCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @CreationTimestamp
    LocalDateTime call_start_time;
    Date call_update_time;
    String thumbnail;
    String title;
    String description;
    Boolean is_active;
}
