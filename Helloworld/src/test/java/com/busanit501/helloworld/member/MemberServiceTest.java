package com.busanit501.helloworld.member;

import com.busanit501.helloworld.jdbcex.dto.TodoDTO;
import com.busanit501.helloworld.jdbcex.service.TodoService;

import com.busanit501.helloworld.member.dto.MemberDTO;
import com.busanit501.helloworld.member.service.MemberService;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Log4j2
public class MemberServiceTest {
    private MemberService memberService;

    @BeforeEach
    public void ready() {
        memberService = MemberService.INSTANCE;
    }

    // 등록
    @Test
    public void testInsert() throws SQLException {
        // 더미 데이터, 화면에서 전달 받은 TodoDTO
        MemberDTO memberDTO = MemberDTO.builder()
                .title("샘플 작업 1127")
                .dueDate(LocalDate.now())
                .build();
        memberService.register(memberDTO);
    }

    // 전체 조회
    @Test
    public void testSelectAll() throws SQLException {
        List<MemberDTO> dtoList = memberService.listAll();
        for (MemberDTO memberDto:dtoList) {
            log.info(memberDto);
        }
    }

    // 하나조회, 상세보기.
    @Test
    public void testSelectOne() throws SQLException {
        val memberDTO = memberService.get(14L);
        log.info("하나 조회. todoDTO " + memberDTO);
    }

    // 하나수정,
    @Test
    public void testUpdateOne() throws SQLException {
        //
        MemberDTO memberDTO = MemberDTO.builder()
                .mno(3L)
                .title("수정된 내용입니다.")
                .dueDate(LocalDate.now())
                .finished(false)
                .build();

        memberService.update(memberDTO);
    }

    // 하나삭제,
    @Test
    public void testDelteOne() throws SQLException {
        memberService.delete(5L);
    }
}
