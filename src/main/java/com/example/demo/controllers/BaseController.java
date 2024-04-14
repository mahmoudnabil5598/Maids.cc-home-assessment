package com.example.demo.controllers;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.stereotype.Component;

@Component
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Bad request parameters",
                content = @Content),
        @ApiResponse(responseCode = "401", description = "User is not authenticated",
                content = @Content),
        @ApiResponse(responseCode = "403", description = "User is not authorized",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Entity not  not found",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content)})
@SuppressWarnings("unused")
public class BaseController {
}
