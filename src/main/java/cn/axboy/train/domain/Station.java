package cn.axboy.train.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author zcw
 * @version 1.0.0
 * @date 2018/1/22 14:53
 * 站台信息，@bjb|北京北|VAP|beijingbei|bjb|0
 */
@Data
@Document
public class Station {

    /**
     * id，第3个字段
     */
    @Indexed
    private String id;

    /**
     * 编号，第6个字段
     */
    private Integer no;

    /**
     * 标记，第1个字段，不加@
     */
    private String mark;

    /**
     * 中文名，第2个字段
     */
    private String title_cn;

    /**
     * 全拼，第4个字段
     */
    private String title_pinyin;

    /**
     * 简拼，第5个字段
     */
    private String title_pinyin_short;
}