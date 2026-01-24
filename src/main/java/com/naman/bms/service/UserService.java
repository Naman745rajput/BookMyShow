package com.naman.bms.service;


import com.naman.bms.dto.UserDto;
import com.naman.bms.exception.ResourceNotFoundException;
import com.naman.bms.model.User;
import com.naman.bms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDto createUser(UserDto userDto)
    {
        User user = mapToEntity(userDto);
        User savedUser = userRepository.save(user);
        return mapToDto(savedUser);

    }

    public UserDto getUserById(Long id)
    {
        User user = userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User not found with id: "+id));
        return mapToDto(user);
    }

    public List<UserDto> getAllUsers()
    {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public UserDto updateUser(Long id, UserDto userDto)
    {
        User user = userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User not found with id: "+id));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());

        User updatedUser = userRepository.save(user);
        return mapToDto(updatedUser);
    }

    public void deleteUser(Long id)
    {
        User user = userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User not found with id: "+id));
        userRepository.delete(user);
    }


    private User mapToEntity(UserDto userDto)
    {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        return user;
    }

    private UserDto mapToDto(User user)
    {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        return userDto;
    }
}
