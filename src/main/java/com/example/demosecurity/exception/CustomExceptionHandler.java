package com.example.demosecurity.exception;

import com.example.demosecurity.Respon.BaseItemRespon;
import com.example.demosecurity.Respon.BaseRespon;
import com.example.demosecurity.Respon.BaseResponError;
import com.example.demosecurity.constant.ErrorCodeDefine;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@ControllerAdvice
public class CustomExceptionHandler {
    @ResponseBody
    @ResponseStatus(OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseRespon methodArgumentNotValidException(MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
        BaseResponError baseRespon = new BaseResponError();
        baseRespon.setSuccess(false);
        baseRespon.setError(processFieldErrors(fieldErrorList));
        return baseRespon;
    }

    private BaseResponError.Error processFieldErrors(List<FieldError> fieldErrorList){
        BaseResponError.Error error = BaseResponError.Error.builder()
                .code(ErrorCodeDefine.VALIDATION_ERROR)
                .message(ErrorCodeDefine.getDetailErrorMessage(ErrorCodeDefine.VALIDATION_ERROR))
                .build();
        List<BaseResponError.ErrorDetail> errorDetailList = new ArrayList<>();
        fieldErrorList.stream().forEach(fieldError ->{
            BaseResponError.ErrorDetail errorDetail = BaseResponError.ErrorDetail.builder()
                    .id(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
            errorDetailList.add(errorDetail);
        });
        error.setErrorDetailList(errorDetailList);
        return error;
    }

    @ResponseBody
    @ResponseStatus(OK)
    @ExceptionHandler(Exception.class)
    public BaseRespon notReadableExceptionHandle(Exception ex){
        BaseResponError error = new  BaseResponError();
        error.setSuccess(false);
        BaseResponError.Error err = BaseResponError.Error.builder()
                .code(ErrorCodeDefine.SERVER_ERROR)
                .message(ex.getMessage())
                .errorDetailList(null)
                .build();
        error.setError(err);
        return error;
    }

    @ResponseBody
    @ResponseStatus(OK)
    @ExceptionHandler(AuthenticationException.class)
    public BaseRespon authenticationException(Exception ex){
        BaseResponError error = new BaseResponError();
        error.setSuccess(false);
        BaseResponError.Error err = BaseResponError.Error.builder()
                .code(ErrorCodeDefine.TOKEN_REQUIRED)
                .message("Password incorrect")
                .errorDetailList(null)
                .build();
        error.setError(err);
        return error;
    }


}
