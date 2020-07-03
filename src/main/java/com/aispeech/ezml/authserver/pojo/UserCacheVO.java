package com.aispeech.ezml.authserver.pojo;

import com.aispeech.ezml.authserver.constant.AppConst;
import com.aispeech.ezml.authserver.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 *
 *
 * @author ZhangXi
 */
@JsonIgnoreProperties(value = {"loginName", "password"})
@EqualsAndHashCode(callSuper = true)
@Data
public class UserCacheVO extends UserVO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConst.BASIC_TIME_FORMAT)
    @Override
    public LocalDateTime getGmtCreate() {
        return super.getGmtCreate();
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConst.BASIC_TIME_FORMAT)
    @Override
    public LocalDateTime getGmtUpdate() {
        return super.getGmtUpdate();
    }

    public UserCacheVO() {
    }

    public UserCacheVO(User user) {
        super(user);
    }
}
