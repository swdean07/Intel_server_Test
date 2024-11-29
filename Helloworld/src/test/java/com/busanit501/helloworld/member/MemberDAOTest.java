package com.busanit501.helloworld.member;

import com.busanit501.helloworld.member.dao.MemberDAO;
import com.busanit501.helloworld.member.vo.MemberVO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

@Log4j2
public class MemberDAOTest {
    private MemberDAO memberDAO;

    // 아래에 각 단위 테스트가 실행되기전에, 먼저 실행을 함.
    @BeforeEach
    public void ready() {
        memberDAO = new MemberDAO();
    }

    @Test
    public void getMemberWithMpwTest() throws SQLException {
        String mid = "swh";
        String mpw = "1234";
        MemberVO memberVO = memberDAO.getMemberWithMpw(mid,mpw);
        log.info("memberVO 조회 확인: " +memberVO );
    }

    @Test
    public void updateUuidTest() throws SQLException {
        String uuid = UUID.randomUUID().toString();
        log.info("uuid 랜덤 문자열 샘플 : " + uuid);
        memberDAO.updateUuid("swh",uuid);

    }

    //uuid 로 유저 검색
    @Test
    public void getMemberWithUuidTest() throws SQLException {

        // 각자 테이블의 유저의uuid를 직접 복사해서 붙여넣기.
        // 각각 전부 다 달라요.
        MemberVO memberVO = memberDAO.getMemberWithUuid("99df8683-2c3e-4c39-a0aa-a21a8f10ad73");
        log.info("memberVO : " + memberVO);

    }

}
