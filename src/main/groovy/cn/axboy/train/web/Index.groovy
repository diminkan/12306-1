package cn.axboy.train.web

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author zcw
 * @date 2018/1/22 17:07
 * @version 1.0.0
 */
@RestController
class Index {

    @RequestMapping("/")
    def index() {
        return [
                code: 200,
                msg : 'success'
        ]
    }
}
