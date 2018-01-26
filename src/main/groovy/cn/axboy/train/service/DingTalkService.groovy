package cn.axboy.train.service

import cn.axboy.train.conf.TrainProperties
import freemarker.template.Configuration
import freemarker.template.Template
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils
import org.springframework.web.client.RestTemplate

/**
 * @author zcw
 * @date 2018/1/26 11:03
 * @version 1.0.0
 * 钉钉群机器人
 */
@Service
class DingTalkService {

    @Autowired
    private TrainProperties properties

    @Autowired
    private Configuration configuration     //freemarker

    @Autowired
    private RestTemplate restTemplate

    /**
     * 发送钉钉群消息
     */
    def sendDingGroupMsg(Map param, String templatePath) {
        Template t = configuration.getTemplate(templatePath)
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(t, param)
        def map = [
                msgtype : "markdown",
                markdown: [
                        title: param.title,
                        text : content
                ]
        ]
        def dingUrl = properties.reminder.dingTalkRobot
        restTemplate.postForObject(dingUrl, map, String)
    }
}
