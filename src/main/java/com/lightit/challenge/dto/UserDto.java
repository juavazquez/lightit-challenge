package com.lightit.challenge.dto;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotEmpty(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;

    @NotEmpty(message = "First name is required")
    @JsonProperty("first_name")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    @JsonProperty("last_name")
    private String lastName;

    @NotEmpty(message = "Street line is required")
    @JsonProperty("street_line")
    private String streetLine;

    @NotEmpty(message = "City is required")
    private String city;

    @NotEmpty(message = "State is required")
    private String state;

    @NotEmpty(message = "Country is required")
    private String country;

    @NotNull(message = "Patient image is required")
    @JsonProperty("document_img")
    private MultipartFile documentImg; // TODO validation for types (.jpg .jpeg . png only)

}
