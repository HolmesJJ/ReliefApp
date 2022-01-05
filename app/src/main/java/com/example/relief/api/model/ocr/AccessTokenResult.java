package com.example.relief.api.model.ocr;

import androidx.annotation.NonNull;

public class AccessTokenResult {

    private String refreshToken;
    private int expiresIn;
    private String scope;
    private String sessionKey;
    private String accessToken;
    private String sessionSecret;

    public AccessTokenResult() {
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getExpiresIn() {
        return this.expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getSessionKey() {
        return this.sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSessionSecret() {
        return sessionSecret;
    }

    public void setSessionSecret(String sessionSecret) {
        this.sessionSecret = sessionSecret;
    }

    @NonNull

    @Override
    public String toString() {
        return "AccessTokenResult{" + "refreshToken='" + refreshToken + '\'' + ", expiresIn=" + expiresIn
                + ", scope='" + scope + '\'' + ", sessionKey='" + sessionKey + '\''
                + ", accessToken='" + accessToken + '\'' + ", sessionSecret='" + sessionSecret + '\'' + '}';
    }
}
