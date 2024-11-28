package com.busanit501.helloworld.member.controller;

import com.busanit501.helloworld.member.dto.MemberDTO;
import com.busanit501.helloworld.member.service.MemberService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

@Log4j2
@WebServlet(name = "LoginController", urlPatterns = "/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("LoginController doGet ");
        request.getRequestDispatcher("/WEB-INF/login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("LoginController doPost ");
        String mid = request.getParameter("mid");
        String mpw = request.getParameter("mpw");
        // auto, 자동로그인 체크 여부, -> 결과 문자열 : "on"
        String auto = request.getParameter("auto");
        // 상태 변수,
        boolean rememberMe = auto != null && auto.equals("on");

        // 랜덤한 문자열 생성. UUID, 중복 되지 않는 문자열을 랜덤하게 생성.
        if(rememberMe) {
            String uuid = UUID.randomUUID().toString();
        }

        // 디비에가서, 해당 유저가 있으면, 임시로 세션에 저장,
        // 예외처리도 없음.
        // 조금있다 할 예정.
        // 임의로 세션 동작 여부만 확인중.
        try {
            MemberDTO memberDTO = MemberService.INSTANCE.login(mid, mpw);
            // 세션에, 위의 로그인 정보를 저장,
            HttpSession session = request.getSession();
            session.setAttribute("loginInfo", memberDTO);
            response.sendRedirect("/todo/list2");
        } catch (SQLException e) {
            response.sendRedirect("/login?result=error");
        }

    }
}

