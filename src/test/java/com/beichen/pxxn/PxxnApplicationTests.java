package com.beichen.pxxn;

import com.beichen.pxxn.request.RequestVideoInfo;
import com.beichen.pxxn.utils.SpringContextUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PxxnApplicationTests {

    @Test
    public void contextLoads() {
        RequestVideoInfo requestVideoInfo = SpringContextUtils.getBean("requestVideoInfo", RequestVideoInfo.class);
        requestVideoInfo.test(163);
    }

}
