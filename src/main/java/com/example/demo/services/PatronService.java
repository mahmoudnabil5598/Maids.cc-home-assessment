package com.example.demo.services;

import com.example.demo.dtos.reponses.*;
import com.example.demo.dtos.requests.PatronRequest;
import com.example.demo.dtos.requests.UserRequest;
import com.example.demo.entities.*;
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
import java.util.HashSet;
import java.util.Set;

import static com.example.demo.utils.SystemUtil.*;

@Service("patronService")
public class PatronService extends UserService {


    @Autowired
    public PatronService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(userRepository, bCryptPasswordEncoder);
    }

    @Override
    @Transactional
    public UserDTO createUser(UserRequest userRequest) {
        PatronRequest patronRequest = (PatronRequest) userRequest;

        // check on birthdate can not be in the future
        LocalDate birthDate = patronRequest.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (birthDate.equals(LocalDate.now()) || birthDate.isAfter(LocalDate.now())) {
            throw new BirthDateNotValidException();
        }
        User applicationUser = new Patron();
        BeanUtils.copyProperties(patronRequest, applicationUser);
        applicationUser.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        applicationUser = userRepository.save(applicationUser);

        PatronDTO userDTO = new PatronDTO();
        BeanUtils.copyProperties(applicationUser, userDTO);
        return userDTO;
    }

    @Override
    public UserDTO getUserById(Long id) {
        Patron user = (Patron) unwrapEntity(userRepository.findById(id), id, "patron");


        //Contact information
        Set<ContactInformationDTO> contactInformationDTOs = new HashSet<>();
        for (ContactInformation C : user.getContactInformation()) {
            ContactInformationDTO contactInformationDTO = new ContactInformationDTO();
            BeanUtils.copyProperties(C, contactInformationDTO);
            contactInformationDTOs.add(contactInformationDTO);
        }
        //Books
        Set<BookDTO> bookDTOS = new HashSet<>();
        for (Book B : user.getBooks()) {
            BookDTO bookDTO = convertToDetailedDTO(B);
            bookDTOS.add(bookDTO);
        }

        PatronDTO userResponseDTO = new PatronDTO();
        userResponseDTO.setBooks(bookDTOS);
        userResponseDTO.setContactInformation(contactInformationDTOs);
        BeanUtils.copyProperties(user, userResponseDTO);
        return userResponseDTO;
    }

    @Override
    public Page<UserDTO> getAllUsers(String searchTerm, Integer pageSize, Integer pageNumber, String sortField, String sortDirection) {
        Pageable paginate = pagination(sortField, sortDirection, pageNumber, pageSize);
        searchTerm = searchTerm == null ? "" : searchTerm.toLowerCase();
        return userRepository.findAllPatrons(searchTerm, paginate).map(this::convertToDto);
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserRequest userRequest) {
        PatronRequest patronRequest = (PatronRequest) userRequest;
        Patron oldPatron = (Patron) unwrapEntity(userRepository.findById(id), id, "patron");
        copyNonNullProperties(patronRequest, oldPatron);
        oldPatron = userRepository.save(oldPatron);
        PatronDTO patronDTO = new PatronDTO();
        BeanUtils.copyProperties(oldPatron, patronDTO);

        return patronDTO;
    }

    @Override
    public UserDTO convertToDto(User user) {
        PatronDTO response = new PatronDTO();
        BeanUtils.copyProperties(user, response);
        return response;
    }

}
