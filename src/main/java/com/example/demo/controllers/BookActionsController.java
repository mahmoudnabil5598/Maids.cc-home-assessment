package com.example.demo.controllers;


import com.example.demo.dtos.reponses.BookDTO;
import com.example.demo.services.BookService;
import com.example.demo.utils.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@AllArgsConstructor
public class BookActionsController extends BaseController {

    private BookService bookService;


    @SuppressWarnings("unused")
    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    @Operation(summary = "Borrow book")
    @ApiResponse(responseCode = "201", description = "Book is borrowed successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = BookDTO.class))})
    public ResponseEntity<APIResponse<String>> BorrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        bookService.BorrowBook(bookId, patronId);

        APIResponse<String> response = new APIResponse<>("Book is borrowed successfully",
                HttpStatus.CREATED);
        log.info("Book is borrowed successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @SuppressWarnings("unused")
    @PutMapping("/return/{bookId}/patron/{patronId}")
    @Operation(summary = "Return book")
    @ApiResponse(responseCode = "201", description = "Book is returned successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = BookDTO.class))})
    public ResponseEntity<APIResponse<String>> ReturnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        bookService.returnBorrowedBook(bookId, patronId);

        APIResponse<String> response = new APIResponse<>("Book is returned successfully",
                HttpStatus.OK);
        log.info("Book is returned successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
