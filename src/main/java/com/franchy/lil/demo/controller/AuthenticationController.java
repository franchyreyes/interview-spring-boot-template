package com.franchy.lil.demo.controller;

import com.franchy.lil.demo.dto.AuthenticationDTO;
import com.franchy.lil.demo.enums.Role;
import com.franchy.lil.demo.model.User;
import com.franchy.lil.demo.request.AuthenticationRequest;
import com.franchy.lil.demo.request.RegisterRequest;
import com.franchy.lil.demo.response.ApiResponse;
import com.franchy.lil.demo.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthenticationDTO>> register(@RequestBody RegisterRequest request) {
        var user =
                new User.Builder().setFirstname(request.firstName()).setLastname(request.lastName()).setEmail(request.email()).setPassword(this.service.encode(request.password())).setRole(Role.USER).build();

        AuthenticationDTO authenticationDTO = this.service.register(user);
        ApiResponse<AuthenticationDTO> response = new ApiResponse<>(true, "Resource created successfully",
                authenticationDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse<AuthenticationDTO>> authenticate(@RequestBody AuthenticationRequest request) {

        AuthenticationDTO authenticationDTO = this.service.authenticate(request);
        ApiResponse<AuthenticationDTO> response = new ApiResponse<>(true, "Get Token", authenticationDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
