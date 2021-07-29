package com.ssafy.api.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Chat {
    private Long userId;
    private String name;
    private String message;

}
