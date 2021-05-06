package org.intercorpretail.challenge.config;

import lombok.Generated;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.intercorpretail.challenge.utils.exception.DetailResponseWrapper;
import org.intercorpretail.challenge.utils.exception.EntityNotFoundException;
import org.intercorpretail.challenge.utils.exception.ExceptionResponseWrapper;
import org.intercorpretail.challenge.utils.exception.ErrorDataConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class WebRestControllerParser {
    @Generated
    private static final Logger log = LoggerFactory.getLogger(WebRestControllerParser.class);

    public WebRestControllerParser() {
        //Empty Constructor
    }

    public ResponseEntity<ExceptionResponseWrapper> convertExceptionToResponseEntity(Exception exception) {
        log.error(exception.getMessage(), exception);
        ResponseEntity<ExceptionResponseWrapper> responseEntity;
        if (exception instanceof ConstraintViolationException) {
            responseEntity = this.convertConstraintViolationExceptionToResponseEntity((ConstraintViolationException) exception);
        } else if (exception instanceof MethodArgumentNotValidException) {
            responseEntity = this.convertMethodArgumentNotValidExceptionToResponseEntity((MethodArgumentNotValidException) exception);
        } else if (exception instanceof HttpMediaTypeNotSupportedException) {
            responseEntity = this.convertToHttpMediaTypeNotSupportedResponseEntity();
        } else if (exception instanceof MethodArgumentTypeMismatchException) {
            responseEntity = this.convertToInternalServerErrorResponseEntity((MethodArgumentTypeMismatchException) exception);
        } else if (exception instanceof HttpRequestMethodNotSupportedException) {
            responseEntity = this.convertToHttpRequestMethodNotSupportedResponseEntity();
        } else if (exception instanceof EntityNotFoundException) {
            responseEntity = this.convertEntityNotFoundExceptionToResponseEntity();
        } else {
            responseEntity = this.convertToInternalServerErrorResponseEntity();
        }

        return responseEntity;
    }

    private ResponseEntity<ExceptionResponseWrapper> convertEntityNotFoundExceptionToResponseEntity() {
        return this.convertToGeneralErrorResponseEntity(ErrorDataConstants.ENTITY_NOT_FOUND_ERROR_DATA);
    }

    private ResponseEntity<ExceptionResponseWrapper> convertToHttpMediaTypeNotSupportedResponseEntity() {
        return this.convertToGeneralErrorResponseEntity(ErrorDataConstants.MEDIA_ERROR_DATA);
    }

    private ResponseEntity<ExceptionResponseWrapper> convertToHttpRequestMethodNotSupportedResponseEntity() {
        return this.convertToGeneralErrorResponseEntity(ErrorDataConstants.METHOD_NOT_ALLOWED_ERROR_DATA);
    }

    private ResponseEntity<ExceptionResponseWrapper> convertToInternalServerErrorResponseEntity(MethodArgumentTypeMismatchException exception) {
        List<DetailResponseWrapper> validationsWrappers = Collections.singletonList(DetailResponseWrapper.builder().field(exception.getName()).message("Field data type is different than expected").build());
        return this.convertToGeneralErrorResponseEntity(ErrorDataConstants.CONSTRAINT_ERROR_DATA, validationsWrappers);
    }

    private ResponseEntity<ExceptionResponseWrapper> convertConstraintViolationExceptionToResponseEntity(ConstraintViolationException exception) {
        List<DetailResponseWrapper> validationsWrappers = new ArrayList<>();

        for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
            validationsWrappers.add(DetailResponseWrapper.builder().field(((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().toString()).message(constraintViolation.getMessage()).build());
        }

        return this.convertToGeneralErrorResponseEntity(ErrorDataConstants.CONSTRAINT_ERROR_DATA, validationsWrappers);
    }

    private ResponseEntity<ExceptionResponseWrapper> convertMethodArgumentNotValidExceptionToResponseEntity(MethodArgumentNotValidException exception) {
        List<DetailResponseWrapper> validationsWrappers = new ArrayList<>();

        for (ObjectError objectError : exception.getBindingResult().getAllErrors()) {
            validationsWrappers.add(DetailResponseWrapper.builder().field(objectError instanceof FieldError ? ((FieldError) objectError).getField() : objectError.getObjectName()).message(objectError.getDefaultMessage()).build());
        }

        return this.convertToGeneralErrorResponseEntity(ErrorDataConstants.CONSTRAINT_ERROR_DATA, validationsWrappers);
    }

    private ResponseEntity<ExceptionResponseWrapper> convertToInternalServerErrorResponseEntity() {
        return this.convertToGeneralErrorResponseEntity(ErrorDataConstants.INTERNAL_SERVER_ERROR_DATA);
    }

    private ResponseEntity<ExceptionResponseWrapper> convertToGeneralErrorResponseEntity(ErrorDataConstants generalError) {
        return this.convertToGeneralErrorResponseEntity(generalError, new ArrayList<>());
    }

    private ResponseEntity<ExceptionResponseWrapper> convertToGeneralErrorResponseEntity(ErrorDataConstants errorData, List<DetailResponseWrapper> detailedList) {
        return ResponseEntity.status(errorData.getStatus()).body(ExceptionResponseWrapper.builder()
                .code(errorData.getStatus().toString())
                .title(errorData.getTitle())
                .message(errorData.getMessage())
                .errors(detailedList).build());
    }
}