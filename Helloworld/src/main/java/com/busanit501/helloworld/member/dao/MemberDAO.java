package com.busanit501.helloworld.member.dao;

import com.busanit501.helloworld.member.vo.MemberVO;
import lombok.Cleanup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    // 화면으로부터 전달받은 ,
    // String mid, String mpw
    // 예시 ) mid: lsy, mpw: 1234
    public MemberVO getMemberWithMpw(String mid, String mpw) throws SQLException {
        String query = "select * from tbl_member where mid=? and mpw=?";
        // 결과 데이터를 담아둘 임시 박스 MemberVO
        MemberVO memberVO = null;

        @Cleanup Connection connection = com.busanit501.helloworld.member.dao.ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, mid);
        preparedStatement.setString(2, mpw);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        memberVO = MemberVO.builder()
                .mid(resultSet.getString("mid"))
                .mpw(resultSet.getString("mpw"))
                .mname(resultSet.getString("mname"))
                .build();

        return memberVO;
    } //

    public void updateUuid(String mid, String uuid) throws SQLException {
        String query = "update tbl_member set uuid=? where mid=?";

        @Cleanup Connection connection = com.busanit501.helloworld.member.dao.ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, uuid);
        preparedStatement.setString(2, mid);
        preparedStatement.executeUpdate();
    } //

    //uuid로 유저를 검색하는 기능.
    public MemberVO getMemberWithUuid(String uuid) throws SQLException {
        String query = "select * from tbl_member where uuid=?";
        // 결과 데이터를 담아둘 임시 박스 MemberVO
        MemberVO memberVO = null;

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, uuid);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        memberVO = MemberVO.builder()
                .mid(resultSet.getString("mid"))
                .mpw(resultSet.getString("mpw"))
                .mname(resultSet.getString("mname"))
                .uuid(resultSet.getString("uuid"))
                .build();

        return memberVO;
    } //

    public void insert(MemberVO memberVO) throws SQLException {

        String sql = "insert into tbl_member (mid, mpw, mname, uuid) " +
                "values (?, ?, ?)";
        @Cleanup Connection connection = com.busanit501.helloworld.member.dao.ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, memberVO.getMid());
        preparedStatement.setString(1, memberVO.getMpw());
        preparedStatement.setString(1, memberVO.getMname());
        preparedStatement.setString(1, memberVO.getUuid());
        preparedStatement.executeUpdate();
    } //insert

    //2
    // select , DB에서 전체 조회.
    public List<MemberVO> selectAll() throws SQLException {
        String sql = "select * from tbl_member";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
        // 넘어온 데이터를 임시로 보관할 리스트 인스턴스 만들고,
        // 반복문 통해서, 넘어온 각행을 리스트에 요소로 하나씩 담기.
        List<MemberVO> list = new ArrayList<>();
        while (resultSet.next()) {
            MemberVO memberVO = MemberVO.builder()
                    .mid(String.valueOf(resultSet.getLong("mid")))
                    .mpw(resultSet.getString("mpw"))
                    .mname(String.valueOf(resultSet.getBoolean("mname")))
                    .uuid(resultSet.getString("uuid"))
                    .build();
            list.add(memberVO);
        }
        return list;
    }

    //3, 하나 조회. 상세보기.
    public MemberVO selectOne(Long mno) throws SQLException {
        String sql = "select * from tbl_member where mno = ?";
        @Cleanup Connection connection = com.busanit501.helloworld.member.dao.ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, mno);
        // 하나만 받아온 상태,
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
        // 임시 TotoVO , 인스턴스 만들어서, 한행의 각 컬럼 4개를 담기.
        // 0행에서 -> 1행으로 조회를 해야하는데, 요게 누락됨.
        resultSet.next();
        MemberVO memberVO = MemberVO.builder()
                .mid(String.valueOf(resultSet.getLong("mid")))
                .mpw(resultSet.getString("mpw"))
                .mname(String.valueOf(resultSet.getBoolean("mname")))
                .uuid(resultSet.getString("uuid"))
                .build();
        return memberVO;
    }

    // 수정.
    // update,
    //  화면에서 낱개로 넘어온 데이터는 DTO 담아서 전달,
    // 서비스 계층에서, DTO -> VO(Value Object) 데이터베이스 직접적인 연관있음.
    // 예시, VO 클래스는 , 테이블과 컬럼과 동일.
    // 예시2) ,DTO 화면( 출력에서 전달하고 싶은 데이터만 골라서 사용할수 있음. )
    // 화면에서 받아옴, 테스트 , 더미 데이터 확인.
    public void updateOne(MemberVO memberVO) throws SQLException {
        String sql = " update tbl_member set mid=?, mpw=?, mname=?, uuid=?" +
                " where mno=?";
        @Cleanup Connection connection = com.busanit501.helloworld.member.dao.ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // 화면에서 넘겨받은 변경할 데이터를 DTO -> VO 변환 후에(서비스에서 할 예정.)
        // VO 에서 꺼내서, 디비로 데이터 전달하는 과정.
        preparedStatement.setString(1, memberVO.getMid());
        preparedStatement.setString(1, memberVO.getMpw());
        preparedStatement.setString(1, memberVO.getMname());
        preparedStatement.setString(1, memberVO.getUuid());
        preparedStatement.executeUpdate();

    }

    //삭제,
    // delete,
    public void deleteMember(Long mno) throws SQLException {
        String sql = "delete from tbl_member where mno =?";
        @Cleanup Connection connection = com.busanit501.helloworld.member.dao.ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, mno);
        preparedStatement.executeUpdate();

    }
}



