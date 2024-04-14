package com.example.demo.services;

import com.example.demo.dtos.reponses.AuthorDTO;
import com.example.demo.dtos.reponses.UserDTO;
import com.example.demo.dtos.requests.AuthorRequest;
import com.example.demo.dtos.requests.UserRequest;
import com.example.demo.entities.Author;
import com.example.demo.entities.User;
import com.example.demo.exceptions.BirthDateNotValidException;
import com.example.demo.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;

import static com.example.demo.utils.SystemUtil.*;

@Service("authorService")
public class AuthorService extends UserService {

    @Autowired
    public AuthorService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(userRepository, bCryptPasswordEncoder);
    }

    @Override
    @Transactional
    public UserDTO createUser(UserRequest userRequest) {
        AuthorRequest authorRequest = (AuthorRequest) userRequest;

        // check on birthdate can not be in the future
        LocalDate birthDate = authorRequest.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (birthDate.equals(LocalDate.now()) || birthDate.isAfter(LocalDate.now())) {
            throw new BirthDateNotValidException();
        }
        User applicationUser = new Author();
        BeanUtils.copyProperties(authorRequest, applicationUser);
        applicationUser.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        applicationUser = userRepository.save(applicationUser);

        AuthorDTO userDTO = new AuthorDTO();
        BeanUtils.copyProperties(applicationUser, userDTO);
        return userDTO;
    }

    @Override
    public UserDTO getUserById(Long id) {
        Author user = (Author) unwrapEntity(userRepository.findById(id), id, "Author");
        AuthorDTO userResponseDTO = new AuthorDTO();
        BeanUtils.copyProperties(user, userResponseDTO);
        return userResponseDTO;
    }

    @Override
    public Page<UserDTO> getAllUsers(String searchTerm, Integer pageSize, Integer pageNumber, String sortField, String sortDirection) {
        Pageable paginate = pagination(sortField, sortDirection, pageNumber, pageSize);
        searchTerm = searchTerm == null ? "" : searchTerm.toLowerCase();
        return userRepository.findAllAuthors(searchTerm, paginate).map(this::convertToDto);
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserRequest userRequest) {
        AuthorRequest authorRequest = (AuthorRequest) userRequest;
        Author oldAuthor = (Author) unwrapEntity(userRepository.findById(id), id, "author");
        copyNonNullProperties(authorRequest, oldAuthor);
        oldAuthor = userRepository.save(oldAuthor);
        AuthorDTO authorDTO = new AuthorDTO();
        BeanUtils.copyProperties(oldAuthor, authorDTO);
        return authorDTO;
    }

    @Override
    public UserDTO convertToDto(User user) {
        AuthorDTO response = new AuthorDTO();
        BeanUtils.copyProperties(user, response);
        return response;
    }


}
