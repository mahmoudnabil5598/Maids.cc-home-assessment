package com.example.demo.controllers;

import com.example.demo.dtos.reponses.ContactInformationDTO;
import com.example.demo.dtos.requests.ContactInformationRequest;
import com.example.demo.services.ContactInformationService;
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
@RequestMapping("/contactInformation")
@Slf4j
@AllArgsConstructor
public class ContactInformationController extends BaseController {
    private ContactInformationService contactInformationService;

    @GetMapping
    @SuppressWarnings("unused")
    @Operation(summary = "Get All ContactInformation")
    @ApiResponse(responseCode = "200", description = "All Contact information are retrieved successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ContactInformationDTO.class))})
    public ResponseEntity<APIResponse<Page<ContactInformationDTO>>> getAllContactInformation(
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) String searchTerm) {

        Page<ContactInformationDTO> result = contactInformationService.getAllContactInformation(searchTerm, pageSize, pageNumber, sortField, sortDirection);
        APIResponse<Page<ContactInformationDTO>> response = new APIResponse<>("All contact information are retrieved successfully", result, HttpStatus.OK);
        log.info("All contactInformation are retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @SuppressWarnings("unused")
    @PostMapping
    @Operation(summary = "Create Contact Information")
    @ApiResponse(responseCode = "201", description = "Contact information is created successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ContactInformationDTO.class))})
    public ResponseEntity<APIResponse<ContactInformationDTO>> createContactInformation(@Valid @RequestBody ContactInformationRequest contactInformationRequest) {
        ContactInformationDTO result = contactInformationService.createContactInformation(contactInformationRequest);
        APIResponse<ContactInformationDTO> response = new APIResponse<>("Contact information is created successfully", result, HttpStatus.CREATED);
        log.info("Contact information is created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @SuppressWarnings("unused")
    @GetMapping("/{bookId}")
    @Operation(summary = "Get Contact information By Id")
    @ApiResponse(responseCode = "200", description = "Contact information is retrieved successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ContactInformationDTO.class))})
    public ResponseEntity<APIResponse<ContactInformationDTO>> getContactInformationById(
            @PathVariable Long bookId) {

        ContactInformationDTO result = contactInformationService.getContactInformationById(bookId);
        APIResponse<ContactInformationDTO> response = new APIResponse<>("Contact information with id " + bookId + " is retrieved successfully", result, HttpStatus.OK);
        log.info("Contact information with id " + bookId + " is retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @SuppressWarnings("unused")
    @PutMapping("/{id}")
    @Operation(summary = "Update Contact information By Id")
    @ApiResponse(responseCode = "200", description = "User is updated successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ContactInformationDTO.class))})
    public ResponseEntity<APIResponse<ContactInformationDTO>> updateContactInformation(@RequestBody ContactInformationRequest contactInformationRequest, @PathVariable Long id) {
        ContactInformationDTO result = contactInformationService.updateContactInformation(id, contactInformationRequest);
        APIResponse<ContactInformationDTO> response = new APIResponse<>("Contact information with id " + id + " is updated successfully", result, HttpStatus.OK);
        log.info("Contact information with id " + id + " is updated successfully");
        return ResponseEntity.ok(response);
    }

    @SuppressWarnings("unused")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Contact information")
    @ApiResponse(responseCode = "204", description = "User is deleted successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ContactInformationDTO.class))})
    public ResponseEntity<Void> DeleteContactInformation(@PathVariable Long id) {

        contactInformationService.deleteContactInformation(id);
        log.info("Contact information with id " + id + " is deleted successfully");
        return ResponseEntity.noContent().build();
    }
}
