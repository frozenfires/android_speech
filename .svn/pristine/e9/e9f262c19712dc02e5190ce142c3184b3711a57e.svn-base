package com.ethink.ai.voice.offline;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.baidu.speech.asr.SpeechConstant;
import com.baidu.android.voicedemo.recognization.CommonRecogParams;
import com.ethink.ai.voice.EthinkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by exlink on 2017/12/12.
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
//        map.put(SpeechConstant.ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH, "asset:///ethink_speech_grammar.bsg");
        map.put(SpeechConstant.ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH, "asset:///ethink_youliao_speech_grammar.bsg");
        map.putAll(fetchSlotDataParam());
        return map;
    }

    public  static Map<String, Object> fetchSlotDataParam(){
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject json = new JSONObject();

            // 添加年份词典数据
            json.put("year", buildYear())
                    // 添加月份词典
                    .put("month", buildChineseNum(1, 12))
                    // 添加日期词典
                    .put("day", buildChineseNum(1, 31))
                    // 添加小时词典
                    .put("hour", buildChineseNum(1, 24))
                    // 添加分，秒词典
                    .put("min", buildChineseNum(1, 60))
                    // 添加油料名称词典
                    .put("youliao", buildChineseNum(90, 99))
                    // 添加数字词典
                    .put("num", buildChineseNum(1, 2000));
//            JSONArray nums = new JSONArray();
//            nums.put("皇家园林").put("北京");
//            for(int i=1; i<100; i++){
//                nums.put(i);
//            }

//            JSONArray types = new JSONArray();
//            types.put("存款")
//                    .put("取款")
//                    .put("转账")
//                    .put("信用卡还款");
//
//
//            json
//                    .put("ethink_doc", nums)
//                    .put("name", types)
//                .put("name", new JSONArray().put("张三")
//                                .put("李四")
//                                .put("赵武"))
//            ;
            map.put(SpeechConstant.SLOT_DATA, json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    private static JSONArray buildChineseNum(int from, int to) {
        JSONArray month = new JSONArray();
        for(int i=from; i<=to; i++){
            month.put(EthinkUtils.numberToChinese(i));
        }
        Log.i("doc.buildChineseNum", month.toString());
        return month;
    }

//    private static JSONArray buildMonth() {
//        JSONArray month = new JSONArray();
//        for(int i=1; i<=12; i++){
//            month.put(EthinkUtils.numberToChinese(i));
//        }
//        Log.i("doc", month.toString());
//        return month;
//    }

    private static JSONArray buildYear() {
        JSONArray nums = new JSONArray();
        for(int i = 1900; i<=2020; i++){
            nums.put(i);
        }

        Log.i("main.buildYear", nums.toString());
        return nums;
    }

    public static void main(String[] args){
        String a = URLEncoder.encode("%7B%0A%20%20%20%20%22version%22%3A%20%220.1%22%2C%0A%20%20%20%20%22");

        String b  = URLDecoder.decode("%7B%0A%20%20%20%20%22version%22%3A%20%220.1%22%2C%0A%20%20%20%20%22");
//        System.out.println(a);
//        System.out.println(b);

        try {
//            File bsg = new File("F:\\download\\ethink_speech_grammar.bsg");
            File bsg = new File("F:\\baidu_speech_grammar.bsg");
            InputStream is = new FileInputStream(bsg);
            int lenth = (int) bsg.length();
            byte[] bs = new byte[(int) lenth];

            is.read(bs);
            String abc = new String(bs);
            System.out.println(abc);

            System.out.println(URLDecoder.decode(abc));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
        }

    }

}