package com.busanit501.boot501.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodDTO {
    private  Long fno;
    private  String title;
    private  String content;
    private  String writer;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
