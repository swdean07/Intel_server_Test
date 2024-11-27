package com.busanit501.helloworld.member.service;

import com.busanit501.helloworld.food.vo.FoodVO;
import com.busanit501.helloworld.jdbcex.util.MapperUtil;
import com.busanit501.helloworld.jdbcex.vo.TodoVO;
import com.busanit501.helloworld.member.dao.MemberDAO;
import com.busanit501.helloworld.member.dto.MemberDTO;
import com.busanit501.helloworld.member.vo.MemberVO;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


//열거형 상수들,
//상수들의 집합, 모음집
@Log4j2
public enum MemberService {
    INSTANCE;

    private MemberDAO memberDAO;
    private ModelMapper modelMapper;

    MemberService() {
        memberDAO = new MemberDAO();
        modelMapper = MapperUtil.INSTANCE.get();
    }

    //1
    // register
    public void register(MemberDTO memberDTO) throws SQLException {
        MemberVO memberVO = modelMapper.map(memberDTO, MemberVO.class);
        log.info("foodVO : " + memberVO);
        memberDAO.insert(memberVO);
    } // register

    //2
    // 전체 조회
    public List<MemberDTO> listAll() throws SQLException {
        List<MemberVO> voList = memberDAO.selectAll();
        log.info("voList : " + voList);
        List<MemberDTO> dtoList = voList.stream().map(vo -> modelMapper.map(vo, MemberDTO.class))
                .collect(Collectors.toList());
        return dtoList;
    }

    //3
    // 하나 조회, 상세보기.
    public MemberDTO get(Long mno) throws SQLException {
        log.info("fno : " + mno);
        ///  디비에서 하나 조회 결과 받았음.
        MemberVO memberVO = memberDAO.selectOne(mno);
        // VO -> DTO 변환 작업.
        MemberDTO memberDTO = modelMapper.map(memberVO,MemberDTO.class);
        return memberDTO;

    }
    //4 수정 기능
    public void update(MemberDTO memberDTO) throws SQLException {

        log.info("memberDTO : " + memberDTO);
        FoodVO foodVO = modelMapper.map(memberDTO, FoodVO.class);
        TodoVO memberVO;
        memberDAO.updateOne(memberVO);

    }

    //5 삭제 기능.
    public void delete(Long mno) throws SQLException {
        memberDAO.deleteMember(mno);
    }


}

