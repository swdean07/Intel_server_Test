package com.busanit501.helloworld.food;

import com.busanit501.helloworld.food.dto.FoodDTO;
import com.busanit501.helloworld.food.service.FoodService;
import com.busanit501.helloworld.jdbcex.dto.MemberDTO;
import com.busanit501.helloworld.jdbcex.dto.TodoDTO;
import com.busanit501.helloworld.jdbcex.service.TodoService;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle.title;

@Log4j2
public class FoodServiceTest {
    private FoodService foodService;

    @BeforeEach
    public void ready() {
        foodService = FoodService.INSTANCE;
    }

    // 등록
    @Test
    public void testInsert() throws SQLException {
        // 더미 데이터, 화면에서 전달 받은 TodoDTO
        FoodDTO foodDTO = FoodDTO.builder()
                .title("샘플 작업 1127, 1교시")
                .dueDate(LocalDate.now())
                .build();
        foodService.register(foodDTO);
    }

    // 전체 조회
    @Test
    public void testSelectAll() throws SQLException {
         List<FoodDTO> dtoList = foodService.listAll();
        for (FoodDTO foodDTO:dtoList) {
            log.info(foodDTO);
        }
    }

    // 하나조회, 상세보기.
    @Test
    public void testSelectOne() throws SQLException {
        FoodDTO foodDTO = foodService.get(9L);
        log.info("하나 조회. foodDTO " + foodDTO);
    }
    // 하나수정,
    @Test
    public void testUpdateOne() throws SQLException {
        //
        FoodDTO foodDTO = FoodDTO.builder()
                .fno(3L)
                .title("수정된 내용입니다.7777777777777777777777")
                .dueDate(LocalDate.now())
                .finished(false)
                .build();

        foodService.update(foodDTO);
    }
    // 하나삭제,
    @Test
    public void testDelteOne() throws SQLException {
        foodService.delete(4L);
    }

    // 등록
    @Test
    public void loginTest() throws SQLException {
        // 로그인 서비스 호출
        FoodDTO foodDTO = foodService.login(1L, "swh",
                LocalDate.of(2024, 11, 29),
                "uuid", Boolean.valueOf("finished"));

        if (foodDTO != null) {
            log.info("FoodService loginTest : " + foodDTO.toString());
        } else {
            log.warn("FoodService loginTest : No data found for the provided parameters.");
        }
    }


    // uuid 업데이트
    @Test
    public void updateUuidTest() throws SQLException {
        Long fno = Long.valueOf("1");
        String title = "swh";
        LocalDate dueDate = LocalDate.parse(LocalDate.now().toString());
        String uuid = UUID.randomUUID().toString();
        Boolean finished = Boolean.valueOf("");
        log.info("uuid 랜덤 문자열 샘플 : " + uuid);
        try {
            foodService.updateUuid(fno, title, dueDate, uuid, Boolean.valueOf(finished));
            log.info("UUID 업데이트 성공: fno=" + fno + ", title=" + title + ", uuid=" + uuid);
        } catch (SQLException e) {
            log.error("SQLException during updateUuidTest: " + e.getMessage());
            throw e;
        }
    }

    // uuid 이용해서 검색해보기.
    @Test
    public void getFoodWithUuidService() throws SQLException {
        // 각자 테이블의 유저의uuid를 직접 복사해서 붙여넣기.
        // 각각 전부 다 달라요.
        foodService.getFoodWithUuidService("8debfc50-226c-4f25-a648-3e791aa80cb7");
    }

}
