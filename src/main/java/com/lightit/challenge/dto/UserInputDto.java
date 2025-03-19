package com.lightit.challenge.dto;

import org.springframework.web.multipart.MultipartFile;

import com.lightit.challenge.validator.ValidFileType;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInputDto extends UserBaseDto {

    public UserInputDto(UserBaseDto userBaseDto, MultipartFile documentImg) {
        super(
                userBaseDto.getEmail(),
                userBaseDto.getFirstName(),
                userBaseDto.getLastName(),
                userBaseDto.getPhoneNumber(),
                userBaseDto.getStreetLine(),
                userBaseDto.getCity(),
                userBaseDto.getState(),
                userBaseDto.getCountry());

        this.documentImg = documentImg;
    }

    @NotNull(message = "Document image is required")
    @ValidFileType(allowedTypes = { "image/jpeg", "image/jpg", "image/png" })
    private MultipartFile documentImg;

}
