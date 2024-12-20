package com.busanit501.boot501.repository.search;

import com.busanit501.boot501.domain.Food;
import com.busanit501.boot501.domain.QFood;
import com.busanit501.boot501.domain.QReply;
import com.busanit501.boot501.dto.FoodListReplyCountDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
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
    // Board
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
                        break;
                    case "c":
                        booleanBuilder.or(food.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(food.writer.contains(keyword));
                        break;
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

    @Override
    // 데이터베이스 형식으로, 단방향으로 참조 중.
    // 댓글 -> 게시글 방향으로 조회 중.
    // 즉, 반대 방향으로 조회를 못함.
    // 게시글 테이블과, 댓글 테이블 2개를 조인
    // 내부 조인(널 표기 안했음.),
    // 외부 조인,(널 표기 같이 해야함.) ,
    public Page<FoodListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {
        QFood food = QFood.food;
        QReply reply = QReply.reply;
        JPQLQuery<Food> query = from(food);// select * from food
        // 조인 설정 , 게시글에서 댓글에 포함된 게시글 번호와 , 게시글 번호 일치
        query.leftJoin(reply).on(reply.food.fno.eq(food.fno));
        // fno  title  content writer , rno  fno  replyText replyer
        // 121   test   test    swh      1    121    댓글    댓글작성자
        // 121   test   test    swh      2    121    댓글2    댓글작성자2

        // 120   test3   test3    swh3   3    120    댓글33    댓글작성자33
        // 120   test3   test3    swh3   4    120    댓글44    댓글작성자44
        // groupby 설정,
        query.groupBy(food);

        // 위에서 사용한 검색 조건 , 재사용하기
        if (types != null && types.length > 0 && keyword != null) {
            // 여러 조건을 하나의 객체에 담기.
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for (String type : types) {
                switch (type) {
                    case "t":
                        booleanBuilder.or(food.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(food.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(food.writer.contains(keyword));
                        break;
                } // switch
            }// end for
            // where 조건을 적용해보기.
            query.where(booleanBuilder);
        } //end if
        // fno >0
        query.where(food.fno.gt(0L));
        // 위에서 사용한 검색 조건 , 재사용하기

        // 모델 맵핑 작업, DTO <-> 엔티티 클래스간에 형변환을 해야함.
        // 기존에는 서비스 계층에서,  dto-entity 변환,


        // query -> 조인이 된 상태이고, 그룹별로 분할 된상태.
        // Querydsl 에서, 데이터 조회 후, 바로 형변환을 자동으로 지원.
        // Projections.bean 기능을 이용하면,
        // modelMapper.map(foodDTO, Food.class)
        JPQLQuery<FoodListReplyCountDTO> dtoQuery =
                query.select(Projections.bean(FoodListReplyCountDTO.class,
                        food.fno,
                        food.title,
                        food.content,
                        food.writer,
                        food.regDate,
                        reply.count().as("replyCount")));

        // 페이징 조건, 재사용, dto<-> 엔티티 형변환 할 때 사용했던 쿼리로 변
        // query -> dtoQuery
        // dtoQuery
        // fno  title  content writer , replyCount
        // 121   test   test    swh       2
        // 120   test3  test3   swh3      2

        // 위에 게시글에 , 댓글 갯수를 포함한 결과에 페이징 처리를 적용하는 코드
        this.getQuerydsl().applyPagination(pageable, dtoQuery);

        // =============================================
        // 위의 조건으로,검색 조건 1) 페이징된 결과물 2) 페이징된 전체 갯수
        // 해당 조건의 데이터를 가져오기,
        List<FoodListReplyCountDTO> list = dtoQuery.fetch();
        // 해당 조건에 맞는 데이터의 갯수 조회.
        long total = dtoQuery.fetchCount();

        // 마지막, Page 타입으로 전달 해주기.
        Page<FoodListReplyCountDTO> result = new PageImpl<FoodListReplyCountDTO>(list, pageable, total);

        return result;
        // 페이징 조건, 재사용
    }
}

