package com.husen.controller.test;

import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by HuSen on 2018/9/10 14:12.
 */
@RestController
public class BrowerCacheController {

    private List<String> browerCache(HttpServletResponse response) {
        List<String> list = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            list.add(UUID.randomUUID().toString());
        }
        return list;
    }
}
