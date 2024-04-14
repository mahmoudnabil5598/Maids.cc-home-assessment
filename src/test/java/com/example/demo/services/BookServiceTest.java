package com.example.demo.services;

import com.example.demo.dtos.reponses.AuthorDTO;
import com.example.demo.dtos.reponses.BookDTO;
import com.example.demo.dtos.reponses.PatronDTO;
import com.example.demo.dtos.requests.BookRequest;
import com.example.demo.entities.Book;
import com.example.demo.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class BookServiceTest {

    @Mock
    private BookRepository booksRepository;

    @Mock
    private PatronService patronService;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createBook_ValidBookRequest_ReturnsBookDTO() {
        // Arrange
        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle("Test Book");
        bookRequest.setPublicationYear(2022);

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1L);
        authorDTO.setFullName("Test Author");

        when(authorService.getUserById(1L)).thenReturn(authorDTO);

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setPublicationYear(2022);
        when(booksRepository.save(ArgumentMatchers.any(Book.class))).thenReturn(book);

        // Act
        BookDTO createdBookDTO = bookService.createBook(bookRequest);

        // Assert
        assertNotNull(createdBookDTO);
        assertEquals("Test Book", createdBookDTO.getTitle());
        assertEquals(2022, createdBookDTO.getPublicationYear());
    }

    @Test
    void getBookById_ExistingBookId_ReturnsBookDTO() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setPublicationYear(2022);

        when(booksRepository.findById(1L)).thenReturn(Optional.of(book));

        // Act
        BookDTO bookDTO = bookService.getBookById(1L);

        // Assert
        assertNotNull(bookDTO);
        assertEquals(1L, bookDTO.getId());
        assertEquals("Test Book", bookDTO.getTitle());
        assertEquals(2022, bookDTO.getPublicationYear());
    }

    @Test
    void getAllBooks_ValidParameters_ReturnsPageOfBookDTOs() {
        // Arrange
        List<Book> books = new ArrayList<>();
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book 1");
        book1.setPublicationYear(2022);
        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book 2");
        book2.setPublicationYear(2023);
        books.add(book1);
        books.add(book2);
        Page<Book> bookPage = new PageImpl<>(books);


        // Mock the userRepository
        when(booksRepository.findAllBooks(anyString(), ArgumentMatchers.any(Pageable.class))).thenReturn(bookPage);


        // Act
        Page<BookDTO> bookDTOPage = bookService.getAllBooks("", 10, 0, "title", "asc");

        // Assert
        assertNotNull(bookDTOPage);
        assertEquals(2, bookDTOPage.getTotalElements());
    }

    @Test
    void updateBook_ExistingBookIdAndValidBookRequest_ReturnsUpdatedBookDTO() {
        // Arrange
        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle("Updated Title");
        bookRequest.setPublicationYear(2023);

        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setTitle("Old Title");
        existingBook.setPublicationYear(2022);

        when(booksRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(booksRepository.save(ArgumentMatchers.any(Book.class))).thenReturn(existingBook);

        // Act
        BookDTO updatedBookDTO = bookService.updateBook(1L, bookRequest);

        // Assert
        assertNotNull(updatedBookDTO);
        assertEquals("Updated Title", updatedBookDTO.getTitle());
        assertEquals(2023, updatedBookDTO.getPublicationYear());
    }

    @Test
    void deleteBook_ExistingBookIdNotBorrowed_DeletesBook() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setIsBorrowed(false);

        when(booksRepository.findById(1L)).thenReturn(Optional.of(book));

        // Act
        assertDoesNotThrow(() -> bookService.deleteBook(1L));

        // Assert
        verify(booksRepository, times(1)).deleteById(1L);
    }

    @Test
    void BorrowBook_BookNotBorrowedAndValidIds_UpdatesBookBorrowedStatus() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setIsBorrowed(false);

        PatronDTO patronDTO = new PatronDTO();
        patronDTO.setId(1L);
        patronDTO.setFullName("Test Patron");

        when(booksRepository.findById(1L)).thenReturn(Optional.of(book));
        when(patronService.getUserById(1L)).thenReturn(patronDTO);

        // Act
        assertDoesNotThrow(() -> bookService.BorrowBook(1L, 1L));

        // Assert
        assertTrue(book.getIsBorrowed());
        assertEquals(1, book.getPatrons().size());
    }

    @Test
    void returnBorrowedBook_BookBorrowedAndValidIds_UpdatesBookReturnedStatus() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setIsBorrowed(true);

        PatronDTO patronDTO = new PatronDTO();
        patronDTO.setId(1L);
        patronDTO.setFullName("Test Patron");

        when(booksRepository.findById(1L)).thenReturn(Optional.of(book));
        when(patronService.getUserById(1L)).thenReturn(patronDTO);

        // Act

        assertDoesNotThrow(() -> bookService.returnBorrowedBook(1L, 1L));

        // Assert
        assertFalse(book.getIsBorrowed());
    }
}
