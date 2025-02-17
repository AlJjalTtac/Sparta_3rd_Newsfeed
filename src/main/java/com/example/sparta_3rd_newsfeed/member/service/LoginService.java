package com.example.sparta_3rd_newsfeed.member.service;

import com.example.sparta_3rd_newsfeed.common.entity.Login;
import com.example.sparta_3rd_newsfeed.member.dto.LoginResponseDto;
import com.example.sparta_3rd_newsfeed.member.repository.LoginRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;

    public LoginResponseDto login(String email, String password, HttpServletRequest request) {
        Login login = loginRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));

        if (!login.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("login", login);

        return new LoginResponseDto(login.getEmail() ,login.getPassword());

    }

    public static LoginResponseDto logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return new LoginResponseDto(null, "Logged out");
    }
}
