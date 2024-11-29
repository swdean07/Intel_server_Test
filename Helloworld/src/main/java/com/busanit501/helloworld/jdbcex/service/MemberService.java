package com.busanit501.helloworld.jdbcex.service;

import com.busanit501.helloworld.jdbcex.dao.MemberDAO;
import com.busanit501.helloworld.jdbcex.dto.MemberDTO;
import com.busanit501.helloworld.jdbcex.util.MapperUtil;
import com.busanit501.helloworld.jdbcex.vo.MemberVO;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;

// 설정1
@Log4j2
public enum MemberService {
    INSTANCE;

    private MemberDAO memberDAO;
    private ModelMapper modelMapper;

    // 생성자 이용해서, 초기화하기.
    MemberService() {
        memberDAO = new MemberDAO();
        modelMapper = MapperUtil.INSTANCE.get();
    }

    // 로그인 확인용.
    public MemberDTO login(String mid, String mpw) throws SQLException {
      MemberVO memberVO = memberDAO.getMemberWithMpw(mid,mpw);
      MemberDTO memberDTO = modelMapper.map(memberVO, MemberDTO.class);
      return memberDTO;
    }

    public void updateUuid(String mid, String uuid) throws SQLException {
        memberDAO.updateUuid(mid,uuid);
    }

    public MemberDTO getMemberWithUuidService(String uuid) throws SQLException {
        MemberVO memberVO= memberDAO.getMemberWithUuid(uuid);
        MemberDTO memberDTO = modelMapper.map(memberVO, MemberDTO.class);
        return memberDTO;
    }


}






