package com.busanit501.boot501.service;

import com.busanit501.boot501.domain.Food;
import com.busanit501.boot501.domain.Reply;
import com.busanit501.boot501.dto.PageRequestDTO;
import com.busanit501.boot501.dto.PageResponseDTO;
import com.busanit501.boot501.dto.ReplyDTO;
import com.busanit501.boot501.repository.FoodRepository;
import com.busanit501.boot501.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    private final FoodRepository foodRepository;

    private final ModelMapper modelMapper;

    @Override
    public Long register(ReplyDTO replyDTO) {
        // 화면에서 받은 데이터 DTO 타입 -> 엔티티 타입으로 변경,
        // replyDTO, fno 값이 존재.
        log.info("Registering new replyDTO: " + replyDTO);
        Reply reply = modelMapper.map(replyDTO, Reply.class);
        Optional<Food> result = foodRepository.findById(replyDTO.getFno());
        Food food = result.orElseThrow();
        reply.changeFood(food);
        log.info("Registering new reply: " + reply);
        Long rno = replyRepository.save(reply).getRno();
        return rno;
    }

    @Override
    public ReplyDTO readOne(Long rno) {
        Optional<Reply> result = replyRepository.findById(rno);
        Reply reply = result.orElseThrow();
        ReplyDTO replyDTO = modelMapper.map(reply, ReplyDTO.class);
        return replyDTO;
    }

    @Override
    public void update(ReplyDTO replyDTO) {
        Optional<Reply> result = replyRepository.findById(replyDTO.getRno());
        Reply reply = result.orElseThrow();
        reply.changeReplyText(replyDTO.getReplyText(), replyDTO.getReplyer());
        replyRepository.save(reply);
    }

    @Override
    public void delete(Long rno) {
        replyRepository.deleteById(rno);
    }

    @Override
    public PageResponseDTO<ReplyDTO> listWithReply(Long fno, PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage()-1 <= 0 ? 0:pageRequestDTO.getPage()-1,
                pageRequestDTO.getSize(), Sort.by("rno").ascending());

        Page<Reply> result = replyRepository.listOfFood(fno, pageable);

        List<ReplyDTO> dtoList = result.getContent().stream()
                .map(reply -> {
                    ReplyDTO replyDTO = modelMapper.map(reply, ReplyDTO.class);
                    log.info("ReplyDTO: setRno 전" + replyDTO);
                    replyDTO.setFno(reply.getFood().getFno());
                    log.info("ReplyDTO: setRno 후" + replyDTO);
                    return replyDTO;
                })
                .collect(Collectors.toList());

        PageResponseDTO<ReplyDTO> pageResponseDTO = PageResponseDTO.<ReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();

        return pageResponseDTO;
    }
}
