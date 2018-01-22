package cn.axboy.train.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author zcw
 * @version 1.0.0
 * @date 2018/1/22 14:49
 * 12306验证码
 */
@Data
@Document
public class Captcha {

    @Indexed
    private String id;

    //@CreatedDate
    private Long createdDate = System.currentTimeMillis();

    /**
     * 文件md5值
     */
    private String md5;

    /**
     * 图片的url
     */
    private String imgUrl;

    /**
     * 验证码答案
     */
    private List<Integer> answer;
}
