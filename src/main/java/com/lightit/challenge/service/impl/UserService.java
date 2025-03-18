package com.lightit.challenge.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lightit.challenge.dto.UserDto;
import com.lightit.challenge.entity.Address;
import com.lightit.challenge.entity.User;
import com.lightit.challenge.repository.UserRepository;
import com.lightit.challenge.service.IUserService;

import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User save(UserDto userDto) {
        // Check if user already exists
        Optional<User> userOptional = userRepository.findByEmail(userDto.getEmail());
        if (userOptional.isPresent()) {
            throw new EntityExistsException("User already registered");
        }

        // Save user to database
        User user = userRepository.save(mapUserDtoToUser(userDto));

        return user; // TODO
    }

    private User mapUserDtoToUser(UserDto userDto) {
        LocalDateTime now = LocalDateTime.now();
        Address address = new Address(
                userDto.getStreetLine(),
                userDto.getCity(),
                userDto.getState(),
                userDto.getCountry());
        User user = new User(
                null,
                userDto.getEmail(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getPhoneNumber(),
                now,
                now,
                address,
                false);

        return user;
    }

    @Override
    public void setDocumentUploadSuccessful(User user) {
        user.setDocumentUploadSuccessful(true);
        userRepository.save(user);
    }

}
