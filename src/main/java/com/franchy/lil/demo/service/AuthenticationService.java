package com.franchy.lil.demo.service;

import com.franchy.lil.demo.dto.AuthenticationDTO;
import com.franchy.lil.demo.model.User;
import com.franchy.lil.demo.repository.jpa.UserRepository;
import com.franchy.lil.demo.request.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                 JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public String encode(String plainText) {
        return this.passwordEncoder.encode(plainText);
    }

    public AuthenticationDTO register(User user) {
        this.userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationDTO(jwtToken);
    }

    public AuthenticationDTO authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(),
                request.password()));
        var user = this.userRepository.findByEmail(request.email()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationDTO(jwtToken);
    }

}
