package org.intercorpretail.challenge.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorDataConstants {
    CONSTRAINT_ERROR_DATA("Error in request body!", "One or more fields have errors", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR_DATA("Internal Server Error", "Something happened and a http 500 was returned :(", HttpStatus.INTERNAL_SERVER_ERROR),
    ENTITY_NOT_FOUND_ERROR_DATA("Entity Not Found!", "Given entity does not exists!", HttpStatus.NOT_FOUND),
    METHOD_NOT_ALLOWED_ERROR_DATA("Unsupported method!", "Either the given verb does not exist or is wrongly mapped!", HttpStatus.METHOD_NOT_ALLOWED),
    MEDIA_ERROR_DATA("Request Body is not JSON!", "Only JSON requests are accepted!", HttpStatus.UNSUPPORTED_MEDIA_TYPE);

    private String title;
    private String message;
    private HttpStatus status;
}
