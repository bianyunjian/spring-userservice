package com.aispeech.ezml.authserver.model;

import com.aispeech.ezml.authserver.menum.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户表实体类
 *
 * @author ZhangXi
 */
@Data
public class User {

    public static final String COL_USER_NAME = "user_name";
    public static final String COL_LOGIN_NAME = "login_name";
    public static final String COL_LAST_LOGIN_TIME = "last_login_time";

    private Integer id;

    private String loginName;

    private String userName;

    private String password;

    private String email;

    private String department;

    private String position;

    private LocalDateTime lastLoginTime;

    private Integer status;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtUpdate;

}
