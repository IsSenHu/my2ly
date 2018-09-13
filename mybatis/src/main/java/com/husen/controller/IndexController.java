package com.husen.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by HuSen on 2018/8/10 16:43.
 */
@RestController
public class IndexController {

    @GetMapping("/index")
    private ModelAndView index() {
        return new ModelAndView("/index");
    }
}
