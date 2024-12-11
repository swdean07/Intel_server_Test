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

    @GetMapping("/todo/list")
    public void list(Model model) {
        List<String> list = Arrays.asList("리스트");
        model.addAttribute("list", list);
    }

    @GetMapping("/todo/register")
    public void register(Model model) {
        List<String> register = Arrays.asList("레지스터");
        model.addAttribute("register", register);
    }
}
