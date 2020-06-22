package com.aispeech.ezml.authserver.pojo;

import com.aispeech.ezml.authserver.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户VO
 *
 * @author ZhangXi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserVO extends User {


    public UserVO() {}

    public UserVO(User user) {
        this.setId(user.getId());
        this.setLoginName(user.getLoginName());
        this.setUserName(user.getUserName());
        this.setPassword(user.getPassword());
        this.setStatus(user.getStatus());
    }

}
