package com.busanit501.helloworld.jdbcex.controller;

import com.busanit501.helloworld.jdbcex.dto.TodoDTO;
import com.busanit501.helloworld.jdbcex.service.TodoService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
@Log4j2
@WebServlet(name = "TodoRead2Controller", urlPatterns = "/todo/read2")
public class TodoRead2Controller extends HttpServlet {
    private TodoService todoService = TodoService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("doGet TodoRead2Controller 하나 조회 예제");

        //사용자가 , 클릭한 게시글 번호를 , 서버에서 쿠키를 생성해서 전달.
        // 프로세스,
        // 특정이름, viewTodos 키이름이 존재하는지, 없다면 쿠키 생성.
        // 만약 있다면, 계속 , 사용자가 조회한 todo 게시글 번호를 추가 할예정.
        // 쿠키가 널이면, 쿠키를 생성, 유효성 체크.

        // 서비스에서, 하나의 todo 더미 데이터를 조회 후,
        try {
            // 클릭한 게시글 번호를 가지고 와야함.
            Long tno = Long.parseLong(request.getParameter("tno"));
            // 컨트롤러 -> 서비스 -> DAO -> DB
            // 컨트롤러 <- 서비스 <- DAO <- DB
            TodoDTO todoDTO = todoService.get(tno);
            // 화면에 전달하기.
            request.setAttribute("dto", todoDTO);

            //1) 쿠키 조회하기. - 전체 쿠키 내용중에서, 내가 찾는 쿠키가 있니?
//            findCookie(전체 쿠키 배열, 찾고자하는 쿠키 이름) -> 반환 해당 쿠키,
           Cookie findCookie = findCookie(request.getCookies(), "viewTodos");
           // 쿠키의 값을 조회하기.
           String cookieValue = findCookie.getValue();
           log.info("cookieValue : " + cookieValue);
           // 상태 변수,
            // 조회한 게시글 번호 ->예시) cookieValue =  "1-3-5-7-"
            boolean exists = false;
            // 기본 유효성 체크, 쿠키의 존재 및, 내용의 존재여부 확인
            if(cookieValue != null && cookieValue.indexOf(tno+"-") >=0) {
                log.info("cookieValue.indexOf(tno+\"-\") " + cookieValue.indexOf(tno+"-"));
                exists = true;
            }
            // 만약에, 쿠키의 내용이 없다면, 내용을 추가하기.
            if(!exists) {
                cookieValue += tno+"-";
                findCookie.setValue(cookieValue);
                findCookie.setMaxAge(60*60*24);
                findCookie.setPath("/");
                // 무조건 서버에서 응답 해주기.
                response.addCookie(findCookie);
            }

            request.getRequestDispatcher("/WEB-INF/todo/todoRead2.jsp")
                    .forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    } // doGet

    // findCookie , 메서드 추가.
    // 역할, 1) 찾는 쿠키 이름의 쿠키를 반환
    // 2) 쿠키가 없다면 쿠키를 생성.(쿠키 이름: viewTodos)
    private Cookie findCookie(Cookie[] cookies, String name) {
        Cookie findCookie = null;
        // 쿠키가 있는 경우
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                // cookie.getName(): 전체 쿠키 목록 요소의 이름
                // name : 찾고자하는 쿠키 이름.
                if (cookie.getName().equals(name)) {
                    findCookie = cookie;
                    break;
                } //if
            } // for
        } // if

        // 2, 쿠키가 없다면, -> 생성하기.
        if (findCookie == null) {
            findCookie = new Cookie("viewTodos", "");
            findCookie.setPath("/");
            findCookie.setMaxAge(60*60*24);
        }

        return findCookie;
    } // method


}// class
