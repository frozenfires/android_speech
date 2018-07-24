package com.ethink.ai.voice.core;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.JsonReader;
import android.util.Log;

import com.baidu.android.voicedemo.control.MyRecognizer;
import com.baidu.android.voicedemo.recognization.CommonRecogParams;
import com.baidu.android.voicedemo.recognization.IRecogListener;
import com.baidu.android.voicedemo.recognization.RecogResult;
import com.baidu.android.voicedemo.recognization.StatusRecogListener;
import com.ethink.ai.voice.offline.OfflineRecogParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Map;

/**
 * Created by exlink on 2017/12/13.
 */

public class VoiceEngine {

    private IRecogListener listener = new StatusRecogListener(){

        /**
         * 临时识别结果
         * @param results
         * @param recogResult
         */
        @Override
        public void onAsrPartialResult(String[] results, RecogResult recogResult) {
//            sendStatusMessage("临时识别结果，结果是“" + results[0] + "”；原始json：" + recogResult.getOrigalJson());
            super.onAsrPartialResult(results, recogResult);
        }

        /**
         * 识别结束
         * @param results
         * @param recogResult
         */
        @Override
        public void onAsrFinalResult(String[] results, RecogResult recogResult) {
            super.onAsrFinalResult(results, recogResult);
            String message = "识别结束，结果是”" + results[0] + "”";
            Log.i("core", message + "“；原始json：" + recogResult.getOrigalJson());
//        String message = results[0];
//        if (speechEndTime > 0) {
//            long diffTime = System.currentTimeMillis() - speechEndTime;
//            message += "；说话结束到识别结束耗时【" + diffTime + "ms】";
//
//        }
//            speechEndTime = 0;
//            sendMessage(message, status, true);

            try {
                String ojson = recogResult.getOrigalJson();
                String result = results[0];
                result = result.replace("两百", "二百");
                result = result.replace("两千", "二千");
                result = result.replace("两万", "二万");

                ojson = ojson.replace("两百", "二百");
                ojson = ojson.replace("两千", "二千");
                ojson = ojson.replace("两万", "二万");

                JSONObject jsonret = new JSONObject(ojson);
                JSONObject oResult = jsonret.getJSONObject("origin_result");

                JSONObject _results = null;
                if(oResult.has("corpus_no")){
                    // 在线识别

                }else{
                    // 离线识别
                    _results = oResult.getJSONObject("result").getJSONObject("_results");
//                    String key = _results.keys().next();
//                    keyword = _results.getJSONObject(key).getString("keyword");
                }

                currentListener.onResult(result, _results,results[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onAsrFinishError(int errorCode, int subErrorCode, String errorMessage, String descMessage, RecogResult recogResult) {
            super.onAsrFinishError(errorCode, subErrorCode, errorMessage, descMessage, recogResult);
            String message = "识别错误, 错误码：" + errorCode + "," + subErrorCode;
            Log.e("core", message);
            currentListener.onError(message);
        }

        /**
         * 使用离线命令词时，有该回调说明离线语法资源加载成功
         */
        @Override
        public void onOfflineLoaded() {
            Log.i("core", "【重要】离线资源加载成功。没有此回调可能离线语法功能不能使用。");
        }
    };

    private Context context;
    private CommonRecogParams apiParams;
    private MyRecognizer myRecognizer ;
    private VoiceListener currentListener;

    public void start(VoiceListener voiceListener) {
        try {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            Map<String, Object> params = apiParams.fetch(sp);
            currentListener = voiceListener;
            myRecognizer.start(params);
        }catch (Throwable e){
            e.printStackTrace();
            Log.e("core", "", e);
        }

    }

    /**
     * 在onCreate中调用。初始化识别控制类MyRecognizer
     */
    public void initRecog(Context context) {
        try{
            this.context = context;
            myRecognizer = new MyRecognizer(context, listener);
            apiParams = new OfflineRecogParams((Activity) context);
            myRecognizer.loadOfflineEngine(OfflineRecogParams.fetchOfflineParams());
        }catch (Throwable e){
            e.printStackTrace();
        }

    }


    public void cancel(){
        if(myRecognizer!=null){
            myRecognizer.cancel();
        }
    }

}
