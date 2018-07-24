package com.baidu.android.voicedemo.recognization.offline;

import android.app.Activity;
import android.content.SharedPreferences;

import com.baidu.android.voicedemo.recognization.CommonRecogParams;
import com.baidu.speech.asr.SpeechConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fujiayi on 2017/6/13.
 */

public class OfflineRecogParams extends CommonRecogParams {

    private static final String TAG = "OnlineRecogParams";

    public OfflineRecogParams(Activity context) {
        super(context);
    }


    public Map<String, Object> fetch(SharedPreferences sp) {

        Map<String, Object> map = super.fetch(sp);
        map.put(SpeechConstant.DECODER, 2);
        return map;

    }

    public static Map<String, Object> fetchOfflineParams() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(SpeechConstant.DECODER, 2);
//        map.put(SpeechConstant.ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH, "asset:///baidu_speech_grammar.bsg");
        map.put(SpeechConstant.ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH, "asset:///ethink_speech_grammar.bsg");
        map.putAll(fetchSlotDataParam());
        return map;
    }

    public  static Map<String, Object> fetchSlotDataParam(){
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject json = new JSONObject();

//            JSONArray nums = new JSONArray();
//            for(int i=1; i<100; i++){
//                nums.put(String.valueOf(i));
//            }

            JSONArray types = new JSONArray();
            types.put("存款")
                    .put("取款")
                    .put("转账")
                    .put("信用卡还款");


            json
//                    .put("num", nums)
                .put("name", types)
//                .put("name", new JSONArray().put("张三")
//                                .put("李四")
//                                .put("赵武"))
            ;
            map.put(SpeechConstant.SLOT_DATA, json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

}
