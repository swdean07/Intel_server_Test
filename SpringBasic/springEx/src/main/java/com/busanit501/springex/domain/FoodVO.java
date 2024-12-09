package com.busanit501.springex.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FoodVO {
    private Long fno;
    private String title;
    private LocalDate dueDate;
    private boolean finished;
    private String writer;
}
