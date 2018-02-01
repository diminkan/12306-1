package cn.axboy.train.service

import cn.axboy.train.conf.TrainProperties
import cn.axboy.train.utils.HashCryptUtil
import com.google.gson.Gson
import com.qiniu.common.QiniuException
import com.qiniu.http.Response
import com.qiniu.storage.UploadManager
import com.qiniu.storage.model.DefaultPutRet
import com.qiniu.util.Auth
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

/**
 * @author zcw
 * @date 2018/1/22 15:41
 * @version 1.0.0
 */
@Service
class QiNiuService {

    @Autowired
    private TrainProperties properties

    @Autowired
    private UploadManager uploadManager

    String uploadByByteArray(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null
        }
        def filename = '/12306/' + HashCryptUtil.getMD5(bytes)
        Auth auth = Auth.create(properties.qiNiu.accessKey, properties.qiNiu.secretKey)
        String upToken = auth.uploadToken(properties.qiNiu.bucket)
        try {
            uploadManager.put(bytes, filename, upToken)
        } catch (QiniuException ex) {
            Response r = ex.response
            println(r.bodyString())
            return null
        }
        return filename
    }
}
