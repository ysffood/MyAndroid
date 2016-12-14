package com.yk.demo.lib.mapper.model;

import com.yk.demo.lib.bean.HttpResponse;
import com.yk.demo.lib.bean.response.UploadFile;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * 文件上传上传相关Api接口
 *
 * @author yk
 * @version V1.0.0
 * created at 2016/12/12 16:58
 */
public interface FileUpApi {

    /**
     * 单文件上传
     * @param file RequestBody 的名称 filename 上传的文件名称
     * Path注解中的filename与上传文件的真实名称可以不匹配。
     * @return 服务器返回对象
     */
    @POST("WebProjectLC/lc/userInfo/singleFileUp")
    @Multipart
    Observable<HttpResponse<UploadFile>> uploadFile(@Part("file\"; filename=\"blank\"") RequestBody file);

    /**
     * 多文件上传
     * @param params 上传的文件集合
     * 使用@PartMap注释，传递多个Part，以实现多文件上传
     * @return 服务器返回对象
     */
    @POST("/WebProjectLC/lc/userInfo/multiFileUp")
    @Multipart
    Observable<HttpResponse<UploadFile>> uploadMultiFile(@PartMap Map<String, RequestBody> params);

    /**
     * 复合上传文件加参数
     * @param params 上传的文件集合
     * @param body1 测试参数1
     * @param body2 测试参数2
     * @return 服务器返回对象
     */
    @POST("/WebProjectLC/lc/userInfo/suffixFileUp")
    @Multipart
    Observable<HttpResponse<UploadFile>> uploadSuffixFile(@PartMap Map<String, RequestBody> params,@Part("body1") RequestBody body1, @Part("body2") RequestBody body2);
}
