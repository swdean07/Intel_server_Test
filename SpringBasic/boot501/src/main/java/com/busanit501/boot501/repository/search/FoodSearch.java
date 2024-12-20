package com.busanit501.boot501.repository.search;

import com.busanit501.boot501.domain.Food;
import com.busanit501.boot501.dto.FoodListReplyCountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// Querydsl 이용시, 해당 인터페이스에서, Jq
public interface FoodSearch {
    // 연습용으로, 자바 문법으로 SQL 문장 전달해보기.
    Page<Food> search(Pageable pageable);

    //String[] types , "t", "c", "tc"
    // Pageable -> 페이징 하기 위한 재료. 현재 페이지, 페이지 보여줄 갯수, 정렬
    // Page -> 1) 페이징된 결과물 10개 2) 전체 갯수 3) 현제 페이지, 등. 정보 조회 가능.
    Page<Food> searchAll(String[] types, String keyword, Pageable pageable);

    Page<FoodListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable);
}
