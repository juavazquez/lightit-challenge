package com.lightit.challenge.mapper;

import java.time.LocalDateTime;

import com.lightit.challenge.dto.UserBaseDto;
import com.lightit.challenge.entity.Address;
import com.lightit.challenge.entity.User;

public class UserMapper implements IMapper<User, UserBaseDto> {

    @Override
    public UserBaseDto toDto(User entity) {
        UserBaseDto userDto = new UserBaseDto();
        userDto.setEmail(entity.getEmail());
        userDto.setFirstName(entity.getFirstName());
        userDto.setLastName(entity.getLastName());
        userDto.setPhoneNumber(entity.getPhoneNumber());
        userDto.setStreetLine(entity.getAddress().getStreetLine());
        userDto.setCity(entity.getAddress().getCity());
        userDto.setState(entity.getAddress().getState());
        userDto.setCountry(entity.getAddress().getCountry());
        return userDto;
    }

    @Override
    public User toEntity(UserBaseDto dto) {
        LocalDateTime now = LocalDateTime.now();
        Address address = new Address();
        address.setStreetLine(dto.getStreetLine());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setCountry(dto.getCountry());

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        user.setAddress(address);

        return user;
    }

}
