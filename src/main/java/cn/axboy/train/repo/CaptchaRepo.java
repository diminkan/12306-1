package cn.axboy.train.repo;

import cn.axboy.train.domain.Captcha;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zcw
 * @version 1.0.0
 * @date 2018/1/22 15:27
 */
@Repository
public interface CaptchaRepo extends MongoRepository<Captcha, String> {

}
