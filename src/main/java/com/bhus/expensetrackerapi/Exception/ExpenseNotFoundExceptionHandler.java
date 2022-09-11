package com.bhus.expensetrackerapi.Exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExpenseNotFoundExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {ExpenseNotFoundException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request){
        String bodyResponse=ex.getMessage();
        return handleExceptionInternal(ex,bodyResponse,new HttpHeaders(), HttpStatus.BAD_REQUEST,request);
    }


}
