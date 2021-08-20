package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Community extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private ConferenceCategory conferenceCategory;

    String descript;
    String title;

    LocalDateTime Time;
    @PrePersist
    public void startTime(){this.Time = LocalDateTime.now();}


}
