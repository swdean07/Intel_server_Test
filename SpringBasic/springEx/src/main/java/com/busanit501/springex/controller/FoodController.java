package com.busanit501.springex.controller;

import com.busanit501.springex.dto.PageRequestDTO;
import com.busanit501.springex.dto.PageResponseDTO;
import com.busanit501.springex.dto.FoodDTO;
import com.busanit501.springex.service.FoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller// 1)화면 2)데이터 제공.
@RequestMapping("/food")
// 웹브라우저에서 아래의 경로로 오는 url 전부 여기 컨트롤러가 받아서 작업함.
// localhost:8080/food/
@Log4j2
@RequiredArgsConstructor
public class FoodController {

//    @Autowired
//    private TodoService todoService;

    private final FoodService foodService;



    // localhost:8080/food/list
    @RequestMapping("/list")
    // 기존 전체 페이지 출력 -> 페이징 처리된 목록 요소만 출력.
//    public void list(Model model) {
    public String list(@Valid PageRequestDTO pageRequestDTO,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       Model model) {
        log.info("FoodController list : 화면제공은 해당 메서드 명으로 제공함.");
        if (bindingResult.hasErrors()) {
            log.info("has errors : 유효성 에러가 발생함.");
            // 1회용으로, 웹 브라우저에서, errors , 키로 조회 가능함. -> 뷰 ${errors}
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/food/list";
        }
        PageResponseDTO<FoodDTO> pageResponseDTO = foodService.getListWithPage(pageRequestDTO);
        log.info("FoodController list 데이터 유무 확인 :" + pageResponseDTO);
        //데이터 탑재. 서버 -> 웹
        model.addAttribute("pageResponseDTO", pageResponseDTO);
        redirectAttributes.addAttribute("page",pageRequestDTO.getPage());
        redirectAttributes.addAttribute("size",pageRequestDTO.getSize());
        return "/food/list";
    }

    // localhost:8080/food/register
    // 1) 글작성 폼, 화면 -> get
    // 2) 글작성 로직 처리 -> post
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public void register() {
        log.info("FoodController register : 화면제공은 해당 메서드 명으로 제공함.");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    //@Valid TodoDTO todoDTO : 검사 대상 클래스,
    // BindingResult bindingResult : 검사 결과의 오류를 모아두는 임시 공간
    // RedirectAttributes redirectAttributes : 서버 -> 웹 , 데이터 전달하는 도구
    public String registerPost(@Valid FoodDTO foodDTO, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        log.info("FoodController register post 로직처리: ");
        log.info("FoodController register post  foodDTO : " + foodDTO);

        // 유효성 체크 -> 유효성 검증시, 통과 안된 원인이 있다면,
        if (bindingResult.hasErrors()) {
            log.info("has errors : 유효성 에러가 발생함.");
            // 1회용으로, 웹 브라우저에서, errors , 키로 조회 가능함. -> 뷰 ${errors}
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/food/register";
        }
        //검사가 통과가 되고, 정상 입력
        foodService.register(foodDTO);

        return "redirect:/food/list";
    }

    // 상세조회, 컨트롤러, 서비스 연결 부분,
    // http://localhost:8080/food/read?fno=9
    // 파라미터 자동 수집 부분 많이 활용.
    // tno, 서버에서 바로 이용가능.
    // 파리미터로 (TodoDTO todoDTO), 웹에서 넘어온 정보는
    //model.addAttribute("todoDTO", todoDTO) 없이도,
    // 뷰에서 -> EL 표기법으로 바로 사용가능 ${todoDTO}
    @RequestMapping("/read")
    //  목록 -> 상세보기 페이지 이동시, PageRequestDTO 의 getLink 이용해서,
    // page=7&size=10 정보를 전달 받았음.
    // 그러면, 이 데이터 서버에서 이용할려면, 컨트롤러에서, 받는 매개변수가 필요해요.
    // 자동으로 쿼리스트링으로 넘어온 데이터 자동으로 받기.
    //
    // 자동으로 받은 데이터를 다시, 자동으로 모델이 알아서, 화면에 전달함.
    // read.jsp 화면에서, pageRequestDTO 이용가능.
    public String read(Long fno, @Valid PageRequestDTO pageRequestDTO,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       Model model) {
        log.info("FoodController read :");
        if (bindingResult.hasErrors()) {
            log.info("has errors : 유효성 에러가 발생함.");
            // 1회용으로, 웹 브라우저에서, errors , 키로 조회 가능함. -> 뷰 ${errors}
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("fno", fno);
            return "redirect:/food/read";
        }
        FoodDTO foodDTO = foodService.getOne(fno);
        log.info("FoodController read 데이터 유무 확인 :" + foodDTO);
        //데이터 탑재. 서버 -> 웹
        model.addAttribute("foodDTO", foodDTO);
        redirectAttributes.addAttribute("page",pageRequestDTO.getPage());
        redirectAttributes.addAttribute("size",pageRequestDTO.getSize());
        return "/food/read";

    }


    // 수정 1) 폼 2) 로직 처리
    @RequestMapping("/update")
    public void update(Long fno,@Valid PageRequestDTO pageRequestDTO, Model model) {
        log.info("FoodController update :");
        FoodDTO foodDTO = foodService.getOne(fno);
        log.info("FoodController update 데이터 유무 확인 :" + foodDTO);
        //데이터 탑재. 서버 -> 웹
        model.addAttribute("foodDTO", foodDTO);

    }

    //수정 로직 처리
    @PostMapping("/update")
    // 수정할 항목을 모두 받아서, FoodDTO 담습니다. 여기에 fno 도 포함 시키기
    public String updateLogic(@Valid FoodDTO foodDTO, BindingResult bindingResult, PageRequestDTO pageRequestDTO,
                              RedirectAttributes redirectAttributes) {

        // 유효성 체크 -> 유효성 검증시, 통과 안된 원인이 있다면,
        if (bindingResult.hasErrors()) {
            log.info("has errors : 유효성 에러가 발생함.");
            // 1회용으로, 웹 브라우저에서, errors , 키로 조회 가능함. -> 뷰 ${errors}
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            //redirectAttributes 이용해서, 쿼리 스트링으로 전달.
            redirectAttributes.addAttribute("fno",foodDTO.getFno());
            redirectAttributes.addAttribute("page",pageRequestDTO.getPage());
            redirectAttributes.addAttribute("size",pageRequestDTO.getSize());
            return "redirect:/food/update";
        }

        // 수정하는 로직 필요함.
        // 주의사항, 체크박스의 값의 문자열 on 전달 받습니다.
        log.info("foodDTO확인 finished의 변환 여부 확인. : " + foodDTO);

        foodService.update(foodDTO);
        // 쿼리 스트링으로 , 목록에 전달함.
        redirectAttributes.addAttribute("page",pageRequestDTO.getPage());
        redirectAttributes.addAttribute("size",pageRequestDTO.getSize());
        return "redirect:/food/list";
    }



    // 삭제
    @PostMapping("/delete")
    public String delete(Long fno, PageRequestDTO pageRequestDTO,
                         RedirectAttributes redirectAttributes
    ) {
        foodService.delete(fno);
        // 쿼리 스트링으로 , 목록에 전달함.
        redirectAttributes.addAttribute("page",pageRequestDTO.getPage());
        redirectAttributes.addAttribute("size",pageRequestDTO.getSize());
        return "redirect:/food/list";
    }

    // 페이징,

    // 검색,

    // 필터

}

















