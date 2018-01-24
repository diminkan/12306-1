package cn.axboy.train.service

import cn.axboy.train.conf.TrainProperties
import cn.axboy.train.utils.HashCryptUtil
import groovy.json.JsonParserType
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

/**
 * @author zcw
 * @date 2018/1/23 12:50
 * @version 1.0.0
 * 打码兔服务
 */
@Service
class Dama2Service {

    @Autowired
    private RestTemplate restTemplate

    @Autowired
    private TrainProperties properties

    /**
     * 识别验证码
     * @return
     */
    String solveCaptcha(String imgUrl) {
        String d2Url = properties.dama2.solveByImgUrl
        String pwd = getPwd(properties.dama2.username, properties.dama2.password, properties.dama2.appKey)
        String sign = getSign(properties.dama2.appKey + properties.dama2.username + imgUrl)
        def params = [
                appID  : properties.dama2.appId,
                user   : properties.dama2.username,
                pwd    : pwd,
                type   : "310",
                timeout: "30",
                url    : imgUrl,
                sign   : sign
        ]
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED)
        HttpEntity entity = new HttpEntity<String>(getUrlParamsByMap(params), headers)
        println "use dama2 solve captcha"
        println "params: ${params}"
        String resp = restTemplate.exchange(d2Url, HttpMethod.POST, entity, String).getBody()
        println "result: ${resp}"
        def parser = new JsonSlurper().setType(JsonParserType.LAX)
        def json = parser.parseText(resp)
        return json.result
    }

    /**
     * 获取加密后的密码
     * @param name
     * @param password
     * @param appKey
     * @return
     */
    private static String getPwd(String name, String password, String appKey) {
        String a = HashCryptUtil.getMD5(name)
        String b = HashCryptUtil.getMD5(password)
        String c = HashCryptUtil.getMD5(a + b)
        return HashCryptUtil.getMD5(appKey + c)
    }

    /**
     * 获取签名
     * @param text
     * @return
     */
    private static String getSign(String text) {
        return HashCryptUtil.getMD5(text).substring(0, 8)
    }

    private static String getUrlParamsByMap(Map params) {
        def sb = new StringBuilder()
        params.each { k, v ->
            sb = sb.append("${k}=${v}&")
        }
        return sb.toString()
    }
}
