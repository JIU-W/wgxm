package com.itjn.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserPageQueryDTO implements Serializable {

    private int pageNum;

    private int pageSize;

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
