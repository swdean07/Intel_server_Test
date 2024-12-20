package com.busanit501.boot501.service;


import com.busanit501.boot501.dto.FoodDTO;
import com.busanit501.boot501.dto.FoodListReplyCountDTO;
import com.busanit501.boot501.dto.PageRequestDTO;
import com.busanit501.boot501.dto.PageResponseDTO;

public interface FoodService {
    Long register(FoodDTO foodDTO);
    FoodDTO readOne(Long fno);
    void update(FoodDTO foodDTO);
    void delete(Long fno);
    PageResponseDTO<FoodDTO> list(PageRequestDTO pageRequestDTO);

    PageResponseDTO<FoodListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);
}
