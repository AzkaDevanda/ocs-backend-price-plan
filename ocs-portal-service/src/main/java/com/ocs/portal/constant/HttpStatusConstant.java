package com.ocs.portal.constant;

public class HttpStatusConstant {
    // 2xx Success
    public static final String SUCCESS_MESSAGE = "Success";

    // 4xx Client Errors
    public static final String BAD_REQUEST_MESSAGE = "Bad Request";
    public static final String UNAUTHORIZED_MESSAGE = "Unauthorized";
    public static final String FORBIDDEN_MESSAGE = "Forbidden";
    public static final String NOT_FOUND_MESSAGE = "Data Not Found";
    public static final String CONFLICT_MESSAGE = "Conflict";

    // 5xx Server Errors
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal Server Error";
    public static final String BAD_GATEWAY_MESSAGE = "Bad Gateway";
    public static final String SERVICE_UNAVAILABLE_MESSAGE = "Service Unavailable";
    public static final String GATEWAY_TIMEOUT_MESSAGE = "Gateway Timeout";


    // Handle Error
    /* ===> Offer <=== */
    public static final String OFFER_NAME_ALREADY_EXISTS = "An offer with the same name";
    public static final String OFFER_ALREADY_EXISTS = "An offer with the same name, type, and SP ID already exists.";
    public static final String OFFER_CODE_ALREADY_EXISTS = "An offer with the same code, type, and SP ID already exists.";
}
