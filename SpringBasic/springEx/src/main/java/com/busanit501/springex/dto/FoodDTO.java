package com.busanit501.springex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodDTO {
    private Long fno;
    private String title;
    private LocalDate dueDate;
    private boolean finished;
    private String writer;
}
