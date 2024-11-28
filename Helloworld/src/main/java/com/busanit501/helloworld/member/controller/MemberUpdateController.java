package com.busanit501.helloworld.member.controller;

import com.busanit501.helloworld.member.dto.MemberDTO;
import com.busanit501.helloworld.member.service.MemberService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Log4j2
@WebServlet(name = "MemberUpdateController",urlPatterns = "/member/update")
public class MemberUpdateController extends HttpServlet {
    // 서비스를 포함 하기. 의존하기.
    private MemberService memberService = MemberService.INSTANCE;
    // 날짜 포맷팅
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //화면 전달.
        // 서비스에서, 하나의 todo 더미 데이터를 조회 후,
        try {
            // 클릭한 게시글 번호를 가지고 와야함.
            Long mno = Long.parseLong(request.getParameter("mno"));
            MemberDTO memberDTO = memberService.get(mno);
            // 화면에 전달하기.
            request.setAttribute("dto", memberDTO);
            request.getRequestDispatcher("/WEB-INF/member/memberUpd.jsp")
                    .forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String finished = request.getParameter("finished");
        log.info("finished : " + finished);

        boolean checkFinished = false;

        if(finished.equals("on")){
            checkFinished = true;
        }
        MemberDTO memberDTO = MemberDTO.builder()
                .mid(String.valueOf(Long.valueOf(request.getParameter("mid"))))
                .mid(String.valueOf(Long.valueOf(request.getParameter("mpw"))))
                .mid(String.valueOf(Long.valueOf(request.getParameter("mname"))))
                .mid(String.valueOf(Long.valueOf(request.getParameter("uuid"))))
                .build();
        log.info("memberDTO 수정된 내용: " + memberDTO);
        // Controller -> Service
        try {
            memberService.update(memberDTO);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("doPost : 글쓰기 처리하는 로직, 디비 연결 전, 리스트로 이동함");
        response.sendRedirect("/food/list");

    }
}
