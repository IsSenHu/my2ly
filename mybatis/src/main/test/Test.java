import com.husen.dao.mapper.UserMapper;
import com.husen.dao.po.UserPo;
import com.husen.service.UserService;
import com.husen.service.id.IdService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

/**
 * Created by HuSen on 2018/6/28 13:08.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"classpath:/dubbo.xml"})
public class Test {

    @org.junit.Test
    public void test() {
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo/dubbo.xml");
        context.start();
    }
}
