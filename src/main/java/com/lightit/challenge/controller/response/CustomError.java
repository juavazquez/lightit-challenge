package com.lightit.challenge.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomError {
    @Schema(description = "status code", example = "int")
    private Integer status;
    @Schema(description = "error message")
    private String message;
    @Schema(description = "error details")
    private String error;
}
