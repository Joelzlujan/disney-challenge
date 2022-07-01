package com.alkemy.disney.disney.controller;

import com.alkemy.disney.disney.dto.ApiErrorDTO;
import com.alkemy.disney.disney.exceptions.DuplicateValueException;
import com.alkemy.disney.disney.exceptions.NotFoundException;
import com.alkemy.disney.disney.exceptions.ParamNotFoundException;
import com.alkemy.disney.disney.exceptions.UserAlreadyExistAuthenticationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value= {ParamNotFoundException.class})
    protected ResponseEntity<Object> handleParamNotFound(RuntimeException ex, WebRequest request){
        ApiErrorDTO errorDTO = new ApiErrorDTO(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                Arrays.asList("Param Not Found")
        );
        return handleExceptionInternal(ex, errorDTO, new HttpHeaders(),HttpStatus.BAD_REQUEST,request);
    }
//Handler for @Valid annotation
    @Override
    protected ResponseEntity<Object>handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error: ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        ApiErrorDTO apiError = new ApiErrorDTO(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),errors);
        return handleExceptionInternal(ex,apiError,headers,apiError.getStatus(),request);
        }

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request){
        String bodyOfResponse = "This should be application specific";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(),HttpStatus.CONFLICT,request);
    }
    @ExceptionHandler(value={NotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundException(RuntimeException ex, WebRequest request){
        ApiErrorDTO errorDTO = new ApiErrorDTO(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                Arrays.asList("Data not found"));
        return handleExceptionInternal(ex, errorDTO,new HttpHeaders(),HttpStatus.NOT_FOUND,request);
    }
    @ExceptionHandler(value={DuplicateValueException.class})
    protected ResponseEntity<Object> handleDuplicateValueException(RuntimeException ex, WebRequest request){
        ApiErrorDTO errorDTO = new ApiErrorDTO(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                Arrays.asList("Duplicated Value"));
        return handleExceptionInternal(ex, errorDTO, new HttpHeaders(),HttpStatus.CONFLICT,request);
    }
    @ExceptionHandler(value={UserAlreadyExistAuthenticationException.class})
    protected ResponseEntity<Object> handleUserAlreadyExistException(RuntimeException ex, WebRequest request){
        ApiErrorDTO errorDTO = new ApiErrorDTO(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                Arrays.asList("User already Exist"));
        return handleExceptionInternal(ex, errorDTO, new HttpHeaders(),HttpStatus.CONFLICT,request);

    }

}
