package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;
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
public class UserConference extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    Conference conference;

    @ManyToOne(fetch = FetchType.LAZY)
    User user;

    public static UserConference of (Conference conference, User user){
        UserConference userConference= new UserConference();
        userConference.setConference(conference);
        userConference.setUser(user);
        return userConference;
    }
}

