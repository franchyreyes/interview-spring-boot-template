package com.franchy.lil.demo.controller;

import com.franchy.lil.demo.dto.AuthenticationDTO;
import com.franchy.lil.demo.dto.OrderDTO;
import com.franchy.lil.demo.enums.Role;
import com.franchy.lil.demo.model.User;
import com.franchy.lil.demo.request.AuthenticationRequest;
import com.franchy.lil.demo.request.RegisterRequest;
import com.franchy.lil.demo.response.ApiResponses;
import com.franchy.lil.demo.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("prod")
@Tag(name = "Authentication Endpoint", description = "Operations related to Authentication")
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }


    @Operation(summary = "Authentication - register a user")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create a user", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "403", description = "Not Authorize", content = @Content)})
    @PostMapping("/register")
    public ResponseEntity<ApiResponses<AuthenticationDTO>> register(@RequestBody RegisterRequest request) {
        var user =
                new User.Builder().setFirstname(request.firstName()).setLastname(request.lastName()).setEmail(request.email()).setPassword(this.service.encode(request.password())).setRole(Role.USER).build();

        AuthenticationDTO authenticationDTO = this.service.register(user);
        ApiResponses<AuthenticationDTO> response = new ApiResponses<>(true, "Resource created successfully",
                authenticationDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @Operation(summary = "Authentication - Authenticate a user")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get a token", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "403", description = "Not Authorize", content = @Content)})
    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponses<AuthenticationDTO>> authenticate(@RequestBody AuthenticationRequest request) {

        AuthenticationDTO authenticationDTO = this.service.authenticate(request);
        ApiResponses<AuthenticationDTO> response = new ApiResponses<>(true, "Get Token", authenticationDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
