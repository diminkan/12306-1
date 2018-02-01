package cn.axboy.train.service

import cn.axboy.train.conf.TrainProperties
import cn.axboy.train.domain.Captcha
import cn.axboy.train.repo.CaptchaRepo
import cn.axboy.train.service.model.QueryResp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

/**
 * @author zcw
 * @date 2018/1/24 17:14
 * @version 1.0.0
 */
@Service
class KyfwService {

    @Autowired
    private RestTemplate restTemplate

    @Autowired
    private TrainProperties properties

    @Autowired
    private QiNiuService qiNiuService

    @Autowired
    private CaptchaRepo captchaRepo

    private static String JSESSIONID = null
    private static String ROUTE = null
    private static String BIGIPSERVEROTN = null

    /**
     * 查询余票
     * @param trainDate ;发车时间
     * @param fromStation ;出发站
     * @param toStation ;到达站
     */
    QueryResp query(String trainDate, String fromStation, String toStation) {
        String queryTicketUrl = properties.train.queryTicketUrl
        final url = queryTicketUrl + "?" +
                "leftTicketDTO.train_date=${trainDate}" +
                "&leftTicketDTO.from_station=${fromStation}" +
                "&leftTicketDTO.to_station=${toStation}" +
                "&purpose_codes=ADULT"  //ADULT成人票，0x00学生票

        HttpHeaders headers = new HttpHeaders()
        def resp
        try {
            headers.add("Cookie", getCookie(false))
            HttpEntity<String> httpEntity = new HttpEntity(headers)
            resp = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Map)
        } catch (RestClientException e) {
            e.printStackTrace()
            println ">>>>cookie失效，重新获取"
            headers.remove("Cookie")
            headers.add("Cookie", getCookie(true))
            HttpEntity<String> httpEntity = new HttpEntity(headers)
            resp = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Map)
        }
        return QueryResp.queryMapper(resp.getBody())
    }

    /**
     * 获取cookie
     * @param boo ;true，重新获取；false，获取内存中的
     */
    String getCookie(boolean boo) {
        if (boo || !JSESSIONID) {
            String url = properties.train.queryInitUrl
            def resp = restTemplate.getForEntity(url, String)
            resp.getHeaders().get("Set-Cookie").each {
                if (it.contains("route")) {
                    ROUTE = it.split(";")[0].split("=")[1]
                    return
                }
                if (it.contains("JSESSIONID")) {
                    JSESSIONID = it.split(";")[0].split("=")[1]
                    return
                }
                if (it.contains("BIGipServerotn")) {
                    BIGIPSERVEROTN = it.split(";")[0].split("=")[1]
                    return
                }
            }
        }
        return "JSESSIONID=${JSESSIONID}; " +
                "RAIL_OkLJUJ=FFBY4klyiGW7EUUxc05YFMzGb8AnhFqJ; " +
                "route=9036359bb8a8a461c164a04f8f50b252; " +
                "BIGipServerotn=1574502666.24610.0000; fp_ver=4.5.1; " +
                "BIGipServerpassport=820510986.50215.0000; " +
                "current_captcha_type=Z; " +
                "BIGipServerportal=2949906698.16671.0000; " +
                "RAIL_EXPIRATION=1505472292897; " +
                "RAIL_DEVICEID=Ig8eIzbIeSgK9rjqXCAQtS2fDVViHFRg7e4NN3Uh5u8GIBKjPQjwUuZkCTyH2mJJIzM4OFJrgbsD2HvFN9CpBclpuVIZ9WgtUgltnTylxKLKtxYAuidaYBIhJIRMyJXtQBnXp6KJQBHlqIP0unQklZFuqvSDtF2Z; "
//                "_jc_save_fromStation=%u8D63%u5DDE%2CGZG; " +
//                "_jc_save_toStation=%u6DF1%u5733%2CSZQ; " +
//                "_jc_save_fromDate=2017-10-06; " +
//                "_jc_save_toDate=2017-09-12; " +
//                "_jc_save_wfdc_flag=dc"
    }

    /**
     * 获取验证码，并保存到七牛云
     * @return
     */
    String getCaptcha() {
        def url = properties.train.getCaptchaUrl
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.IMAGE_JPEG)
        headers.add("Cookie", getCookie(false))
        def resp = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<byte[]>(headers), byte[])
        byte[] result = resp.getBody()
        String key = qiNiuService.uploadByByteArray(result)

        Captcha captcha = new Captcha()
        captcha.id = key
        captcha.md5 = key
        captcha.imgUrl = properties.qiNiu.prefix + key
        captchaRepo.save(captcha)
        return captcha.imgUrl
    }

}
