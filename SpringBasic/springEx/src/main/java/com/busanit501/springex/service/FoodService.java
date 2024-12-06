package com.busanit501.springex.service;

import com.busanit501.springex.dto.FoodDTO;
import com.busanit501.springex.dto.PageRequestDTO;
import com.busanit501.springex.dto.PageResponseDTO;

import java.util.List;

public interface FoodService {
    void register(FoodDTO foodDTO);

    List<FoodDTO> getAll();

    PageResponseDTO<FoodDTO> getListWithPage(PageRequestDTO pageRequestDTO);

    FoodDTO getOne(Long fno);

    void delete(Long fno);

    void update(FoodDTO foodDTO);
}
