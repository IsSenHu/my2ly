package com.husen.service.id;

import org.springframework.stereotype.Service;

/**
 * Id生成服务
 * Created by HuSen on 2018/7/3 16:15.
 */
@Service
public class IdService {

    public long getId(){
        IdGenerator idGenerator = new IdGenerator(new Thread().getId());
        return idGenerator.nextId();
    }
}
