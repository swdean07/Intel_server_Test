package com.busanit501.boot501.controller;

import com.busanit501.boot501.dto.FoodDTO;
import com.busanit501.boot501.dto.FoodListReplyCountDTO;
import com.busanit501.boot501.dto.PageRequestDTO;
import com.busanit501.boot501.dto.PageResponseDTO;
import com.busanit501.boot501.service.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequestMapping("/food")
@RequiredArgsConstructor

public class FoodController {
    private final FoodService foodService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model ) {
//        PageResponseDTO<FoodDTO> responseDTO = foodService.list(pageRequestDTO);
        PageResponseDTO<FoodListReplyCountDTO> responseDTO = foodService.listWithReplyCount(pageRequestDTO);
        log.info("pageRequestDTO 의 getLink 조사 : " + pageRequestDTO.getLink());
        model.addAttribute("responseDTO", responseDTO);
    }

    @GetMapping("/register")
    public void register() {

    }
    @PostMapping("/register")
    public String registerPost(@Valid FoodDTO foodDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        log.info("FoodController register post 로직처리: ");
        log.info("FoodController register post  foodDTO : " + foodDTO);

        if (bindingResult.hasErrors()) {
            log.info("has errors : 유효성 에러가 발생함.");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/food/register";
        }

        Long fno = foodService.register(foodDTO);

        redirectAttributes.addFlashAttribute("result", fno);
        redirectAttributes.addFlashAttribute("resultType", "register");

        return "redirect:/food/list";

    }

    @GetMapping("/read")
    public void read(Long fno, PageRequestDTO pageRequestDTO,
                     Model model) {
        FoodDTO foodDTO = foodService.readOne(fno);
        model.addAttribute("dto", foodDTO);
    }

    @GetMapping("/update")
    public void update(Long fno, PageRequestDTO pageRequestDTO,
                       Model model) {
        FoodDTO foodDTO = foodService.readOne(fno);
        model.addAttribute("dto", foodDTO);
    }

    @PostMapping("/update")
    public String updatePost(@Valid FoodDTO foodDTO,
                             BindingResult bindingResult,
                             PageRequestDTO pageRequestDTO,
                             String keyword2,String page2, String type2,
                             RedirectAttributes redirectAttributes) {
        log.info("FoodController updatePost post 로직처리: ");
        log.info("FoodController updatePost post  foodDTO : " + foodDTO);

        log.info("FoodController updatePost post  pageRequestDTO : " + pageRequestDTO);

        // 유효성 체크 -> 유효성 검증시, 통과 안된 원인이 있다면,
        if (bindingResult.hasErrors()) {
            log.info("has errors : 유효성 에러가 발생함.");
            // 1회용으로, 웹 브라우저에서, errors , 키로 조회 가능함. -> 뷰 ${errors}
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/food/update?fno="+foodDTO.getFno()+"&keyword="+keyword2+"&page="+page2+"&type="+type2;
        }
        //검사가 통과가 되고, 정상 입력
        foodService.update(foodDTO);

        // 글 정상 등록후, 화면에 result 값을 전달하기.
        // 1회용 사용하기.
        redirectAttributes.addFlashAttribute("result", foodDTO.getFno());
        redirectAttributes.addFlashAttribute("resultType", "update");

        return "redirect:/food/read?fno="+foodDTO.getFno()+"&keyword="+keyword2+"&page="+page2+"&type="+type2;

    }

    @PostMapping("/delete")
    public String delete(Long fno,
                         String keyword2,String page2, String type2,
                         RedirectAttributes redirectAttributes) {
        foodService.delete(fno);
        redirectAttributes.addFlashAttribute("result", fno);
        redirectAttributes.addFlashAttribute("resultType", "delete");
        return "redirect:/food/list?"+"&keyword="+keyword2+"&page="+page2+"&type="+type2;
    }

}

