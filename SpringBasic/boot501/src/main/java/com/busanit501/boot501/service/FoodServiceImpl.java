package com.busanit501.boot501.service;

import com.busanit501.boot501.domain.Food;
import com.busanit501.boot501.dto.FoodDTO;
import com.busanit501.boot501.dto.PageRequestDTO;
import com.busanit501.boot501.dto.PageResponseDTO;
import com.busanit501.boot501.repository.FoodRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class FoodServiceImpl implements FoodService {

    //맵퍼에게 의존 해야함.
    // 디비 작업 도구,
    private final FoodRepository foodRepository;
    // DTO <-> Entity class
    private final ModelMapper modelMapper;

    @Override
    public Long register(FoodDTO foodDTO) {
        Food food = modelMapper.map(foodDTO, Food.class);
        Long fno = foodRepository.save(food).getFno();
        return fno;
    }

    @Override
    public FoodDTO readOne(Long fno) {
        Optional<Food> result = foodRepository.findById(fno);
        Food food = result.orElseThrow();
        FoodDTO dto = modelMapper.map(food, FoodDTO.class);
        return dto;
    }

    @Override
    public void update(FoodDTO foodDTO) {
        Optional<Food> result = foodRepository.findById(foodDTO.getFno());
        Food food = result.orElseThrow();
        food.changeTitleConent(foodDTO.getTitle(),foodDTO.getContent());
        foodRepository.save(food);
    }

    @Override
    public void delete(Long fno) {
        foodRepository.deleteById(fno);
    }

    @Override
    public PageResponseDTO<FoodDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("fno");

        Page<Food> result = foodRepository.searchAll(types,keyword,pageable);
        // list -> PageResponseDTO 타입으로 변경 필요.

        // result.getContent() -> 페이징된 엔티티 클래스 목록
        List<FoodDTO> dtoList = result.getContent().stream()
                .map(food ->modelMapper.map(food, FoodDTO.class))
                .collect(Collectors.toList());


        return PageResponseDTO.<FoodDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();

    } // list
}

