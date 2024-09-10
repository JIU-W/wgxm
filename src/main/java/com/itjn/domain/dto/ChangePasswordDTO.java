package com.itjn.domain.dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {

    private Integer userId;

    private String password;

    private String newPassword;

}
