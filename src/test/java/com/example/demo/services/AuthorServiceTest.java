package com.example.demo.services;


import static com.example.demo.utils.SystemUtil.pagination;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.dtos.reponses.AuthorDTO;
import com.example.demo.dtos.reponses.UserDTO;
import com.example.demo.dtos.requests.AuthorRequest;
import com.example.demo.entities.Author;
import com.example.demo.exceptions.BirthDateNotValidException;
import com.example.demo.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import static org.junit.jupiter.api.Assertions.assertThrows;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AuthorServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    void setUp() throws IllegalAccessException, NoSuchFieldException {
        MockitoAnnotations.openMocks(this);
        Field field = UserService.class.getDeclaredField("userRepository");
        field.setAccessible(true);
        field.set(authorService, userRepository);

        MockitoAnnotations.openMocks(this);
        Field field2 = UserService.class.getDeclaredField("bCryptPasswordEncoder");
        field2.setAccessible(true);
        field2.set(authorService, bCryptPasswordEncoder);
    }

    @Test
    void testCreateUser_ValidData_Success() {
        // Given
        AuthorRequest authorRequest = new AuthorRequest("author", "password", "John Doe", new Date(1123847746L), "bio");

        Author authorEntity = new Author();

        //set fields
        authorEntity.setId(1L);
        authorEntity.setBio("bio");
        authorEntity.setUserName(authorRequest.getUserName());
        authorEntity.setFullName(authorRequest.getFullName());
        authorEntity.setBirthDate(authorRequest.getBirthDate());

        when(userRepository.save(any(Author.class))).thenReturn(authorEntity);

        // When
        UserDTO result = authorService.createUser(authorRequest);
        // Then
        assertNotNull(result);
        assertEquals("John Doe", result.getFullName());
        assertEquals("author", result.getUserName());
        assertEquals(new Date(1123847746L), result.getBirthDate());

    }

    @Test
    void testCreateUser_InvalidBirthDate_ExceptionThrown() {
        // Given
        AuthorRequest authorRequest = new AuthorRequest("author", "password", "John Doe", new Date(Instant.now().toEpochMilli() + 9000000000L), "bio");

        // When / Then
        BirthDateNotValidException exception = assertThrows(BirthDateNotValidException.class, () -> {
            authorService.createUser(authorRequest);
        });
        assertEquals("birthdate must be in the past", exception.getMessage());
    }

    @Test
    void testCreateUser_Check_Password_Encoding() {
        // Given
        AuthorRequest authorRequest = new AuthorRequest("author", "password", "John Doe", new Date(1123847746L), "bio");
        Author authorEntity = new Author();

        authorEntity.setPassword(authorRequest.getPassword());
        authorEntity.setFullName(authorRequest.getFullName());
        authorEntity.setBirthDate(authorRequest.getBirthDate());
        authorEntity.setPassword(authorRequest.getPassword());

        // When
        when(userRepository.save(any(Author.class))).thenReturn(authorEntity);

        authorService.createUser(authorRequest);
        // Then
        verify(bCryptPasswordEncoder).encode(authorRequest.getPassword());
    }


    @Test
    void testGetUserById() {
        // Given
        Long userId = 1L;
        Author authorEntity = new Author();
        authorEntity.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(authorEntity));

        // When
        AuthorDTO result = (AuthorDTO) authorService.getUserById(userId);

        // Then
        assertNotNull(result);
        assertEquals(userId, result.getId());
    }

    @Test
    void testGetAllUsers() {
        // Given
        String searchTerm = "author";
        Integer pageSize = 10;
        Integer pageNumber = 0;
        String sortField = "fullName";
        String sortDirection = "asc";
        Pageable paginate = pagination(sortField, sortDirection, pageNumber, pageSize);

        // Mock the Page object
        List<Author> authors = new ArrayList<>();
        Author author1 = new Author();
        author1.setUserName("author1");
        author1.setFullName("John Doe");
        author1.setBirthDate(new Date());

        Author author2 = new Author();
        author2.setUserName("author2");
        author2.setFullName("Jane Smith");
        author2.setBirthDate(new Date());

        authors.add(author1);
        authors.add(author2);
        Page<Author> authorPage = new PageImpl<>(authors, paginate, authors.size());

        // Mock the userRepository
        when(userRepository.findAllAuthors((searchTerm.toLowerCase()), (paginate))).thenReturn(authorPage);

        // When
        Page<UserDTO> resultPage = authorService.getAllUsers(searchTerm, pageSize, pageNumber, sortField, sortDirection);

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
        AuthorRequest authorRequest = new AuthorRequest("author", "password", "John Doe", null, "bio");
        Author oldAuthorEntity = new Author();
        oldAuthorEntity.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(oldAuthorEntity));
        when(userRepository.save(any(Author.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        AuthorDTO result = (AuthorDTO) authorService.updateUser(userId, authorRequest);

        // Then
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("John Doe", result.getFullName());
    }

    @Test
    void testDeleteUser() {
        // Given
        Long userId = 1L;
        Author authorEntity = new Author();
        authorEntity.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(authorEntity));

        // When
        authorService.deleteUser(userId);

        // Then
        verify(userRepository, times(1)).deleteById(userId);
    }
}
