package io.vendas.application.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public final class GenericErrorResponse {

    private LocalDateTime timestamp;

    private Integer status;

    private String error;

}
