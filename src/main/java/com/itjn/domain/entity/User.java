package com.itjn.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String userName;

    private String password;

    private String name;

    private String email;

    private String avatar;

    private String role;

    private String sex;

    private String phone;

    private String info;

    private String birth;

}
