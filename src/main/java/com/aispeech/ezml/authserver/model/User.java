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

    private Integer id;

    private String loginName;

    private String userName;

    private String password;

    private UserStatus status;

}
