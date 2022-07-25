package com.rbc.stocks.exceptions;

import com.rbc.stocks.errors.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@ControllerAdvice
public class CustomException extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ArrayList errors = new ArrayList();
        for (FieldError error : ex.getBindingResult().getFieldErrors()){
            errors.add(error.getField() + " : " + error.getDefaultMessage());
        }
        ErrorResponse eR = new ErrorResponse(errors,"Number of invliad fields :"+Integer.toString(ex.getFieldErrorCount()),HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(ex,eR,headers,eR.getErrorStatus(),request);
    }

//    public ResponseEntity<Object> headerError(Exception ex, HttpHeaders headers, ServletWebRequest request) {
//        ErrorResponse eR = new ErrorResponse("issue with clientid","Number of invliad fields :",HttpStatus.BAD_REQUEST);
//        return handleExceptionInternal(ex,eR,headers,eR.getErrorStatus(),request);
//    }


}
