package com.example.demosecurity.Respon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponError extends BaseRespon{
    private Error error;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Error{
        private int code;
        private String message;
        private List<ErrorDetail> errorDetailList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ErrorDetail{
        private String id;
        private String message;
    }
}
