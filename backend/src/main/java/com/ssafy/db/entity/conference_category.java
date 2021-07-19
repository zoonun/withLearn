package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * 컨퍼런스 모델 정의.
 */
@Entity
@Getter
@Setter
public class conference_category extends BaseEntity{
    String name;
}
