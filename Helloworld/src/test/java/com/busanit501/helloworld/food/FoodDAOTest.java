package com.busanit501.helloworld.food;

import com.busanit501.helloworld.food.dao.FoodDAO;
import com.busanit501.helloworld.food.vo.FoodVO;
import com.busanit501.helloworld.jdbcex.vo.MemberVO;
import com.busanit501.helloworld.jdbcex.vo.TodoVO;
import jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Log4j2
public class FoodDAOTest {
    private FoodDAO foodDAO;

    // 아래에 각 단위 테스트가 실행되기전에, 먼저 실행을 함.
    @BeforeEach
    public void ready() {
        foodDAO = new FoodDAO();
    }


    @Test
    public void insetTest() throws Exception {
        FoodVO foodVO = FoodVO.builder()
                .title("샘플 데이터 추가1234")
                .dueDate(LocalDate.now())
                .finished(false)
                .build();

        foodDAO.insert(foodVO);
    }

    //2, 전체 목록 조회 테스트
    @Test
    public void testList() throws SQLException {
        List<FoodVO> list = foodDAO.selectAll();
        list.forEach(vo -> System.out.println(vo));
    }

    //3, 하나 조회 테스트
    @Test
    public void getOneTest() throws SQLException {
        Long fno = 9L;
        FoodVO foodVO = foodDAO.selectOne(fno);
        log.info(foodVO);
    }

    // 4, 수정 테스트
    @Test
    public void updateTest() throws SQLException {
        // 실제 작업은 내용을 화면에서 받아오는 대신,
        // 하드 코딩으로 값을 더미로 테스트.
        FoodVO foodVO = FoodVO.builder()
                .fno(3L)
                .title("수정 테스트 중222222222")
                .finished(true)
                .dueDate(LocalDate.of(2024, 11, 25))
                .build();

        foodDAO.updateOne(foodVO);

    }

    // 5, 삭제 테스트
    @Test
    public void deleteTest() throws SQLException {
        Long fno = 3L;
        foodDAO.deleteFood(fno);
    }

    @Test
    public void getFoodWithFno() throws SQLException {
        Long fno = 1L;
        String title = "swh";
        LocalDate dueDate = LocalDate.now();
        String uuid = UUID.randomUUID().toString();
        Boolean finished = false;

        // foodDAO.getFoodWithFno() 호출
        FoodVO foodVO = foodDAO.getFoodWithFno(fno, title, dueDate, uuid, finished);

        // 조회된 foodVO를 로그에 출력
        log.info("foodVO 조회 확인: " + foodVO);
    }

    //로그인시, 유저의 uuid 업데이트 하기.
    @Test
    public void updateUuidTest() throws SQLException {
        Long fno = 1L;
        String title = "swh";
        LocalDate dueDate = LocalDate.now();
        String uuid = UUID.randomUUID().toString();
        Boolean finished = false;

        log.info("uuid 랜덤 문자열 샘플 : " + uuid);

        foodDAO.updateUuid(fno, title, dueDate, uuid, finished);
    }


    //uuid 로 유저 검색
    @Test
    public void getFoodWithUuide() throws SQLException {

        // 각자 테이블의 유저의uuid를 직접 복사해서 붙여넣기.
        // 각각 전부 다 달라요.
        FoodVO foodVO = foodDAO.getFoodWithUuid("8debfc50-226c-4f25-a648-3e791aa80cb7");
        log.info("foodVO : " + foodVO);

    }

}
