package com.busanit501.helloworld.food.dao;

import com.busanit501.helloworld.food.vo.FoodVO;
import lombok.Cleanup;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FoodDAO<dueDate, finished> {
    // 화면으로부터 전달받은 ,
    // String mid, String mpw
    // 예시 ) mid: lsy, mpw: 1234
    public FoodVO getFoodWithFno(Long fno, String title, LocalDate dueDate, String uuid, Boolean finished) throws SQLException {
        String query = "SELECT * FROM tbl_food WHERE fno=? AND title=? AND dueDate=? AND uuid=? AND finished=?";

        FoodVO foodVO = null;

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, fno);
        preparedStatement.setString(2, title);
        preparedStatement.setDate(3, Date.valueOf(dueDate));
        preparedStatement.setString(4, uuid);
        preparedStatement.setBoolean(5, finished);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            foodVO = FoodVO.builder()
                    .fno(resultSet.getLong("fno"))
                    .title(resultSet.getString("title"))
                    .dueDate(resultSet.getDate("dueDate").toLocalDate())
                    .uuid(resultSet.getString("uuid"))
                    .finished(resultSet.getBoolean("finished"))
                    .build();
        }

        return foodVO;
    }

    public void updateUuid(Long fno, String title, LocalDate dueDate, String uuid, Boolean finished) throws SQLException {
        // UPDATE 쿼리에서 조건을 잘못 작성했기 때문에 SET으로 수정
        String query = "UPDATE tbl_food SET title=?, dueDate=?, uuid=?, finished=? WHERE fno=?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);

        // PreparedStatement에 올바른 매개변수 설정
        preparedStatement.setString(1, title);
        preparedStatement.setDate(2, Date.valueOf(dueDate));
        preparedStatement.setString(3, uuid);
        preparedStatement.setBoolean(4, finished);
        preparedStatement.setLong(5, fno);

        // 쿼리 실행
        preparedStatement.executeUpdate();
    }

    public FoodVO getFoodWithUuid(String uuid) throws SQLException {
        String query = "SELECT * FROM tbl_food WHERE uuid=?";

        // 결과 데이터를 담을 FoodVO 객체 생성
        FoodVO foodVO = null;

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, uuid);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        // 결과가 있는지 확인
        if (resultSet.next()) {
            foodVO = FoodVO.builder()
                    .fno(resultSet.getLong("fno"))
                    .title(resultSet.getString("title"))
                    .dueDate(resultSet.getDate("dueDate").toLocalDate())
                    .uuid(resultSet.getString("uuid"))
                    .finished(resultSet.getBoolean("finished"))
                    .build();
        }

        return foodVO;
    }

    public void insert(FoodVO foodVO) throws SQLException {

        String sql = "insert into tbl_food (title, dueDate, finished) " +
                "values (?, ?, ?)";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, foodVO.getTitle());
        preparedStatement.setDate(2, Date.valueOf(foodVO.getDueDate()));
        preparedStatement.setBoolean(3, foodVO.isFinished());
        preparedStatement.executeUpdate();
    } //insert

    //2
    // select , DB에서 전체 조회.
    public List<FoodVO> selectAll() throws SQLException {
        String sql = "select * from tbl_food";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
        // 넘어온 데이터를 임시로 보관할 리스트 인스턴스 만들고,
        // 반복문 통해서, 넘어온 각행을 리스트에 요소로 하나씩 담기.
        List<FoodVO> list = new ArrayList<>();
        while (resultSet.next()) {
            FoodVO foodVO = FoodVO.builder()
                    .fno(resultSet.getLong("fno"))
                    .title(resultSet.getString("title"))
                    .dueDate(resultSet.getDate("dueDate").toLocalDate())
                    .finished(resultSet.getBoolean("finished"))
                    .build();
            list.add(foodVO);
        }
        return list;
    }

    //3, 하나 조회. 상세보기.
    public FoodVO selectOne(Long fno) throws SQLException {
        String sql = "select * from tbl_food where fno = ?";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, fno);
        // 하나만 받아온 상태,
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
        // 임시 TotoVO , 인스턴스 만들어서, 한행의 각 컬럼 4개를 담기.
        // 0행에서 -> 1행으로 조회를 해야하는데, 요게 누락됨.
        resultSet.next();
        FoodVO foodVO = FoodVO.builder()
                .fno(resultSet.getLong("fno"))
                .title(resultSet.getString("title"))
                .dueDate(resultSet.getDate("dueDate").toLocalDate())
                .finished(resultSet.getBoolean("finished"))
                .build();
        return foodVO;
    }

    public void updateOne(FoodVO foodVO) throws SQLException {
        String sql = " update tbl_food set title=?, dueDate=?, finished=?" +
                " where fno=?";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, foodVO.getTitle());
        preparedStatement.setDate(2, Date.valueOf(foodVO.getDueDate()));
        preparedStatement.setBoolean(3, foodVO.isFinished());
        preparedStatement.setLong(4, foodVO.getFno());
        preparedStatement.executeUpdate();

    }

    //삭제,
    // delete,
    public void deleteFood(Long fno) throws SQLException {
        String sql = "delete from tbl_food where fno =?";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, fno);
        preparedStatement.executeUpdate();
    }
}

