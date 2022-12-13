package com.dimas.gw.utils;

public interface Constants {
    String JWT_CLIENT_ID_CLAIM = "azp";
    String TIMESTAMP_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    String API_ERROR_LBL_TIMESTAMP = "timestamp";
    String API_ERROR_LBL_PATH = "path";
    String API_ERROR_LBL_STATUS = "status";
    String API_ERROR_LBL_ERRORS = "errors";
    String API_ERROR_LBL_MESSAGE = "message";

    String USER_ID_HEADER_NAME = "X-USER-ID";
    String USER_NAME_HEADER_NAME = "X-USER-NAME"; //aka login

    String USER_ID_CLAIM = "userId";
    String USER_NAME_CLAIM = "email";
    String GUEST_AUTHORITY = "GUEST";
    String USER_PERMISSION = "USER";

}
