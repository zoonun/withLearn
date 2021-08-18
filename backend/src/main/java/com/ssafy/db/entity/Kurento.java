package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Kurento extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    String imageName;
    String imagePath;
}
