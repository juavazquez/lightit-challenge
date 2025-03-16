package com.lightit.challenge.controller.response;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomError {
    private Integer status;
    private String message;
    private Map<String, String> error;
}
