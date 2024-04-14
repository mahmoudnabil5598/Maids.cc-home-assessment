package com.example.demo.services;

import com.example.demo.dtos.reponses.PatronDTO;
import com.example.demo.dtos.reponses.UserDTO;
import com.example.demo.dtos.requests.PatronRequest;
import com.example.demo.entities.Patron;
import com.example.demo.exceptions.BirthDateNotValidException;
import com.example.demo.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.example.demo.utils.SystemUtil.pagination;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PatronServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private PatronService patronService;

    @BeforeEach
    void setUp() throws IllegalAccessException, NoSuchFieldException {
        MockitoAnnotations.openMocks(this);
        Field field = UserService.class.getDeclaredField("userRepository");
        field.setAccessible(true);
        field.set(patronService, userRepository);

        MockitoAnnotations.openMocks(this);
        Field field2 = UserService.class.getDeclaredField("bCryptPasswordEncoder");
        field2.setAccessible(true);
        field2.set(patronService, bCryptPasswordEncoder);
    }

    @Test
    void testCreateUser_ValidData_Success() {
        // Given
        PatronRequest patronRequest = new PatronRequest("author", "password", "John Doe", new Date(1123847746L));

        Patron patron = new Patron();

        //set fields
        patron.setId(1L);
        patron.setUserName(patronRequest.getUserName());
        patron.setFullName(patronRequest.getFullName());
        patron.setBirthDate(patronRequest.getBirthDate());

        when(userRepository.save(any(Patron.class))).thenReturn(patron);

        // When
        UserDTO result = patronService.createUser(patronRequest);
        // Then
        assertNotNull(result);
        assertEquals("John Doe", result.getFullName());
        assertEquals("author", result.getUserName());
        assertEquals(new Date(1123847746L), result.getBirthDate());

    }

    @Test
    void testCreateUser_InvalidBirthDate_ExceptionThrown() {
        // Given
        PatronRequest patronRequest = new PatronRequest("author", "password", "John Doe", new Date(Instant.now().toEpochMilli() + 9000000000L));

        // When / Then
        BirthDateNotValidException exception = assertThrows(BirthDateNotValidException.class, () -> {
            patronService.createUser(patronRequest);
        });
        assertEquals("birthdate must be in the past", exception.getMessage());
    }

    @Test
    void testCreateUser_Check_Password_Encoding() {
        // Given
        PatronRequest patronRequest = new PatronRequest("author", "password", "John Doe", new Date(1123847746L));
        Patron patron = new Patron();

        patron.setPassword(patronRequest.getPassword());
        patron.setFullName(patronRequest.getFullName());
        patron.setBirthDate(patronRequest.getBirthDate());
        patron.setPassword(patronRequest.getPassword());

        // When
        when(userRepository.save(any(Patron.class))).thenReturn(patron);

        patronService.createUser(patronRequest);
        // Then
        verify(bCryptPasswordEncoder).encode(patronRequest.getPassword());
    }


    @Test
    void testGetUserById() {
        // Given
        Long userId = 1L;
        Patron patron = new Patron();
        patron.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(patron));

        // When
        PatronDTO result = (PatronDTO) patronService.getUserById(userId);

        // Then
        assertNotNull(result);
        assertEquals(userId, result.getId());
    }

    @Test
    void testGetAllUsers() {
        // Given
        String searchTerm = "patron";
        Integer pageSize = 10;
        Integer pageNumber = 0;
        String sortField = "fullName";
        String sortDirection = "asc";
        Pageable paginate = pagination(sortField, sortDirection, pageNumber, pageSize);

        // Mock the Page object
        List<Patron> patrons = new ArrayList<>();
        Patron patron1 = new Patron();
        patron1.setUserName("patron1");
        patron1.setFullName("John Doe");
        patron1.setBirthDate(new Date());

        Patron patron2 = new Patron();
        patron2.setUserName("patron2");
        patron2.setFullName("Jane Smith");
        patron2.setBirthDate(new Date());

        patrons.add(patron1);
        patrons.add(patron2);
        Page<Patron> authorPage = new PageImpl<>(patrons, paginate, patrons.size());

        // Mock the userRepository
        when(userRepository.findAllPatrons((searchTerm.toLowerCase()), (paginate))).thenReturn(authorPage);

        // When
        Page<UserDTO> resultPage = patronService.getAllUsers(searchTerm, pageSize, pageNumber, sortField, sortDirection);

        // Then
        assertNotNull(resultPage);
        assertEquals(authorPage.getTotalElements(), resultPage.getTotalElements());
        assertEquals(authorPage.getContent().size(), resultPage.getContent().size());
        // Add more assertions if needed
    }

    @Test
    void testUpdateUser() {
        // Given
        Long userId = 1L;
        PatronRequest patronRequest = new PatronRequest("author", "password", "John Doe", null);
        Patron oldPatronEntity = new Patron();
        oldPatronEntity.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(oldPatronEntity));
        when(userRepository.save(any(Patron.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        PatronDTO result = (PatronDTO) patronService.updateUser(userId, patronRequest);

        // Then
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("John Doe", result.getFullName());
    }

    @Test
    void testDeleteUser() {
        // Given
        Long userId = 1L;
        Patron authorEntity = new Patron();
        authorEntity.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(authorEntity));

        // When
        patronService.deleteUser(userId);

        // Then
        verify(userRepository, times(1)).deleteById(userId);
    }
}
