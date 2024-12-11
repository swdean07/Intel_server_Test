package com.busanit501.boot501.repository.search;

import com.busanit501.boot501.domain.Food;
import com.busanit501.boot501.domain.QFood;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

// 반드시 이름 작성시: 인터페이스이름 + Impl
// QuerydslRepositorySupport 의무 상속,
// 만든 인터페이스 구현하기.
public class FoodSearchImpl extends QuerydslRepositorySupport
        implements FoodSearch {


    // 부모 클래스 초기화, 사용하는 엔티티 클래스를 설정.
    // Food
    public FoodSearchImpl() {
        super(Food.class);
    }

    @Override
    // 자바 문법으로 , sql 문장 명령어 대체해서 전달중.
    // 1) where
    // 2) 페이징
    // 3) 제목, 내용, 조건절. 추가중.
    public Page<Food> search(Pageable pageable) {
        //예시,
        QFood food = QFood.food; // Q 도메인 객체, 엔티티 클래스(= 테이블)
        JPQLQuery<Food> query = from(food); // select * from food 한 결과와 비슷함.
        //select * from food 작성한 내용을 query 객체 형식으로 만듦.
        // 다양한 쿼리 조건을 이용할수 있음.
        // 예, where, groupby, join , pagination
        query.where(food.title.contains("3"));
        // =================================.,조건1

        // 제목, 작성자 검색 조건 추가,
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.or(food.title.contains("3"));// "3" 제목 임시
        booleanBuilder.or(food.content.contains("7"));// "3" 제목 임시
        // query, 해당 조건을 적용함.
        query.where(booleanBuilder);
        // 방법2, 추가 조건으로, fno 가 0보다 초과 하는 조건.
        query.where(food.fno.gt(0L));

        // =================================.,조건3

        // 페이징 조건 추가하기. qeury에 페이징 조건을 추가한 상황
        this.getQuerydsl().applyPagination(pageable, query);
        // =================================.,조건2

        // 해당 조건의 데이터를 가져오기,
        List<Food> list = query.fetch();
        // 해당 조건에 맞는 데이터의 갯수 조회.
        long total = query.fetchCount();
        //

        return null;
    }

    @Override
    //String[] types , "t", "c", "tc"
    public Page<Food> searchAll(String[] types, String keyword, Pageable pageable) {
        QFood food = QFood.food;
        JPQLQuery<Food> query = from(food);
        // select * from food
        if (types != null && types.length > 0 && keyword != null) {
            // 여러 조건을 하나의 객체에 담기.
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for (String type : types) {
                switch (type) {
                    case "t":
                        booleanBuilder.or(food.title.contains(keyword));
                    case "c":
                        booleanBuilder.or(food.content.contains(keyword));
                    case "w":
                        booleanBuilder.or(food.writer.contains(keyword));
                } // switch
            }// end for
            // where 조건을 적용해보기.
            query.where(booleanBuilder);
        } //end if
        // fno >0
        query.where(food.fno.gt(0L));
        // where 조건.

        // 페이징 조건,
        // 페이징 조건 추가하기. qeury에 페이징 조건을 추가한 상황
        this.getQuerydsl().applyPagination(pageable, query);

        // =============================================
        // 위의 조건으로,검색 조건 1) 페이징된 결과물 2) 페이징된 전체 갯수
        // 해당 조건의 데이터를 가져오기,
        List<Food> list = query.fetch();
        // 해당 조건에 맞는 데이터의 갯수 조회.
        long total = query.fetchCount();

        // 마지막, Page 타입으로 전달 해주기.
        Page<Food> result = new PageImpl<Food>(list, pageable, total);

        return result;
    }
}
