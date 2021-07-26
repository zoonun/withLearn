package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
public class ConferenceRecorded extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    private Conference conference;


    LocalDateTime startTime;
    Date endTime;
    String url;
    String conferenceRecordedcol;

    @PrePersist
    public void startTime(){this.startTime = LocalDateTime.now();}
}
