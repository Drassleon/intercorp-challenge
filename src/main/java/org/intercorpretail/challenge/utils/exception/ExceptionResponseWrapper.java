package org.intercorpretail.challenge.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseWrapper {
    private String title;
    private String code;
    private String message;
    private List<DetailResponseWrapper> errors;

}
