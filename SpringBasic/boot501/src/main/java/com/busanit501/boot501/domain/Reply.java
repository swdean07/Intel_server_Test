package com.busanit501.boot501.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reply", indexes = {
        @Index(name = "idx_reply_food_fno", columnList = "food_fno")
})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString// db에 접근시, 2번접근 안함.
public class Reply extends FoodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;// 댓글의 구분 번호,

    //중요, 여기서 연관관계 설정, 외래키 설정,
    // 마치, 데이터베이스에서 설정하는 것과 비슷
    @ManyToOne(fetch = FetchType.LAZY) // 사용하는 시점에 조회함.
    // FetchType.EAGER , 즉시 로딩, 사용 안해도 조회 하겠다.
    private Food food; // 부모의 게시글 번호,

    private String replyText;

    private String replyer;

    public void changeFood(Food food) {
        this.food = food;
    }

    // 수정 시, 내용만 변경하기위해서 사용함, 불변성을 유지하기위한 조취
    public void changeReplyText(String replyText, String replyer) {
        this.replyText = replyText;
        this.replyer = replyer;
    }

}
