package cn.axboy.train.repo;

import cn.axboy.train.domain.Station;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zcw
 * @version 1.0.0
 * @date 2018/1/22 15:27
 */
@Repository
public interface StationRepo extends MongoRepository<Station, String> {

    /**
     * 模糊匹配 拼音简拼、拼音、中文
     *
     * @param keyword; 关键词
     * @return
     */
    @Query("{ $or : [ {title_pinyin_short:{$regex:'?0'}}, {title_pinyin:{$regex:'?0'}}, {title_cn:{$regex:'?0'}} ] }")
    List<Station> findAllByKeyword(String keyword);
}
