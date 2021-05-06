package org.intercorpretail.challenge.config;

import org.intercorpretail.challenge.utils.exception.ExceptionResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebRestControllerAdvice {

    private final WebRestControllerParser webRestControllerParser;

    @Autowired
    public WebRestControllerAdvice(WebRestControllerParser webRestControllerParser) {
        this.webRestControllerParser = webRestControllerParser;
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ExceptionResponseWrapper> handleNotFoundException(Exception ex) {
        return this.webRestControllerParser.convertExceptionToResponseEntity(ex);
    }

}
