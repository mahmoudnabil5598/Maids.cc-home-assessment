package com.example.demo.services;

import com.example.demo.dtos.reponses.AuthorDTO;
import com.example.demo.dtos.reponses.BookDTO;
import com.example.demo.dtos.reponses.PatronDTO;
import com.example.demo.dtos.requests.BookRequest;
import com.example.demo.entities.Author;
import com.example.demo.entities.Book;
import com.example.demo.entities.Patron;
import com.example.demo.exceptions.BookIsAlreadyBorrowedException;
import com.example.demo.exceptions.BookIsNotBorrowed;
import com.example.demo.exceptions.PublicationYearInTheFutureException;
import com.example.demo.repositories.BookRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.example.demo.utils.SystemUtil.*;
import static com.example.demo.utils.SystemUtil.unwrapEntity;

@Service
@AllArgsConstructor
public class BookService {


    private BookRepository booksRepository;

    private PatronService patronService;

    private AuthorService authorService;

    @Transactional
    public BookDTO createBook(BookRequest bookRequest) {

        System.out.println(new Date().getYear());
        if (bookRequest.getPublicationYear() > Year.now().getValue())
            throw new PublicationYearInTheFutureException();

        Book book = new Book();
        BeanUtils.copyProperties(bookRequest, book);


        Set<Author> authors = new HashSet<Author>();

        for (Long id : bookRequest.getAuthors()) {
            AuthorDTO authorDTO = (AuthorDTO) authorService.getUserById(id);
            Author A = new Author();
            BeanUtils.copyProperties(authorDTO, A);
            authors.add(A);
        }
        book.setAuthors(authors);
        book = booksRepository.save(book);
        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(book, bookDTO);
        return bookDTO;
    }

    public BookDTO getBookById(Long id) {
        Book book = unwrapEntity(booksRepository.findById(id), id, "Book");
        return convertToDetailedDTO(book);
    }

    public Page<BookDTO> getAllBooks(String searchTerm, Integer pageSize, Integer pageNumber, String sortField, String sortDirection) {
        Pageable paginate = pagination(sortField, sortDirection, pageNumber, pageSize);
        searchTerm = searchTerm == null ? "" : searchTerm.toLowerCase();
        return booksRepository.findAllBooks(searchTerm, paginate).map(this::convertToDto);
    }

    public BookDTO updateBook(Long id, BookRequest bookRequest) {

        Book oldBook = unwrapEntity(booksRepository.findById(id), id, "Book");
        copyNonNullProperties(bookRequest, oldBook);
        oldBook = booksRepository.save(oldBook);
        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(oldBook, bookDTO);
        return bookDTO;
    }

    public BookDTO convertToDto(Book book) {
        BookDTO response = new BookDTO();
        BeanUtils.copyProperties(book, response);
        return response;
    }

    public final void deleteBook(Long id) {
        Book book = unwrapEntity(booksRepository.findById(id), id, "book");
        // check if the book is borrowed
        if (book.getIsBorrowed())
            throw new BookIsAlreadyBorrowedException(id);
        booksRepository.deleteById(id);
    }

    public void BorrowBook(Long bookId, Long patronId) {

        Book book = unwrapEntity(booksRepository.findById(bookId), bookId, "book");
        // check if the book is borrowed
        if (book.getIsBorrowed())
            throw new BookIsAlreadyBorrowedException(bookId);

        PatronDTO patronDTO = (PatronDTO) patronService.getUserById(patronId);
        Patron P = new Patron();
        BeanUtils.copyProperties(patronDTO, P);

        book.setIsBorrowed(true);
        Set<Patron> patrons = book.getPatrons();
        patrons.add(P);
        book.setPatrons(patrons);
        book.setCurrentPatron(P);

        booksRepository.save(book);

    }

    public void returnBorrowedBook(Long bookId, Long patronId) {

        Book book = unwrapEntity(booksRepository.findById(bookId), bookId, "book");
        // check if the book is borrowed
        if (!book.getIsBorrowed())
            throw new BookIsNotBorrowed(bookId);

        PatronDTO patronDTO = (PatronDTO) patronService.getUserById(patronId);
        Patron P = new Patron();
        BeanUtils.copyProperties(patronDTO, P);

        book.setIsBorrowed(false);
        book.setCurrentPatron(null);

        booksRepository.save(book);

    }
}
