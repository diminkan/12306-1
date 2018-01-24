package cn.axboy.train

import cn.axboy.train.service.KyfwService
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
class KyfwTests {

    @Autowired
    private KyfwService kyfwService

    //@Test
    void queryTicket() {
        def resp = kyfwService.query("2018-01-25", "GZG", "SZQ")
        println resp.map
    }
}
