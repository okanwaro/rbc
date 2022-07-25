package com.rbc.stocks.errors;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private String errorMessage;
    private HttpStatus errorStatus;
    private List inValidFields ;


    public ErrorResponse (List<String> errorCode, String errorMessage, HttpStatus errorStatus){
        this.inValidFields = Arrays.asList(errorCode);
        this.errorMessage = errorMessage;
        this.errorStatus = errorStatus;
    }

}
