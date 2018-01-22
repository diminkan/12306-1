package cn.axboy.train.conf

import org.quartz.Scheduler
import org.quartz.SchedulerFactory
import org.quartz.impl.StdSchedulerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

/**
 * @author zcw
 * @version 1.0.0
 * @date 2018/1/22 15:19
 */
@Configuration
class QuartzConf {

    @Bean
    @Scope("singleton")
    SchedulerFactory schedulerFactory() {
        return new StdSchedulerFactory()
    }

    @Bean
    @Scope("singleton")
    Scheduler scheduler(SchedulerFactory schedulerFactory) {
        return schedulerFactory.getScheduler()
    }
}
