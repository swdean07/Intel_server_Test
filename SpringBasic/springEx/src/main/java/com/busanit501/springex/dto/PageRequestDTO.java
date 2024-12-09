package com.busanit501.springex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDTO {
    // 웹 -> 서버
    //예시: http://localhost:8080/food/list?page=1&size=10
    // 화면에서 전달할 페이지 번호, 크기를 받을 상자필요.
    // 현재 페이지 번호
    @Builder.Default
    @Min(value = 1)
    @Positive
    private int page = 1;

    // 페이징당 출력할 데이터 갯수
    @Builder.Default
    @Min(value = 10)
    @Max(value = 100)
    @Positive
    private int size = 10;

    //목록 -> 상세보기 화면 이동시, 페이지 정보를 쿼리 스트링으로 전달하는 용도 멤버
    private String link;

    // 데이터를 얼마나 스킵 할지 정할 기능.
    public int getSkip() {
        return (page-1)*size;
    }

    // 페이징 정보를 쿼리 스트링 형식으로 반환 하는 메서드
    // 예시 ) http://localhost:8080/food/list&page=7&size=10
    // page=7&size=10 , 문자열 생성해주는 기능.
    public String getLink() {
        if(link==null){
            StringBuilder builder = new StringBuilder();
            builder.append("page=" + this.page);
            builder.append("&size=" + this.size);
            // link = "page=7&size=10"
            link = builder.toString();
        }
        return link;
    }
}




