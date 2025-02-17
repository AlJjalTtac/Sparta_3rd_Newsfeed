package com.example.sparta_3rd_newsfeed.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.ServerCloneException;

@Slf4j
public class LoginFilter implements Filter {
    // 인증을 하지 않아도될 URL Path 배열
    private static final String[] WHITE_LIST = {"/", "/member/signup", "/member/login", "/logout"};

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        // 다양한 기능을 사용하기 위해 다운 캐스팅
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        // 다양한 기능을 사용하기 위해 다운 캐스팅
        HttpServletResponse httpResponse = (HttpServletResponse) response;


        // 로그인을 체크 해야하는 URL인지 검사
        // whiteListURL에 포함된 경우 true 반환 -> !true = false
        if (!isWhiteList(requestURI)) {

            // 로그인 확인 -> 로그인하면 session에 값이 저장되어 있다는 가정.
            // 세션이 존재하면 가져온다. 세션이 없으면 session = null
            HttpSession session = httpRequest.getSession(false);

            // 로그인하지 않은 사용자인 경우
            if (session == null || session.getAttribute("sessionKey값") == null) {
                throw new RuntimeException("로그인 해주세요.");
            }
        }

        // 1번경우 : whiteListURL에 등록된 URL 요청이면 바로 chain.doFilter()
        // 2번경우 : 필터 로직 통과 후 다음 필터 호출 chain.doFilter()
        // 다음 필터 없으면 Servlet -> Controller 호출
        chain.doFilter(request, response);

    }

    // 로그인 여부를 확인하는 URL인지 체크하는 메서드
    private boolean isWhiteList(String requestURI) {
        // request URI가 whiteListURL에 포함되는지 확인
        // 포함되면 true 반환
        // 포함되지 않으면 false 반환
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}