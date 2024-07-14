package com.itjn.domain.dto;
import lombok.Data;
import java.io.Serializable;




@Data
public class UserRegisterDTO implements Serializable {

    private String userName;

    private String password;

}