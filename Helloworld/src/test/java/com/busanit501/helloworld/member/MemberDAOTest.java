package com.busanit501.helloworld.member;

import com.busanit501.helloworld.member.dao.MemberDAO;
import com.busanit501.helloworld.member.vo.MemberVO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Log4j2
public class MemberDAOTest {
    private MemberDAO memberDAO;

    // 아래에 각 단위 테스트가 실행되기전에, 먼저 실행을 함.
    @BeforeEach
    public void ready() {
        memberDAO = new MemberDAO();
    }


    @Test
    public void insetTest() throws Exception {
        MemberVO memberVO = MemberVO.builder()
                .title("샘플 데이터 추가1234")
                .dueDate(LocalDate.now())
                .finished(false)
                .build();

        memberDAO.insert(memberVO);
    }

    //2, 전체 목록 조회 테스트
    @Test
    public void testList() throws SQLException {
        List<MemberVO> list = memberDAO.selectAll();
        list.forEach(vo -> System.out.println(vo));
    }

    //3, 하나 조회 테스트
    @Test
    public void getOneTest() throws SQLException {
        Long mno = 5L;
        MemberVO memberVO = memberDAO.selectOne(mno);
        log.info(memberVO);
    }

    // 4, 수정 테스트
    @Test
    public void updateTest() throws SQLException {
        // 실제 작업은 내용을 화면에서 받아오는 대신,
        // 하드 코딩으로 값을 더미로 테스트.
        MemberVO memberVO = MemberVO.builder()
                .mno(3L)
                .title("수정 테스트 중")
                .finished(true)
                .dueDate(LocalDate.of(2024, 11, 27))
                .build();

        memberDAO.updateOne(memberVO);

    }

    // 5, 삭제 테스트
    @Test
    public void deleteTest() throws SQLException {
        Long mno = 3L;
        memberDAO.deleteMember(mno);
    }

}
