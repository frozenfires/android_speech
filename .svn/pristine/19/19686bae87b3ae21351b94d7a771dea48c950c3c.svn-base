package com.ethink.ai.voice.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.autoSpinner.SearchAdapter;
import com.baidu.speech.recognizerdemo.R;
import com.ethink.ai.voice.EthinkUtils;
import com.ethink.ai.voice.core.VoiceEngine;
import com.ethink.ai.voice.core.VoiceListener;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ActivityMain extends AppCompatActivity {

    private View.OnClickListener click = new View.OnClickListener(){
        @Override
        public void onClick(final View view) {
            if(isLocked()) {
                if(view.getId()==locker){
                    engine.cancel();
                    findTextView(view.getId()).setText("");
                    setLockView(view.getId(),false);
                    ((ImageView)view).setImageResource(R.drawable.voice);
                }
                return;
            }
            setLockView(view.getId(),true);

            try{
                currentInput = view.getId();
                final TextView input = findTextView(currentInput);
                if(currentInput == R.id.btn_loginPassword){
                    input.requestFocus();
                    search.setText(" ");
                    search.showDropDown();
                }else{
                    input.requestFocus();
                    input.setTextColor(Color.parseColor("#007ed7"));
                    input.setText("正在录入...");
                }

                final ImageView btn = (ImageView) view;
                btn.setImageResource(R.drawable.voice_ing);

                engine.start(new VoiceListener(){
                    @Override
                    public void onResult(String result, JSONObject resultJson,String old){
                        Log.d("result", result);
                        //((TextView)findViewById(R.id.result_text)).setText(old);
                        setLockView(view.getId(),false);
                        //如果
                        if((R.id.btn_loginPassword==currentInput)){
                            result = handleSpinner(result);
                        }
                        result = resultJson != null ? dealChineseNum(result, resultJson) : result;
                        Log.i("result.........=" , result);
                        if(!result.endsWith("公斤") &&
                                (R.id.btn_cunyou == currentInput || R.id.btn_huoyou == currentInput)
                                &&!((ActivityMain.this.getString(R.string.fault_on_yuyin)).equals(result))){
                            result = result + "公斤";
                            Log.i("666666666666", result);
                        }

                        Log.d("result==", result);
                        input.setTextColor(Color.parseColor("#444444"));
                        input.setText(result);
                        input.requestFocus(result.length());

                        btn.setImageResource(R.drawable.voice);
                        search.dismissDropDown();
                    }

                    @Override
                    public void onError(String errmsg) {
                        setLockView(view.getId(),false);
                        input.setTextColor(Color.parseColor("#666666"));
                        input.setText(ActivityMain.this.getString(R.string.fault_on_yuyin));
                        Log.e("error", errmsg);

                        btn.setImageResource(R.drawable.voice);
                    }
                });
            }catch (Throwable e){
                e.printStackTrace();
            }
        }
    };

    private int currentInput;
    private VoiceEngine engine;
    @SuppressLint("UseSparseArrays")
    private Map<Integer, Integer> idmap = new HashMap<>();
    private static int locker = -1;
    private AutoCompleteTextView search;
    private String[] selections = {
            " -10号军用柴油",
            " 柴油机冷却缓蚀剂",
            " HV46低温抗磨液压油",
            " cf-4 15w/40柴油机油"};

    private static Map<String,String> pinyinMap = new HashMap<String,String>();
    private static Map<String,String> youliaoMap = new HashMap<String,String>();
    static {
        pinyinMap.put("liang","二");
        pinyinMap.put("qian","千");
        pinyinMap.put("shi","十");

        youliaoMap.put("0501","-10号军用柴油");
        youliaoMap.put("4185","柴油机冷却缓蚀剂");
        youliaoMap.put("2312","HV46低温抗磨液压油");
        youliaoMap.put("1314","cf-4 15w/40柴油机油");
    }

    /**
     * 处理中文数据
     * @param result
     * @param resultJson
     * @return
     */
    private String dealChineseNum(String result, JSONObject resultJson) {
        Log.d("result.befor=", result);
        if(currentInput == R.id.btn_loginAccount){
            result = result.replace("号", "日");
        }

        if(currentInput == R.id.btn_cunyou || currentInput == R.id.btn_huoyou){
            result = transNumber(result);
            if(EthinkUtils.isChineseNum(result)){
                String num = String.valueOf(EthinkUtils.chineseToNumber(result));
                return num;
            }else{
                return this.getString(R.string.fault_on_yuyin);
            }
        }

        Iterator<String> keys = resultJson.keys();
        while(keys.hasNext()){
            String key = keys.next();
            try {
                String keyword = resultJson.getJSONObject(key).getString("keyword");
                if(EthinkUtils.isChineseNum(keyword)){
                    String num = String.valueOf(EthinkUtils.chineseToNumber(keyword));
                    if(key.equals("month")){
                        keyword = keyword + "月";
                        num = num + "月";
                    }
                    else if(key.equals("day")){
                        keyword = keyword + "日";
                        num = num + "日";
                    }
                    result = result.replace(keyword, num);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("main", "", e);
            }
        }

        Log.d("result.after=", result);
        return result;
    }

    private TextView findTextView(int btnId) {
        return ((TextView)ActivityMain.this.findViewById(idmap.get(btnId)));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSpinnerSearch();

        engine = new VoiceEngine();
        engine.initRecog(this);

        idmap.put(R.id.btn_loginAccount, R.id.loginAccount);
        idmap.put(R.id.btn_loginPassword, R.id.loginPassword);
        idmap.put(R.id.btn_mark, R.id.mark);
        idmap.put(R.id.btn_cunyou, R.id.cunyou);
        idmap.put(R.id.btn_huoyou, R.id.huoyou);

        this.findViewById(R.id.btn_loginAccount).setOnClickListener(click);
        this.findViewById(R.id.btn_loginPassword).setOnClickListener(click);
        this.findViewById(R.id.btn_mark).setOnClickListener(click);
        this.findViewById(R.id.btn_cunyou).setOnClickListener(click);
        this.findViewById(R.id.btn_huoyou).setOnClickListener(click);

    }


    private void initSpinnerSearch() {

        search = (AutoCompleteTextView) findViewById(R.id.loginPassword);
        search.setThreshold(0);
        search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Log.i("xxxxxxxx", "23333333333333333333333");
                    // 此处为得到焦点时的处理内容
                    if ("".equals(search.getText().toString())) {
                        Log.i("xxxxxxxx", "90999999999999");
                        search.setText("请输入操作指令");
                        search.setText(" ");
                        search.setListSelection(0);
                    }
                } else {
                    Log.i("xxxxxxxx", "8888888888888888888");
                    // 此处为失去焦点时的处理内容
                }
            }
        });
        search.setOnItemClickListener(new AdapterView.OnItemClickListener()  {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                engine.cancel();
                setLockView(view.getId(),false);
                ((ImageView)findViewById(R.id.btn_loginPassword)).setImageResource(R.drawable.voice);
            }
        });

        // 自动提示适配器
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, str);
        // 支持拼音检索
        SearchAdapter<String> adapter = new SearchAdapter<String>(this,
                R.layout.simple_dropdown_item_1line, selections, SearchAdapter.ALL);
        int num = adapter.getCount();
        search.setAdapter(adapter);
    }

    //true为锁定，false解锁
    private void setLockView(int id, boolean state) {
        if (state) {
            locker = id;
        } else {
            locker = -1;
        }
    }

    //有值是锁定，-1解锁
    private boolean isLocked() {
        if (locker == -1) {
            return false;
        }
        return true;
    }

    //从数组里找出含有key的字符串
    public String getValueFromArray(String []args,String key){
        for(String value : args){
            if(value.indexOf(key)!=-1){
                return value;
            }
        }
        return "";
    }

    //处理下拉选控件的值
    private String handleSpinner(String result) {
        String value = result;
        if(youliaoMap.get(value)!=null){
            return youliaoMap.get(value);
        }
        if(result.indexOf("斜杠")!=-1){
            value = value.replaceAll("斜杠","/");
        }
        if(result.indexOf("杠")!=-1){
            value = value.replaceAll("杠","-");
        }
        if(result.indexOf("负十")!=-1){
            value = value.replaceAll("负十","-10");
        }
        value = getValueFromArray(selections,value);
        if("".equals(value)){
            value = "请重新录入";
        }
        return value;
    }

    public String transNumber(String number){
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i<number.length();i++){
            String word = String.valueOf(number.charAt(i));
            try {
                String key = PinyinHelper.convertToPinyinString(word, "", PinyinFormat.WITHOUT_TONE);
                if(pinyinMap.containsKey(key)){
                    word = pinyinMap.get(key);
                }
                sb.append(word);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}