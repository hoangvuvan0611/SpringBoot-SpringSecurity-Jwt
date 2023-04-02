package com.example.demosecurity.constant;

public class ErrorCodeDefine {
    public static final int OTHER = 1;
    public static final int VALIDATION_ERROR = 2;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int TOKEN_REQUIRED = 405;
    public static final int REQUEST_TIME_OUT = 408;
    public static final int SERVER_ERROR = 500;
    public static final int GATEWAY_TIME_OUT = 504;

    public static final String getDetailErrorMessage(int code){
        switch (code){
            case OTHER:
                return "Các lỗi khác!";
            case VALIDATION_ERROR:
                return "Invalid parameter";
            case BAD_REQUEST:
                return "Cú pháp lỗi, yêu cầu bị từ chối!";
            case FORBIDDEN:
                return "Truy cập bị từ chối, không có quyền truy cập!";
            case UNAUTHORIZED:
                return "Yêu cầu không xác thực!";
            case NOT_FOUND:
                return "Không tìm thấy bản ghi yêu cầu!";
            case TOKEN_REQUIRED:
                return "Phương thức truy cập không được phép!";
            case REQUEST_TIME_OUT:
                return "Hết thời gian yêu cầu!";
            case SERVER_ERROR:
                return "Lỗi hệ thống!";
            case GATEWAY_TIME_OUT:
                return "Hết thời gian truy cập!";
            default:
                return "Lỗi không xác định!";
        }
    }
}
