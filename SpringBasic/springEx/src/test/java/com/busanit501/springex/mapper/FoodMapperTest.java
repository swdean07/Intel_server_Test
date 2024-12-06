package com.busanit501.springex.mapper;

import com.busanit501.springex.domain.FoodVO;
import com.busanit501.springex.dto.PageRequestDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@ExtendWith(SpringExtension.class) //JUnit5 테스트 설정.
//JUnit4 테스트 설정. @Runwith
// 설정 파일의 경로를 지정.
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/root-context.xml")
public class FoodMapperTest {

    // 해당 인스턴스가 없다면, 널로 받을게.
    // 기본값은 required = true
    @Autowired(required = false)
    private FoodMapper foodMapper;

    @Test
    public void testGetTime() {
        log.info("getTime : " + foodMapper.getTime());
    }

    @Test
    public void testInsert() {
        // 더미 데이터 , FoodVO 담아서, 진행.
        FoodVO foodVO = FoodVO.builder()
                .title("샘플 테스트")
                .dueDate(LocalDate.now())
                .writer("홍상우")
                .build();
        foodMapper.insert(foodVO);
    }

    @Test
    public void testSelectAll() {
        List<FoodVO> lists = foodMapper.selectAll();
        for (FoodVO foodVo : lists) {
            log.info("foodVo : " + foodVo);
        }
    }

    @Test
    public void testSelectOne() {
        FoodVO foodVO = foodMapper.selectOne(2L);
        log.info("foodVO : " + foodVO);
    }

    @Test
    public void testDelete() {
        foodMapper.delete(2L);
    }

    @Test
    public void testUpdate() {
        // 업데이트 할 더미 데이터 필요, FoodVO
        FoodVO foodVO = FoodVO.builder()
                .fno(2L)
                .title("수정 제목")
                .dueDate(LocalDate.now())
                .finished(true)
                .build();

        foodMapper.update(foodVO);
    }

    // 페이징 처리해서 전체 조회
    @Test
    public void testSelectAllWithPage() {
        // 페이징 준비물을 담은 PageRequestDTO 필요함,
        // 더미로 PageRequestDTO 만들고,
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(2)
                .size(10)
                .build();

        List<FoodVO> list = foodMapper.selectList(pageRequestDTO);
        list.forEach(vo -> log.info("vo : " + vo));
    }

    // 페이징 처리해서 전체 갯수 조회
    @Test
    public void testGetCount() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(150)
                .size(10)
                .build();
        int total = foodMapper.getCount(pageRequestDTO);
        log.info("total : " + total);
    }
}

