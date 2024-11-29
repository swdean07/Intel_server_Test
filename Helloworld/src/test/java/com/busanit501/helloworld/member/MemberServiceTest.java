package com.busanit501.helloworld.member;

import com.busanit501.helloworld.member.dto.MemberDTO;
import com.busanit501.helloworld.member.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

@Log4j2
public class MemberServiceTest {
    private MemberService memberService;

    @BeforeEach
    public void ready() {
        memberService = MemberService.INSTANCE;
    }

    // 등록
    @Test
    public void loginTest() throws SQLException {
     MemberDTO memberDTO = memberService.login("swh", "1234");
     log.info("MemberService loginTest : " + memberDTO.toString());
    }

    // uuid 업데이트
    @Test
    public void updateUuidTest() throws SQLException {
        String uuid = UUID.randomUUID().toString();
        memberService.updateUuid("swh2",uuid);
    }

    // uuid 이용해서 검색해보기.
    @Test
    public void getMemberWithUuidSearch() throws SQLException {
        // 각자 테이블의 유저의uuid를 직접 복사해서 붙여넣기.
        // 각각 전부 다 달라요.
        MemberDTO memberDTO = memberService.getMemberWithUuidService("99df8683-2c3e-4c39-a0aa-a21a8f10ad73");
        log.info("memberDTO : " + memberDTO);
    }



}
