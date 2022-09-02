package com.project.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Info {
    private String userEmail;
    private String userPassword;
    private String userName;
    private String createdAt;
    private String selfAuth;

}
