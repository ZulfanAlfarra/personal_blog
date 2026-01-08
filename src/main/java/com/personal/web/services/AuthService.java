package com.personal.web.services;

import com.personal.web.dtos.LoginDto;
import com.personal.web.dtos.RegisterDto;
import com.personal.web.dtos.UserDto;
import com.personal.web.entities.User;
import com.personal.web.exceptions.ConflictException;
import com.personal.web.exceptions.DuplicateException;
import com.personal.web.mappers.UserMapper;
import com.personal.web.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto signup(RegisterDto req){
        if(userRepository.existsByUsername(req.username())) {
            throw new DuplicateException("Username is already exists");
        }

        User user = userMapper.toEntity(req);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return userMapper.toDtoResponse(user);
    }

    public String login(LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password())
        );

        User user = (User) authentication.getPrincipal();
        String token = jwtService.createToke(user);

        return token;
    }
}
