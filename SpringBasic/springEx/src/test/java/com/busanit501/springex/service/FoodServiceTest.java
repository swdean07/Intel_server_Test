package com.busanit501.springex.service;

import com.busanit501.springex.dto.FoodDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

@Log4j2
@ExtendWith(SpringExtension.class) //JUnit5 테스트 설정.
//JUnit4 테스트 설정. @Runwith
// 설정 파일의 경로를 지정.
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/root-context.xml")
//방법2
//@RequiredArgsConstructor
public class FoodServiceTest {

    // 방법1
        @Autowired
    private FoodService foodService;

    //방법2
//    private final FoodService foodService;

    @Test
    public void testRegister() {
        FoodDTO foodDTO = FoodDTO.builder()
                .title("샘플데이터 서비스에서 입력")
                .dueDate(LocalDate.now())
                .writer("이상용")
                .build();
        foodService.register(foodDTO);
    } //


}//
