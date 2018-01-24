package cn.axboy.train

import cn.axboy.train.service.Dama2Service
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author zcw
 * @version 1.0.0
 * @date 2018/1/22 14:56
 */
@RunWith(SpringRunner)
@SpringBootTest
class CaptcheTests {

    @Autowired
    private Dama2Service dama2Service

    //@Test
    void solve() {
        String url = "http://owvfacd8p.bkt.clouddn.com/102b83f8763efec595179b3f46fbd7ff"
        dama2Service.solveCaptcha(url)
    }
}
