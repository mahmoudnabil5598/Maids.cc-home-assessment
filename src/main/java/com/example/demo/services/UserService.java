package com.example.demo.services;

import com.example.demo.dtos.reponses.UserDTO;
import com.example.demo.dtos.requests.UserRequest;
import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.example.demo.utils.SystemUtil.unwrapEntity;

public abstract class UserService {
    protected UserRepository userRepository;
    protected BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public abstract UserDTO createUser(UserRequest userRequest);

    public abstract UserDTO getUserById(Long id);

    public abstract Page<UserDTO> getAllUsers(String searchTerm, Integer pageSize, Integer pageNumber, String sortField, String sortDirection);

    public abstract UserDTO updateUser(Long id, UserRequest userRequest);

    public abstract UserDTO convertToDto(User user);

    public UserDTO getUserByUserName(String userName) {
        User user = unwrapEntity(userRepository.findByUserName(userName), userName, "user");
        UserDTO userResponseDTO = new UserDTO();
        BeanUtils.copyProperties(user, userResponseDTO);
        return userResponseDTO;
    }

    public final void deleteUser(Long id) {
        unwrapEntity(userRepository.findById(id), id, "user");
        userRepository.deleteById(id);
    }
}
