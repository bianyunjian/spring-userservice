package com.aispeech.ezml.authserver.pojo;

import com.aispeech.ezml.authserver.model.User;
import lombok.Data;

/**
 * 用户DTO
 * 将用户请求数据封装后传递到Service层
 *
 * @author ZhangXi
 */
@Data
public class UserDTO {

    private User user;

    private Integer roleId;

}
