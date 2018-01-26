package cn.axboy.train

import cn.axboy.train.service.DingTalkService
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
class DingTalkTests {

    @Autowired
    private DingTalkService dingTalkService

    //@Test
    void test() {
        def param = [
                title: "车票提醒",
                from : "深圳",
                to   : "广州",
                type : "二等座",
                date : "2018/01/26",
                no   : "G6030",
                num  : "22"
        ]
        println dingTalkService.sendDingGroupMsg(param, "TicketReminder.md")
    }
}
