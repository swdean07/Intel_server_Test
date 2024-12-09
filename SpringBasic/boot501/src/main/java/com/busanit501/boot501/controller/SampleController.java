package com.busanit501.boot501.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller // 1)화면 제공 2) 데이터 제공
@Log4j2
public class SampleController {

    @GetMapping("/hello")
    // 레거시에서 앞단 화면을 jsp 사용했고,
    // 부트에서 앞단 화면을 타임리프 사용. 확장자, .html 동일함.
    public void hello(Model model) {
        // 레거시, 뷰 설정, xml 등록,
        // WEB-INF/Views/todo prefix
        // .jsp , suffix
        // 기본 : templates/hello.html

        model.addAttribute("msg", "hello world");
    }

    @GetMapping("/ex/ex1")
    public void ex1(Model model) {
        List<String> list = Arrays.asList("a", "b", "c");
        model.addAttribute("list", list);
    }

}
