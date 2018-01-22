package cn.axboy.train.conf

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.PropertySource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

/**
 * @author zcw
 * @date 2018/1/22 15:50
 * @version 1.0.0
 */
@Component
@ConfigurationProperties(prefix = "cn.axboy.train", ignoreUnknownFields = false)
class TrainProperties {

    final Train train = new Train()
    final Cert Cert = new Cert()
    final QiNiu qiNiu = new QiNiu()

    static class Train {
        String getStationUrl
        String queryInitUrl
        String queryTicketUrl
    }

    static class Cert {
        Resource srcaKeyStore
        String srcaPassword
    }

    static class QiNiu {
        String accessKey
        String secretKey
        String bucket
        String prefix
    }
}
