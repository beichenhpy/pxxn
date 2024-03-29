package com.beichen.pxxn.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beichen.pxxn.entity.RequestQuery;
import com.beichen.pxxn.entity.ResponseInfo;
import com.beichen.pxxn.entity.VideoInfo;
import com.beichen.pxxn.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RequestVideoInfo {

    public void test(Integer id){
        log.info("-----------拿到id={}",id);
        RequestQuery requestQuery = new RequestQuery();
        requestQuery.setId(id);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS).build();
        Request request = new Request.Builder()
                .url("http://yrav.cc/api/payvideo.html")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                // 表单提交
                .post(RequestBody.create(
                        MediaType.parse("application/json; charset=utf-8"),
                        JSONObject.toJSONString(requestQuery)))
                .build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    if (!"<!DOCTYPE html>".equals(json.substring(0,15))){
                        ResponseInfo responseInfo = JSON.parseObject(json, ResponseInfo.class);
                        if(responseInfo.getResultCode() == 0){
                            VideoInfo videoInfo = responseInfo.getData().getVideoInfo();
                            JdbcTemplate jdbcTemplate = SpringContextUtils.getBean("jdbcTemplate", JdbcTemplate.class);
                            Integer integer = jdbcTemplate.queryForObject("select count(*) from video.videoinfo where id = ?", Integer.class, id);
                            if (integer == 0) {
                                log.info("-----拿到数据了");
                                jdbcTemplate.update("insert into video.videoinfo(id, title, url, thumbnail) VALUES (?,?,?,?)", videoInfo.getId(), videoInfo.getTitle(), videoInfo.getUrl(), videoInfo.getThumbnail());
                            }
                        }
                        response.body().close();
                    }
                }
            });
    }
}
