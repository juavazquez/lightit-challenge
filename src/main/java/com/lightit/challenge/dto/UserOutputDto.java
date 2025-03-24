package com.lightit.challenge.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(name = "User Schema", description = "User schema")
public class UserOutputDto extends UserBaseDto {

    public UserOutputDto(UserBaseDto userBaseDto, String documentImgUrl) {
        super(
                userBaseDto.getEmail(),
                userBaseDto.getFirstName(),
                userBaseDto.getLastName(),
                userBaseDto.getPhoneNumber(),
                userBaseDto.getStreetLine(),
                userBaseDto.getCity(),
                userBaseDto.getState(),
                userBaseDto.getCountry());
        this.documentImgUrl = documentImgUrl;
    }

    @NotNull(message = "Document image is required")
    private String documentImgUrl;

}
