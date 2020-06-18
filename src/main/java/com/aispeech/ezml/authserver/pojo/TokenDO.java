package com.aispeech.ezml.authserver.pojo;

import com.aispeech.ezml.authserver.tool.JwtTool;

/**
 *
 *
 * @author ZhangXi
 */
public class TokenDO extends JwtTool.TokenData {

    public TokenDO() {
    }

    public TokenDO(JwtTool.TokenData data) {
        this.setUserId(data.getUserId());
        this.setUserName(data.getUserName());
        this.setAccessToken(data.getAccessToken());
        this.setRefreshToken(data.getRefreshToken());
        this.setExtMap(data.getExtMap());
    }
}
