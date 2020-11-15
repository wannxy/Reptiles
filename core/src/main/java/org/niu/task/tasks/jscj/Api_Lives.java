package org.niu.task.tasks.jscj;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import okhttp3.*;
import org.apache.ibatis.session.SqlSession;
import org.jetbrains.annotations.NotNull;
import org.mybatis.BatisHelper;
import org.niu.bean.jscj.Live;
import org.niu.log.XLog;
import org.niu.mapping.jscj.LiveMapper;

import java.io.IOException;

public class Api_Lives {

    public static String TAG ="Api_Lives";

    private final String URL = "https://api.jinse.com/noah/v2/lives";
    private final String limit = "20";
    private final boolean reading = false;
    private final String source = "web";
    private final String flag = "down";
    private final String id = "0";

    private OkHttpClient client = new OkHttpClient();

    public void Get(){
        Request request = new Request.Builder()
                .url(String.format("%s?limit=%s&reading=%s&source=%s&flag=%s&id=%s",URL,limit,reading,source,flag,id))
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try(SqlSession session = BatisHelper.getHelper().getSession()){
                    JsonElement element = JsonParser.parseString(response.body().string());
                    JsonArray dates = element.getAsJsonObject().getAsJsonArray("list");
                    LiveMapper mapper = session.getMapper(LiveMapper.class);
                    int counter = 0;
                    for(JsonElement e : dates){
                        JsonArray lives = e.getAsJsonObject().getAsJsonArray("lives");
                        for(JsonElement el : lives){
                            counter++;
                            Live live = new Live();
                            live.setId(el.getAsJsonObject().get("id").getAsInt());
                            live.setContent(el.getAsJsonObject().get("content").getAsString());
                            live.setContent_prefix(el.getAsJsonObject().get("content_prefix").getAsString());
                            live.setLink(el.getAsJsonObject().get("link").getAsString());
                            live.setAttribute(el.getAsJsonObject().get("attribute").getAsString());
                            live.setUp_counts(el.getAsJsonObject().get("up_counts").getAsInt());
                            live.setDown_counts(el.getAsJsonObject().get("down_counts").getAsInt());
                            live.setDate(el.getAsJsonObject().get("created_at").getAsLong());
                            mapper.insertLive(live);
                        }
                    }
                    session.commit();
                    XLog.I(TAG,"已插入: %s 条记录！",counter);
                }catch (Exception ex){
                    XLog.E(TAG,"Ex: %s",ex.getMessage());
                }
            }
        });
    }


}
