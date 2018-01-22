package cn.axboy.train.conf

import com.qiniu.common.Zone
import com.qiniu.storage.UploadManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope

/**
 * @author zcw
 * @version 1.0.0
 * @date 2018/1/22 15:11
 * 七牛云配置
 */
@Configuration
public class QiNiuConf {

    @Bean
    @Scope("singleton")
    UploadManager uploadManager() {
//        华东	Zone.zone0()
//        华北	Zone.zone1()
//        华南	Zone.zone2()
//        北美	Zone.zoneNa0()
        com.qiniu.storage.Configuration cfg = new com.qiniu.storage.Configuration(Zone.huanan())
        return new UploadManager(cfg)
    }
}
