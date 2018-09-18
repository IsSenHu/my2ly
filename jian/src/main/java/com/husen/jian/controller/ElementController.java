package com.husen.jian.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by HuSen on 2018/9/18 13:41.
 */
@RestController
public class ElementController {

    @GetMapping("/element")
    private ModelAndView element() {
        return new ModelAndView("element/index");
    }
}
