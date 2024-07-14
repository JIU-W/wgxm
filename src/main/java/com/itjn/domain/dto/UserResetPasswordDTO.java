package com.itjn.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserResetPasswordDTO implements Serializable {

    private Integer userId;

    private String userName;

    private String password;

    private String newPassword;

}
