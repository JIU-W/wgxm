package com.itjn.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVO implements Serializable {

    private Integer userId;

    private String userName;

    private String name;

    private String email;

    private String avatar;

    private String role;

    private String sex;

    private String phone;

    private String info;

    private String birth;

    //private String token;


}
