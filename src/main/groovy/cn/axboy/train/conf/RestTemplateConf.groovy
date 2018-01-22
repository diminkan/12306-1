package cn.axboy.train.conf

import org.apache.http.client.HttpClient
import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.conn.ssl.TrustSelfSignedStrategy
import org.apache.http.impl.client.HttpClients
import org.apache.http.ssl.SSLContexts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.web.HttpMessageConverters
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.core.io.Resource
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.web.client.RestTemplate

import javax.net.ssl.SSLContext
import java.nio.charset.Charset
import java.security.KeyStore

/**
 * @author zcw
 * @date 2018/1/22 15:14
 * @version 1.0.0
 * restTemplate配置
 */
@Configuration
class RestTemplateConf {

    @Autowired
    private TrainProperties properties

    @Bean
    HttpMessageConverters httpMessageConverters() {
        return new HttpMessageConverters(true, [
                new StringHttpMessageConverter(Charset.forName("UTF-8"))
        ])
    }

    @Bean
    HttpClient trainHttpClient() {
        //String file = "D:\\work\\ali-repo\\app-train\\src\\main\\resources\\train\\srca.keystore"
        String password = properties.cert.srcaPassword
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType())
        InputStream is = properties.cert.srcaKeyStore.getInputStream()     //new FileInputStream(new File(file))
        trustStore.load(is, password.toCharArray())
        is.close()
        SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                .build()
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext)
        return HttpClients.custom().setSSLSocketFactory(sslsf).build()
    }

    @Bean
    ClientHttpRequestFactory trainClientHttpRequestFactory(HttpClient trainHttpClient) {
        return new HttpComponentsClientHttpRequestFactory(trainHttpClient)
    }

    /**
     * 配置了12306证书的restTemplate，用于向12306请求数据
     */
    @Bean
    @Scope("prototype")
    RestTemplate trainRestTemplate(
            RestTemplateBuilder restTemplateBuilder,
            ClientHttpRequestFactory trainClientHttpRequestFactory) {
        return restTemplateBuilder.requestFactory(trainClientHttpRequestFactory).build()
    }

    /**
     * 普通的restTemplate
     */
    @Bean
    @Scope("prototype")
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build()
    }
}
