package com.example.relief.config;

import com.example.relief.constants.SpUtilKeyConstants;
import com.example.relief.utils.SpUtils;

public final class Config {

    public static final String SETTING_CONFIG = "SettingConfig";

    private static SpUtils sSp = SpUtils.getInstance(SETTING_CONFIG);

    private static boolean sIsLogin;
    private static int sUserId;
    private static String sUsername;

    // OCR
    private static String sOcrAccessToken;
    private static long sOcrExpiredTime;
    private static String sOcrRefreshToken;

    private Config() {
    }

    public static boolean getIsLogin() {
        return sIsLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        sSp.put(SpUtilKeyConstants.IS_LOGIN, isLogin);
        sIsLogin = isLogin;
    }

    public static int getUserId() {
        return sUserId;
    }

    public static void setUserId(int userId) {
        sSp.put(SpUtilKeyConstants.USER_ID, userId);
        sUserId = userId;
    }

    public static String getUsername() {
        return sUsername;
    }

    public static void setUsername(String username) {
        sSp.put(SpUtilKeyConstants.USERNAME, username);
        sUsername = username;
    }

    public static void resetConfig() {
        SpUtils.getInstance(SETTING_CONFIG).clear();
        loadConfig();
    }

    // OCR
    public static String getOcrAccessToken() {
        return sOcrAccessToken;
    }

    public static void setOcrAccessToken(String ocrAccessToken) {
        sSp.put(SpUtilKeyConstants.OCR_ACCESS_TOKEN, ocrAccessToken);
        sOcrAccessToken = ocrAccessToken;
    }

    public static long getOcrExpiredTime() {
        return sOcrExpiredTime;
    }

    public static void setOcrExpireTime(long ocrExpiredTime) {
        sSp.put(SpUtilKeyConstants.OCR_EXPIRED_TIME, ocrExpiredTime);
        sOcrExpiredTime = ocrExpiredTime;
    }

    public static String getOcrRefreshToken() {
        return sOcrRefreshToken;
    }

    public static void setOcrRefreshToken(String ocrRefreshToken) {
        sSp.put(SpUtilKeyConstants.OCR_REFRESH_TOKEN, ocrRefreshToken);
        sOcrRefreshToken = ocrRefreshToken;
    }

    public static void loadConfig() {
        sIsLogin = sSp.getBoolean(SpUtilKeyConstants.IS_LOGIN, false);
        sUserId = sSp.getInt(SpUtilKeyConstants.USER_ID, -1);
        sUsername = sSp.getString(SpUtilKeyConstants.USERNAME, "");
        // OCR
        sOcrAccessToken = sSp.getString(SpUtilKeyConstants.OCR_ACCESS_TOKEN, "");
        sOcrRefreshToken = sSp.getString(SpUtilKeyConstants.OCR_REFRESH_TOKEN, "");
        sOcrExpiredTime = sSp.getLong(SpUtilKeyConstants.OCR_EXPIRED_TIME, -1);
    }

    static {
        loadConfig();
    }
}
