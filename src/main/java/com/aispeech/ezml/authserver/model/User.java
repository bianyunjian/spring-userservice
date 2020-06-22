package com.aispeech.ezml.authserver.model;

import com.aispeech.ezml.authserver.menum.UserStatus;
import lombok.Data;

/**
 * 用户表实体类
 *
 * @author ZhangXi
 */
@Data
public class User {

    public static final String COL_USER_NAME = "user_name";
    public static final String COL_LOGIN_NAME = "login_name";

    private Integer id;

    private String loginName;

    private String userName;

    private String password;

    private UserStatus status;

}
