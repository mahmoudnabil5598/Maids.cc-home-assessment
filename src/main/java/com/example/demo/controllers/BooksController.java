package com.example.demo.controllers;

import com.example.demo.dtos.reponses.BookDTO;
import com.example.demo.dtos.requests.BookRequest;
import com.example.demo.services.BookService;
import com.example.demo.utils.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@Slf4j
@AllArgsConstructor
public class BooksController extends BaseController {

    private BookService bookService;

    @GetMapping
    @SuppressWarnings("unused")
    @Operation(summary = "Get All Books")
    @ApiResponse(responseCode = "200", description = "All books are retrieved successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = BookDTO.class))})
    public ResponseEntity<APIResponse<Page<BookDTO>>> getAllBooks(
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) String searchTerm) {

        Page<BookDTO> result = bookService.getAllBooks(searchTerm, pageSize, pageNumber, sortField, sortDirection);
        APIResponse<Page<BookDTO>> response = new APIResponse<>("All books are retrieved successfully", result, HttpStatus.OK);
        log.info("All books are retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @SuppressWarnings("unused")
    @PostMapping
    @Operation(summary = "Create Book")
    @ApiResponse(responseCode = "201", description = "Book is created successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = BookDTO.class))})
    public ResponseEntity<APIResponse<BookDTO>> createBook(@Valid @RequestBody BookRequest book) {
        BookDTO result = bookService.createBook(book);
        APIResponse<BookDTO> response = new APIResponse<>("Book is created successfully", result, HttpStatus.CREATED);
        log.info("Book is created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @SuppressWarnings("unused")
    @GetMapping("/{bookId}")
    @Operation(summary = "Get Book By Id")
    @ApiResponse(responseCode = "200", description = "Book is retrieved successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = BookDTO.class))})
    public ResponseEntity<APIResponse<BookDTO>> getBookById(
            @PathVariable Long bookId) {

        BookDTO result = bookService.getBookById(bookId);
        APIResponse<BookDTO> response = new APIResponse<>("Book with id " + bookId + " is retrieved successfully", result, HttpStatus.OK);
        log.info("Book with id " + bookId + " is retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @SuppressWarnings("unused")
    @PutMapping("/{id}")
    @Operation(summary = "Update Book By Id")
    @ApiResponse(responseCode = "200", description = "User is updated successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = BookDTO.class))})
    public ResponseEntity<APIResponse<BookDTO>> updateBook(@RequestBody BookRequest book, @PathVariable Long id) {
        BookDTO result = bookService.updateBook(id, book);
        APIResponse<BookDTO> response = new APIResponse<>("Book with id " + id + " is updated successfully", result, HttpStatus.OK);
        log.info("Book with id " + id + " is updated successfully");
        return ResponseEntity.ok(response);
    }

    @SuppressWarnings("unused")
    @DeleteMapping("/{bookId}")
    @Operation(summary = "Delete Book")
    @ApiResponse(responseCode = "204", description = "User is deleted successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = BookDTO.class))})
    public ResponseEntity<Void> DeleteBook(@PathVariable Long bookId) {

        bookService.deleteBook(bookId);
        log.info("Book with id " + bookId + " is deleted successfully");
        return ResponseEntity.noContent().build();
    }
}
