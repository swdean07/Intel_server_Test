package com.busanit501.boot501.service;

import com.busanit501.boot501.dto.FoodDTO;
import com.busanit501.boot501.dto.FoodListReplyCountDTO;
import com.busanit501.boot501.dto.PageRequestDTO;
import com.busanit501.boot501.dto.PageResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@SpringBootTest
public class ServiceTests {
    @Autowired
    private FoodService foodService;

    @Test
    public void testRegisterFood() {
        // 더미 데이터 필요, 임시 DTO 생성.
        FoodDTO foodDTO = FoodDTO.builder()
                .title("오늘 점심 메뉴는?")
                .content("파스타")
                .writer("홍상우")
                .regDate(LocalDateTime.now())
                .build();

        Long fno = foodService.register(foodDTO);
        log.info("입력한 게시글 번호: " + fno.toString());
    }

    @Test
    public void testSelectOneFood() {
        // 더미 데이터 필요, 임시 DTO 생성.
        Long fno = 703L;
        FoodDTO foodDTO= foodService.readOne(fno);
        log.info("testSelectOneBoard , 하나 조회 foodDTO: " + foodDTO.toString());
    }

    @Test
    public void testUpdateFood() {
        // 수정할 더미 데이터 필요,
        FoodDTO foodDTO = FoodDTO.builder()
                .fno(703L)
                .title("변경할 점심 메뉴는?")
                .content("스테이크")
                .build();
        foodService.update(foodDTO);

    }

    @Test
    public void testDeleteFood() {
        foodService.delete(33L);
    }

    @Test
    public void testSelectAllFood() {
        // 검색할 더미 데이터
        // 준비물 1) PageRequestDTO, 키워드, 페이지, 사이즈 정보가 다 있음.
        PageRequestDTO pageRequestDTO =
                PageRequestDTO.builder()
                        .page(1)
                        .type("tcw")
                        .keyword("푸드")
                        .size(10)
                        .build();

        PageResponseDTO<FoodDTO> list = foodService.list(pageRequestDTO);
        log.info("list: " + list.toString());
    }

    @Test
    @Transactional
    public void testSelectAllFoodWithReplyCount() {
        // 검색할 더미 데이터
        // 준비물 1) PageRequestDTO, 키워드, 페이지, 사이즈 정보가 다 있음.
        PageRequestDTO pageRequestDTO =
                PageRequestDTO.builder()
                        .page(1)
                        .type("tcw")
                        .keyword("샘플")
                        .size(10)
                        .build();

        PageResponseDTO<FoodListReplyCountDTO> list = foodService.listWithReplyCount(pageRequestDTO);
        log.info("list: " + list.toString());
    }
}
