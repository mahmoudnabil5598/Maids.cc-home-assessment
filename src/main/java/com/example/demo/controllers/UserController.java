package com.example.demo.controllers;

import com.example.demo.dtos.reponses.UserDTO;
import com.example.demo.dtos.requests.UserRequest;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import com.example.demo.utils.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Component
public abstract class UserController<T> extends BaseController {

    protected abstract UserService getService();
    

    @GetMapping
    @SuppressWarnings("unused")
    @Operation(summary = "Get All Users")
    @ApiResponse(responseCode = "200", description = "All Users are retrieved successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDTO.class))})
    public ResponseEntity<APIResponse<Page<UserDTO>>> getAllUsers(
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) String searchTerm) {

        Page<UserDTO> result = getService().getAllUsers(searchTerm, pageSize, pageNumber, sortField, sortDirection);
        APIResponse<Page<UserDTO>> response = new APIResponse<>("All Users are retrieved successfully", result, HttpStatus.OK);
        log.info("All Users are retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @SuppressWarnings("unused")
    @PostMapping
    @Operation(summary = "Create User")
    @ApiResponse(responseCode = "201", description = "Users is created successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDTO.class))})
    public ResponseEntity<APIResponse<UserDTO>> createUser(@Valid @RequestBody UserRequest user) {
        UserDTO result = getService().createUser(user);
        APIResponse<UserDTO> response = new APIResponse<>("User is created successfully", result, HttpStatus.CREATED);
        log.info("User is created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @SuppressWarnings("unused")
    @GetMapping("/{userId}")
    @Operation(summary = "Get User By Id")
    @ApiResponse(responseCode = "200", description = "User is retrieved successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDTO.class))})
    public ResponseEntity<APIResponse<UserDTO>> getUserById(
            @PathVariable Long userId) {

        UserDTO result = getService().getUserById(userId);
        APIResponse<UserDTO> response = new APIResponse<>("User with id " + userId + " is retrieved successfully", result, HttpStatus.OK);
        log.info("User with id " + userId + " is retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @SuppressWarnings("unused")
    @PutMapping("/{id}")
    @Operation(summary = "Update User By Id")
    @ApiResponse(responseCode = "200", description = "User is updated successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDTO.class))})
    public ResponseEntity<APIResponse<UserDTO>> updateUser(@RequestBody UserRequest user, @PathVariable Long id) {
        UserDTO result = getService().updateUser(id, user);
        APIResponse<UserDTO> response = new APIResponse<>("User with id " + id + " is updated successfully", result, HttpStatus.OK);
        log.info("User with id " + id + " is updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete User")
    @ApiResponse(responseCode = "204", description = "User is deleted successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDTO.class))})
    public ResponseEntity<Void> DeleteUser(@PathVariable Long userId) {

        getService().deleteUser(userId);
        log.info("User with id " + userId + " is deleted successfully");
        return ResponseEntity.noContent().build();
    }
}
