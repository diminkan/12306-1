package cn.axboy.train

import cn.axboy.train.conf.TrainProperties
import cn.axboy.train.domain.Station
import cn.axboy.train.repo.StationRepo
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.client.RestTemplate

/**
 * @author zcw
 * @version 1.0.0
 * @date 2018/1/22 14:56
 */
@RunWith(SpringRunner)
@SpringBootTest
class InitDatabaseTests {

    @Autowired
    private RestTemplate restTemplate

    @Autowired
    private RestTemplate trainRestTemplate

    @Autowired
    private StationRepo stationRepo

    @Autowired
    private TrainProperties properties

    //@Before
    void before() {
        stationRepo.deleteAll()
    }

    @Test
    void start() {
        //截取等号后面数据，并去除单引号
        getStationStr()
                .split("=")[1]
                .replaceAll("'", "")
                .replaceAll(";", "")
                .replaceFirst("@", "")
                .split("@")
                .each { data ->
            println data
            def arr = data.split("\\|")
            def station = new Station()
            station.id = arr[2]
            station.no = Integer.valueOf(arr[5])
            station.mark = arr[0]
            station.title_cn = arr[1]
            station.title_pinyin = arr[3]
            station.title_pinyin_short = arr[4]
            stationRepo.save(station)
        }
    }

    private String getStationStr() {
        String url = properties.train.getStationUrl
        return trainRestTemplate.getForEntity(url, String).getBody()
    }
}
